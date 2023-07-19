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
                    CardModifierManager.removeSpecificModifier(c, modifier, true);
                }
            });
        }

        public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard c, AbstractMonster target) {
            if (c.damage > 0) return damage * amplifier();
            return damage;
        }

        public float modifyBlock(float block, AbstractCard c) {
            if (c.block > 0) return block * amplifier();
            return block;
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

    public static GrimedMod getModifier(AbstractCard c) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(c, GrimedMod.ID);
        if (modifiers.size() > 0)
            return (GrimedMod)modifiers.get(0);
        return null;
    }

    public static boolean isGrimy(AbstractCard c) {
        return grimeAmount(c) > 0;
    }

    public static int grimeAmount(AbstractCard c) {
        GrimedMod modifier = getModifier(c);
        if (modifier == null) return 0;
        return modifier.amount;
    }

    public static void grime(AbstractCard c, int amount) {
        GrimedMod modifier = getModifier(c);
        if (modifier == null) CardModifierManager.addModifier(c, new GrimedMod(amount));
        else modifier.amount += amount;
    }

    public static void grime(AbstractCard c) {
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

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddKeyword {
        private static String KEYWORD_ID = makeID("grimy");

        public static void Postfix(AbstractCard __instance) {
            if (isGrimy(__instance) && !__instance.keywords.contains(KEYWORD_ID))
                __instance.keywords.add(KEYWORD_ID);
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