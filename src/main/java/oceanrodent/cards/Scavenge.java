package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Scavenge extends AbstractRodentCard {
    public final static String ID = makeID("Scavenge");

    public Scavenge() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        previewsJunk = true;
        cardsToPreview = dummyCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ScavengePower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    public static class ScavengePower extends AbstractEasyPower {
        public static String POWER_ID = makeID("ScavengePower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public ScavengePower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void atStartOfTurn() {
            flash();
            atb(new Junk.MakeAction(amount, Junk.MakeAction.Location.HAND));
        }
    }
}