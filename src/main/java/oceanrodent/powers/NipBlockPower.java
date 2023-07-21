package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class NipBlockPower extends NipPower {
    public static String POWER_ID = "NipBlock";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(makeID(POWER_ID));
    
    public NipBlockPower(int amount) {
        super(POWER_ID, true, amount, powerStrings.NAME, a -> powerStrings.DESCRIPTIONS[0] + a + powerStrings.DESCRIPTIONS[1], a -> atb(new GainBlockAction(adp(), adp(), a, true)));
    }
}