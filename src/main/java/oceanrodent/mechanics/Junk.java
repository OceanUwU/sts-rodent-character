package oceanrodent.mechanics;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.OmegaPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import oceanrodent.cards.AbstractRodentCard;
import oceanrodent.cards.RatKing.RatKingPower;
import oceanrodent.mechanics.Junk.MakeAction.Location;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.modID;
import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Junk {
    public static ArrayList<JunkCard> allJunk = new ArrayList<>();
    private static ArrayList<String> weightedJunk = new ArrayList<>();

    public static void initialise() {
        add(new JunkCard("Wrapper", 30, CardType.ATTACK, CardTarget.ENEMY, 4, 0, 0, (c, m) -> {
            c.dmg(m, AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Lid", 30, CardType.SKILL, CardTarget.SELF, 0, 3, 0, (c, m) -> {
            c.blck();}));

        add(new JunkCard("StaleLoafOfBread", 10, CardType.ATTACK, CardTarget.ALL_ENEMY, 2, 0, 0, (c, m) -> {
            c.allDmg(AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Bottle", 15, CardType.ATTACK, CardTarget.ENEMY, 3, 3, 0, (c, m) -> {
            c.blck();
            c.dmg(m, AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Cigarette", 10, CardType.ATTACK, CardTarget.ENEMY, 3, 0, 1, (c, m) -> {
            c.dmg(m, AttackEffect.BLUNT_LIGHT);
            applyToEnemy(m, new VulnerablePower(m, c.magicNumber, false));}));

        add(new JunkCard("Straw", 10, CardType.SKILL, CardTarget.ENEMY, 0, 3, 1, (c, m) -> {
            c.blck();
            applyToEnemy(m, new WeakPower(m, c.magicNumber, false));}));

        add(new JunkCard("Mud", 10, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new Grime.GrimeRandomAction(c.magicNumber));}));

        add(new JunkCard("MouldyCheese", 10, CardType.SKILL, CardTarget.ENEMY, 0, 0, 3, (c, m) -> {
            applyToEnemy(m, new Encheesed(m, c.magicNumber));}));

        add(new JunkCard("PaperPlate", 10, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new DrawCardAction(c.magicNumber));}));

        add(new JunkCard("FullCan", 6, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new GainEnergyAction(c.magicNumber));}));

        add(new JunkCard("BinBag", 2, CardType.SKILL, CardTarget.SELF, 0, 0, 2, (c, m) -> {
            atb(new MakeAction(c.magicNumber, Location.HAND));}));

        add(new JunkCard("Sandwich", 6, CardType.POWER, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            applyToSelf(new StrengthPower(adp(), c.magicNumber));}));

        add(new JunkCard("Toothbrush", 4, CardType.POWER, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            applyToSelf(new DexterityPower(adp(), c.magicNumber));}));

        add(new JunkCard("BinLid", 4, CardType.POWER, CardTarget.SELF, 0, 0, 2, (c, m) -> {
            applyToSelf(new PlatedArmorPower(adp(), c.magicNumber));}));

        add(new JunkCard("BubbleWrap", 4, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            for (int i = 0; i < c.magicNumber; i++)
                atb(new UpgradeRandomCardAction());}));

        add(new JunkCard("PlasticBag", 5, CardType.SKILL, CardTarget.ENEMY, 0, 0, 3, (c, m) -> {
            applyToEnemy(m, new StrengthPower(m, -c.magicNumber));
            if (m != null && !m.hasPower(ArtifactPower.POWER_ID))
                applyToEnemy(m, new GainStrengthPower(m, c.magicNumber));}));

        add(new JunkCard("Battery", 5, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            for (int i = 0; i < c.magicNumber; i++)
                atb(new ChannelAction(new Lightning()));}));

        add(new JunkCard("GlassBottle", 5, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            for (int i = 0; i < c.magicNumber; i++)
                atb(new ChannelAction(new Frost()));}));

        add(new JunkCard("Stardust", 1, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            for (int i = 0; i < c.magicNumber; i++)
                atb(new ChannelAction(new Plasma()));}));

        add(new JunkCard("Stickyweed", 4, CardType.SKILL, CardTarget.SELF, 0, 0, 0, (c, m) -> {})
            .edit(c -> c.selfRetain = true));

        add(new JunkCard("LuckyPenny", 3, CardType.SKILL, CardTarget.SELF, 0, 0, 0, (c, m) -> {
            applyToSelf(new DuplicationPower(adp(), 1));}));

        add(new JunkCard("HalfAPairOfScissors", 4, CardType.SKILL, CardTarget.SELF, 0, 0, 2, (c, m) -> {
            atb(new ScryAction(c.magicNumber));}));

        add(new JunkCard("ShreddedLetter", 6, CardType.SKILL, CardTarget.SELF, 0, 0, 3, (c, m) -> {
            atb(new AbstractGameAction() {
                private boolean retrieved = false;
                public void update() {
                    if (duration == 0f) {
                        duration = Settings.ACTION_DUR_FAST;
                        ArrayList<AbstractCard> cards = new ArrayList<>();
                        while (cards.size() < c.magicNumber) {
                            AbstractCard.CardRarity r;
                            boolean dupe = false;
                            int roll = AbstractDungeon.cardRandomRng.random(99);
                            if (roll < 55) r = AbstractCard.CardRarity.COMMON;
                            else if (roll < 85) r = AbstractCard.CardRarity.UNCOMMON;
                            else r = AbstractCard.CardRarity.RARE;
                            AbstractCard c = CardLibrary.getAnyColorCard(r);
                            for (AbstractCard check : cards)
                                if (c.cardID.equals(check.cardID) || c.tags.contains(AbstractCard.CardTags.HEALING))
                                    dupe = true;
                            if (!dupe) cards.add(c.makeCopy());
                        }
                        AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], true);
                        tickDuration();
                        return;
                    }
                    if (!retrieved) {
                        if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                            AbstractCard c = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                            c.current_x = -1000f * Settings.scale;
                            if (adp().hand.size() < BaseMod.MAX_HAND_SIZE) AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, Settings.WIDTH/2f, Settings.HEIGHT/2f));
                            else AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, Settings.WIDTH/2f, Settings.HEIGHT/2f));
                            AbstractDungeon.cardRewardScreen.discoveryCard = null;
                        }
                        retrieved = true;
                    }
                    tickDuration();
                    if (duration <= 0f) isDone = true;
                }
            });}));

        add(new JunkCard("SpoiledOrange", 5, CardType.SKILL, CardTarget.ENEMY, 0, 0, 4, (c, m) -> {
            applyToEnemy(m, new PoisonPower(m, adp(), c.magicNumber));}));

        add(new JunkCard("RunicWrapper", 1, CardType.POWER, CardTarget.SELF, 0, 0, 10, (c, m) -> {
            applyToSelf(new OmegaPower(adp(), c.magicNumber));}));
    }

    private static void add(JunkCard junk) {
        for (int i = 0; i < junk.weight; i++)
            weightedJunk.add(junk.cardID);
        allJunk.add(junk);
    }

    public static JunkCard getJunk(String junkID) {
        for (JunkCard c : allJunk)
            if (c.cardID.equals(junkID))
                return (JunkCard)c.makeCopy();
        for (JunkCard c : allJunk)
            if (c.cardID.equals(makeID(junkID)))
                return (JunkCard)c.makeCopy();
        return null;
    }

    public static JunkCard getRandomJunk(Random random) {
        return getJunk(weightedJunk.get(random.random(0, weightedJunk.size() - 1)));
    }

    public static ArrayList<JunkCard> getRandomJunk(Random random, int amount) {
        return getRandomJunk(random, amount, false);
    }

    public static ArrayList<JunkCard> getRandomJunk(Random random, int amount, boolean allUnique) {
        ArrayList<JunkCard> junk = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            while (true) {
                JunkCard newJunk = getRandomJunk(random);
                if (allUnique) {
                    boolean foundSame = false;
                    for (JunkCard comp : junk)
                        if (comp.cardID.equals(newJunk.cardID))
                            foundSame = true;
                    if (foundSame) continue;
                }
                junk.add(newJunk);
                break;
            }
        }
        return junk;
    }

    public static class MakeAction extends AbstractGameAction {
        public enum Location {DRAW, HAND}
        private int amount;
        private Location location;
        private Consumer<ArrayList<JunkCard>> doToJunk;

        public MakeAction(int amount, Location location, Consumer<ArrayList<JunkCard>> doToJunk) {
            this.amount = amount;
            this.location = location;
            this.doToJunk = doToJunk;
        }

        public MakeAction(int amount, Location location) {
            this(amount, location, cards -> {});
        }

        public MakeAction(Location location) {
            this(1, location);
        }

        public void update() {
            ArrayList<JunkCard> junk = getRandomJunk(AbstractDungeon.cardRandomRng, amount);
            if (adp().hasPower(RatKingPower.POWER_ID))
                adp().getPower(RatKingPower.POWER_ID).flash();
            for (JunkCard c : junk) {
                if (adp().hasPower(RatKingPower.POWER_ID) && c.canUpgrade())
                    c.upgrade();
                switch (location) {
                    case DRAW:
                        att(new MakeTempCardInDrawPileAction(c, 1, true, true));
                        break;
                    case HAND:
                        att(new MakeTempCardInHandAction(c, 1));
                        break;
                }
            }
            doToJunk.accept(junk);
            isDone = true;
        }
    }

    public static class JunkCard extends AbstractRodentCard {
        private static CardStrings junkStrings = CardCrawlGame.languagePack.getCardStrings(makeID("Junk"));
        private BiConsumer<JunkCard, AbstractMonster> effect;
        public int weight;
        private int origD, origB, origM;
        private Consumer<JunkCard> editOp;

        public JunkCard(String cardID, int weight, CardType type, CardTarget target, int dmg, int blc, int mgc, BiConsumer<JunkCard, AbstractMonster> effect) {
            super(makeID(cardID), 0, type, CardRarity.SPECIAL, target, CardColor.COLORLESS);
            this.effect = effect;
            this.weight = weight;

            if (dmg == 0) dmg = -1;
            if (blc == 0) blc = -1;
            if (mgc == 0) mgc = -1;
            origD = dmg;
            baseDamage = dmg;
            origB = blc;
            baseBlock = blc;
            origM = mgc;
            baseMagicNumber = magicNumber = mgc;

            exhaust = type != CardType.POWER;
            baseSecondMagic = secondMagic = 1;

            CardModifierManager.addModifier(this, new RarityTipModifier());
        }

        public JunkCard edit(Consumer<JunkCard> consumer) {
            if (consumer != null) {
                editOp = consumer;
                editOp.accept(this);
            }
            return this;
        }

        public void use(AbstractPlayer p, AbstractMonster m) {
            effect.accept(this, m);
            atb(new DrawCardAction(secondMagic));
        }

        public void initializeDescription() {
            if (cardStrings == null)
                cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
            rawDescription = junkStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION + junkStrings.EXTENDED_DESCRIPTION[1];
            if (type != CardType.POWER)
                rawDescription += junkStrings.EXTENDED_DESCRIPTION[2];
            super.initializeDescription();
        }

        public void upp() {
            upSecondMagic(1);
        }

        public AbstractCard makeCopy() {
            return new JunkCard(cardID.replace(modID+":", ""), weight, type, target, origD, origB, origM, effect).edit(editOp);
        }

        public static class RarityTipModifier extends AbstractCardModifier {
            public List<TooltipInfo> additionalTooltips(AbstractCard card) {
                ArrayList<TooltipInfo> tips = new ArrayList<>();
                if (card instanceof JunkCard) {
                    JunkCard junk = (JunkCard)card;
                    tips.add(new TooltipInfo(junkStrings.EXTENDED_DESCRIPTION[3], ("#b"+((float)Math.round((float)junk.weight/(float)weightedJunk.size()*10000))/100f+"%").replace(".0%", "%")));
                }
                return tips;
            }

            public boolean isInherent(AbstractCard card) {return true;}
            public AbstractCardModifier makeCopy() {return new RarityTipModifier();}
        }
    }
}