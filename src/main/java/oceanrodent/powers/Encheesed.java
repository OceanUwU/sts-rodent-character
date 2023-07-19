package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Encheesed extends AbstractEasyPower {
    public static String POWER_ID = makeID("Encheesed");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
    public Encheesed(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, true, owner, amount);
    }
  
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage + amount;
        return damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL)
            flashWithoutSound();
        return damageAmount;
    }
  
    public void atEndOfTurn(boolean isPlayer) {
        if (!owner.hasPower(CheeseSeal.POWER_ID))
            atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
        
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}