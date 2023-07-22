package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class AppleCore extends AbstractEasyRelic {
    public static final String ID = makeID("AppleCore");
    private static final int JUNK_ADDED = 1;

    public AppleCore() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + JUNK_ADDED + DESCRIPTIONS[1];
    }

    public void atBattleStartPreDraw() {
        atb(new RelicAboveCreatureAction(adp(), this));
        atb(new Junk.MakeAction(JUNK_ADDED, Junk.MakeAction.Location.HAND));
    }
}