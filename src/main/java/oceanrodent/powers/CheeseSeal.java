package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class CheeseSeal extends AbstractEasyPower {
    public static String POWER_ID = makeID("CheeseSeal");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
    public CheeseSeal(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, true, owner, amount);
    }
  
    public void atEndOfTurn(boolean isPlayer) {
        atb(new ReducePowerAction(owner, owner, this, 1));
    }
        
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[amount == 1 ? 1 : 2];
    }
}