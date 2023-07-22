package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Pill extends AbstractEasyRelic {
    public static final String ID = makeID("Pill");
    private static final int PER_JUNK_PLAYED = 5;

    public Pill() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
        counter = 0;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PER_JUNK_PLAYED + DESCRIPTIONS[1];
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof Junk.JunkCard) {
            if (++counter == PER_JUNK_PLAYED) {
                counter = 0;
                flash();
                pulse = false;
                atb(new RelicAboveCreatureAction(adp(), this));
                atb(new GainEnergyAction(1));
            } else if (counter == PER_JUNK_PLAYED - 1) {
                beginPulse();
                pulse = true;
            }
        }
    }
  
    public void atBattleStart() {
        if (counter == PER_JUNK_PLAYED - 1) {
            beginPulse();
            pulse = true;
        }
    }
}