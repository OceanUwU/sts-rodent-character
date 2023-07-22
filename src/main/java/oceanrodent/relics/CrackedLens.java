package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import oceanrodent.actions.DiscardFromDrawPile;
import oceanrodent.characters.TheRodent;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class CrackedLens extends AbstractEasyRelic {
    public static final String ID = makeID("CrackedLens");
    private static final int CARDS_DISCARDED = 2;
  
    private boolean activated = false;

    public CrackedLens() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS_DISCARDED + DESCRIPTIONS[1];
    }
  
    public void atBattleStartPreDraw() {
        activated = false;
    }
  
    public void atTurnStartPostDraw() {
        if (!activated) {
            activated = true;
            flash();
            atb(new RelicAboveCreatureAction(adp(), this));
            atb(new DiscardFromDrawPile(CARDS_DISCARDED));
        } 
    }
}