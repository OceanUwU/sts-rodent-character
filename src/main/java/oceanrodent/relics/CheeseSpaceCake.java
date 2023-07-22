package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import oceanrodent.characters.TheRodent;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class CheeseSpaceCake extends AbstractEasyRelic {
    public static final String ID = makeID("CheeseSpaceCake");
    private static final int ENCHEESED_APPLIED = 3;

    public CheeseSpaceCake() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ENCHEESED_APPLIED + DESCRIPTIONS[1];
    }
  
    public void atBattleStart() {
        flash();
        forAllMonstersLiving(mo -> {
            atb(new RelicAboveCreatureAction(mo, this));
            applyToEnemy(mo, new Encheesed(mo, ENCHEESED_APPLIED));
        });
    }
}