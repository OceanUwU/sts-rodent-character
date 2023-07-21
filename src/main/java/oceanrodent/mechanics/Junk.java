package oceanrodent.mechanics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import oceanrodent.cards.AbstractRodentCard;
import oceanrodent.mechanics.Junk.MakeAction.Location;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.modID;
import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Junk {
    public static ArrayList<JunkCard> allJunk = new ArrayList<>();
    private static ArrayList<String> weightedJunk = new ArrayList<>();

    public static void initialise() {
        add(new JunkCard("Wrapper", 20, CardType.ATTACK, CardTarget.ENEMY, 4, 0, 0, (c, m) -> {
            c.dmg(m, AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Lid", 20, CardType.SKILL, CardTarget.SELF, 0, 3, 0, (c, m) -> {
            c.blck();}));

        add(new JunkCard("StaleLoafOfBread", 8, CardType.ATTACK, CardTarget.ALL_ENEMY, 2, 0, 0, (c, m) -> {
            c.allDmg(AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Bottle", 8, CardType.ATTACK, CardTarget.ENEMY, 3, 3, 0, (c, m) -> {
            c.blck();
            c.dmg(m, AttackEffect.BLUNT_LIGHT);}));

        add(new JunkCard("Cigarette", 8, CardType.ATTACK, CardTarget.ENEMY, 3, 0, 1, (c, m) -> {
            c.dmg(m, AttackEffect.BLUNT_LIGHT);
            applyToEnemy(m, new VulnerablePower(m, c.magicNumber, false));}));

        add(new JunkCard("Straw", 8, CardType.SKILL, CardTarget.ENEMY, 0, 3, 1, (c, m) -> {
            c.blck();
            applyToEnemy(m, new WeakPower(m, c.magicNumber, false));}));

        add(new JunkCard("Mud", 5, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new Grime.GrimeRandomAction(c.magicNumber));}));

        add(new JunkCard("MouldyCheese", 5, CardType.SKILL, CardTarget.ENEMY, 0, 0, 3, (c, m) -> {
            applyToEnemy(m, new Encheesed(m, c.magicNumber));}));

        add(new JunkCard("PaperPlate", 5, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new DrawCardAction(c.magicNumber));}));

        add(new JunkCard("FullCan", 3, CardType.SKILL, CardTarget.SELF, 0, 0, 1, (c, m) -> {
            atb(new GainEnergyAction(c.magicNumber));}));

        add(new JunkCard("BinBag", 1, CardType.SKILL, CardTarget.SELF, 0, 0, 2, (c, m) -> {
            atb(new MakeAction(c.magicNumber, Location.HAND));}));
    }

    private static void add(JunkCard junk) {
        for (int i = 0; i < junk.weight; i++)
            weightedJunk.add(junk.cardID);
        allJunk.add(junk);
    }

    public static JunkCard getJunk(String junkID) {
        for (JunkCard c : allJunk)
            if (c.cardID == junkID)
                return (JunkCard)c.makeCopy();
        for (JunkCard c : allJunk)
            if (c.cardID == makeID(junkID))
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
                if (allUnique)
                    for (JunkCard comp : junk)
                        if (comp.cardID == newJunk.cardID)
                            continue;
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

        public MakeAction(int amount, Location location) {
            this.amount = amount;
            this.location = location;
        }

        public MakeAction(Location location) {
            this(1, location);
        }

        public void update() {
            ArrayList<JunkCard> junk = getRandomJunk(AbstractDungeon.cardRandomRng, amount);
            for (JunkCard c : junk)
                switch (location) {
                    case DRAW:
                        att(new MakeTempCardInDrawPileAction(c, 1, true, true));
                        break;
                    case HAND:
                        att(new MakeTempCardInHandAction(c, 1));
                        break;
                }
            isDone = true;
        }
    }

    public static class JunkCard extends AbstractRodentCard {
        private static CardStrings junkStrings = CardCrawlGame.languagePack.getCardStrings(makeID("Junk"));
        private BiConsumer<JunkCard, AbstractMonster> effect;
        public int weight;
        public int origD, origB, origM;

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

            exhaust = true;
            baseSecondMagic = secondMagic = 1;
        }

        public void use(AbstractPlayer p, AbstractMonster m) {
            effect.accept(this, m);
            atb(new DrawCardAction(secondMagic));
        }

        public void initializeDescription() {
            if (cardStrings == null)
                cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
            rawDescription = junkStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION + junkStrings.EXTENDED_DESCRIPTION[1];
            super.initializeDescription();
        }

        public void upp() {
            upSecondMagic(1);
        }

        public AbstractCard makeCopy() {
            return new JunkCard(cardID.replace(modID+":", ""), weight, type, target, origD, origB, origM, effect);
        };
    }
}