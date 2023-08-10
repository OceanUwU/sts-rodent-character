package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Net extends AbstractEasyRelic {
    public static final String ID = makeID("Net");
    private static final int PER_JUNK_PLAYED = 6;
    private static final int JUNK_ADDED = 1;

    public Net() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
        counter = 0;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + PER_JUNK_PLAYED + DESCRIPTIONS[1] + JUNK_ADDED + DESCRIPTIONS[2];
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof Junk.JunkCard) {
            if (++counter == PER_JUNK_PLAYED) {
                counter = 0;
                flash();
                pulse = false;
                atb(new RelicAboveCreatureAction(adp(), this));
                atb(new Junk.MakeAction(JUNK_ADDED, Junk.MakeAction.Location.HAND));
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