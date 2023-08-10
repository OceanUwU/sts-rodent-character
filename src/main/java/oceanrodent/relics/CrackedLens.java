package oceanrodent.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
            atb(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    att(new SelectCardsAction(adp().drawPile.group, CARDS_DISCARDED, DESCRIPTIONS[2], true, c -> true, cards -> cards.forEach(c -> att(new DiscardSpecificCardAction(c, adp().drawPile)))));
                }
            });
            //atb(new DiscardFromDrawPile(CARDS_DISCARDED));
        } 
    }
}