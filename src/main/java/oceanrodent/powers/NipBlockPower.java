package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

//this class is stupid lol i would love to make it just extend nippower but that crashes w together in spire for some reason
public class NipBlockPower {
    public static String POWER_ID = "NipBlock";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(makeID(POWER_ID));

    public static NipPower make(int amount) {
        return new NipPower(POWER_ID, true, amount, powerStrings.NAME, a -> powerStrings.DESCRIPTIONS[0] + a + powerStrings.DESCRIPTIONS[1], a -> atb(new GainBlockAction(adp(), adp(), a, true)));
    }
}