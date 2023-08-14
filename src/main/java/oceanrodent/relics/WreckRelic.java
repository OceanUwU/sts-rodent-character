package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import oceanrodent.characters.TheRodent;
import oceanrodent.powers.WreckPower;
import oceanrodent.powers.interfaces.OnFinishWreckage;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class WreckRelic extends AbstractEasyRelic implements OnFinishWreckage {
    public static final String ID = makeID("WreckRelic");
    private static final int PLATED_GIVEN = 1;

    public WreckRelic() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PLATED_GIVEN + DESCRIPTIONS[1];
    }
  
    public void onFinishWreckage(WreckPower wreckage) {
        flash();
        applyToSelf(new PlatedArmorPower(adp(), PLATED_GIVEN));
        atb(new RelicAboveCreatureAction(adp(), this));
    }
}