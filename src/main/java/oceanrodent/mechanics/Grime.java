package oceanrodent.mechanics;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.lang.reflect.Field;
import java.util.ArrayList;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.RodentMod.makeImagePath;
import static oceanrodent.util.Wiz.*;

public class Grime {
    public static class GrimedMod extends AbstractCardModifier {
        public static final String ID = makeID("GrimedMod");
        private static final Texture ICON_TEXTURE = TexLoader.getTexture(makeImagePath("ui/grime.png"));
        public int amount;
        public float timer = 0f;
        public boolean supplyingKeyword;

        public GrimedMod(int amount) {
            this.amount = amount;
        }

        public void onUpdate(AbstractCard c) {
            timer += Gdx.graphics.getDeltaTime();
        }

        public void onRender(AbstractCard c, SpriteBatch sb) {
            ExtraIcons.icon(ICON_TEXTURE).text(String.valueOf(amount)).render(c);
        }

        private float amplifier() {
            return 1 + 0.5f * amount;
        }

        public void onUse(AbstractCard c, AbstractCreature target, UseCardAction action) {
            GrimedMod modifier = this;
            att(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    TarnishedMod tarnish = getTarnish(c);
                    if (tarnish != null) {
                        if (tarnish.amount > 1) tarnish.amount--;
                        else CardModifierManager.removeSpecificModifier(c, tarnish, true);
                    } else
                        CardModifierManager.removeSpecificModifier(c, modifier, true);
                }
            });
        }

        public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard c, AbstractMonster target) {
            if (type == DamageInfo.DamageType.NORMAL)
                return damage * amplifier();
            return damage;
        }

        public float modifyBlock(float block, AbstractCard c) {
            return block * amplifier();
        }

        public boolean shouldApply(AbstractCard c) {
            return canGrime(c) && !CardModifierManager.hasModifier(c, ID);
        }

        public void onInitialApplication(AbstractCard c) {
            c.initializeDescription();
        }
     
        public void onRemove(AbstractCard c) {
            c.initializeDescription();
        }

        public String identifier(AbstractCard c) {return ID;}
        public GrimedMod makeCopy() {return new GrimedMod(amount);}
    }

    public static class TarnishedMod extends AbstractCardModifier {
        public static final String ID = makeID("TarnishedMod");
        private static final Texture ICON_TEXTURE = TexLoader.getTexture(makeImagePath("ui/tarnish.png"));
        public int amount;
        public float timer = 0f;
        public boolean supplyingKeyword;

        public TarnishedMod(int amount) {
            this.amount = amount;
        }

        public void onRender(AbstractCard c, SpriteBatch sb) {
            ExtraIcons.icon(ICON_TEXTURE).text(String.valueOf(amount)).render(c);
        }

        public boolean shouldApply(AbstractCard c) {
            return canGrime(c) && !CardModifierManager.hasModifier(c, ID);
        }

        public void onInitialApplication(AbstractCard c) {
            c.initializeDescription();
        }
     
        public void onRemove(AbstractCard c) {
            c.initializeDescription();
        }

        public String identifier(AbstractCard c) {return ID;}
        public TarnishedMod makeCopy() {return new TarnishedMod(amount);}
    }

    public static GrimedMod getModifier(AbstractCard c) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(c, GrimedMod.ID);
        if (modifiers.size() > 0)
            return (GrimedMod)modifiers.get(0);
        return null;
    }

    public static TarnishedMod getTarnish(AbstractCard c) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(c, TarnishedMod.ID);
        if (modifiers.size() > 0)
            return (TarnishedMod)modifiers.get(0);
        return null;
    }

    public static boolean isGrimy(AbstractCard c) {
        return grimeAmount(c) > 0;
    }

    public static boolean isTarnished(AbstractCard c) {
        return getTarnish(c) != null;
    }

    public static int grimeAmount(AbstractCard c) {
        GrimedMod modifier = getModifier(c);
        if (modifier == null) return 0;
        return modifier.amount;
    }

    public static void grime(AbstractCard c, int amount) {
        if (!canGrime(c)) return;
        GrimedMod modifier = getModifier(c);
        if (modifier == null) CardModifierManager.addModifier(c, new GrimedMod(amount));
        else modifier.amount += amount;
        c.flash(Color.BROWN.cpy());
    }

    public static void grime(AbstractCard c) {
        grime(c, 1);
    }

    public static void tarnish(AbstractCard c, int amount) {
        if (!canGrime(c)) return;
        TarnishedMod modifier = getTarnish(c);
        if (modifier == null) CardModifierManager.addModifier(c, new TarnishedMod(amount));
        else modifier.amount += amount;
        c.flash(Color.FIREBRICK.cpy());
    }

    public static void tarnish(AbstractCard c) {
        grime(c, 1);
    }

    public static boolean canGrime(AbstractCard c) {
        return c.baseDamage > 0 || c.baseBlock > 0;
    }

    public static class Action extends AbstractGameAction {
        AbstractCard c;
        int amount;

        public Action(AbstractCard c, int amount) {
            this.c = c;
            this.amount = amount;
        }

        public Action(AbstractCard c) {
            this(c, 1);
        }

        public void update() {
            if (c != null)
                grime(c, amount);
            isDone = true;
        }
    }

    public static class GrimeRandomAction extends AbstractGameAction {
        int amount;

        public GrimeRandomAction(int amount) {
            this.amount = amount;
        }

        public GrimeRandomAction() {
            this(1);
        }

        public void update() {
            isDone = true;
            CardGroup grimable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : adp().hand.group)
                if (Grime.canGrime(c))
                    grimable.addToTop(c);
            if (grimable.size() > 0) {
                grimable.shuffle();
                int toAttune = amount;
                for (AbstractCard c : grimable.group) {
                    att(new Grime.Action(c));
                    if (--toAttune <= 0) break;
                }
            }
        }
    }

    public static class TarnishAction extends AbstractGameAction {
        AbstractCard c;
        int amount;

        public TarnishAction(AbstractCard c, int amount) {
            this.c = c;
            this.amount = amount;
        }

        public TarnishAction(AbstractCard c) {
            this(c, 1);
        }

        public void update() {
            if (c != null)
                tarnish(c, amount);
            isDone = true;
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddKeyword {
        private static String GRIME_KEYWORD_ID = makeID("grimy");
        private static String TARNISHED_KEYWORD_ID = makeID("tarnished");

        public static void Postfix(AbstractCard __instance) {
            if (isGrimy(__instance) && !__instance.keywords.contains(GRIME_KEYWORD_ID))
                __instance.keywords.add(GRIME_KEYWORD_ID);
            if (isTarnished(__instance) && !__instance.keywords.contains(TARNISHED_KEYWORD_ID))
                __instance.keywords.add(TARNISHED_KEYWORD_ID);
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="renderCardBg")
    public static class Render {
        private static final float GRIME_TIME = 0.4f;
        private static final float INITIAL_SCALE = 3.0f;
        private static Field colorField = ReflectionHacks.getCachedField(AbstractCard.class, "renderColor");
        private static TextureAtlas.AtlasRegion GRIMED_TEX = new TextureAtlas.AtlasRegion(new Texture(makeImagePath("512/grime.png")), 0, 0, 512, 512);

        public static void Postfix(AbstractCard __instance, SpriteBatch sb, float x, float y) {
            try {
                GrimedMod modifier = getModifier(__instance);
                if (modifier != null) {
                    float progress = Math.min(modifier.timer / GRIME_TIME, 1f);
                    float scale = INITIAL_SCALE - (INITIAL_SCALE - 1f) * progress;
                    sb.setColor(((Color)colorField.get(__instance)).cpy().mul(1f, 1f, 1f, progress));
                    sb.draw(GRIMED_TEX, __instance.current_x - GRIMED_TEX.originalWidth / 2F, __instance.current_y - GRIMED_TEX.originalHeight / 2F, GRIMED_TEX.originalWidth / 2F, GRIMED_TEX.originalHeight / 2F, GRIMED_TEX.packedWidth, GRIMED_TEX.packedHeight, __instance.drawScale * Settings.scale * scale, __instance.drawScale * Settings.scale * scale, __instance.angle);
                }
            } catch (Exception e) {}
        }
    }
}