package oceanrodent.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanrodent.characters.TheRodent;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;

public class SoggySock extends AbstractEasyRelic implements OnApplyPowerRelic {
    public static final String ID = makeID("SoggySock");
    private static final int EXTRA_ENCHEESED_APPLIED = 1;

    public SoggySock() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EXTRA_ENCHEESED_APPLIED + DESCRIPTIONS[1];
    }

    public boolean onApplyPower(AbstractPower var1, AbstractCreature var2, AbstractCreature var3) {
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (source instanceof AbstractPlayer && power instanceof Encheesed){
            power.amount += EXTRA_ENCHEESED_APPLIED;
            stackAmount += EXTRA_ENCHEESED_APPLIED;
            flash();
        }
        return stackAmount;
    }
}