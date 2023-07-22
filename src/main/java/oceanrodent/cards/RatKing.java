package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class RatKing extends AbstractRodentCard {
    public final static String ID = makeID("RatKing");

    public RatKing() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RatKingPower(p));
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    public static class RatKingPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("RatKingPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

        public RatKingPower(AbstractCreature owner) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, -1);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0];
        }
    }
}