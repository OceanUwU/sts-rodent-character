package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class LickEm extends AbstractRodentCard {
    public final static String ID = makeID("LickEm");

    public LickEm() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LickEmPower(p, magicNumber));
        if (upgraded)
            LickEmPower.chooseToGrime(magicNumber);
    }

    public void upp() {}

    public static class LickEmPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("LickEmPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public LickEmPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
        }

        public void atStartOfTurnPostDraw() {
            flash();
            chooseToGrime(amount);
            atb(new HandCheckAction());
        }

        public static void chooseToGrime(int amount) {
            atb(new SelectCardsInHandAction(amount, powerStrings.DESCRIPTIONS[3], false, false, c -> Grime.canGrime(c), cards -> cards.forEach(c -> att(new Grime.Action(c)))));
        }
    }
}