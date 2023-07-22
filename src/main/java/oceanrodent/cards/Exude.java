package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanrodent.powers.AbstractEasyPower;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Exude extends AbstractRodentCard {
    public final static String ID = makeID("Exude");

    public Exude() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DoubleEncheesedPower(p, 3));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class DoubleEncheesedPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("DoubleEncheesedPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public DoubleEncheesedPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
        }

        public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
            if (power instanceof Encheesed && source == owner) {
                power.amount *= 2;
                flash();
                att(new ReducePowerAction(owner, owner, this, 1));
            }
        }
    }
}