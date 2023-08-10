package oceanrodent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;

import static oceanrodent.RodentMod.makeID;

public class DiscardFromDrawPile extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("DiscardFromDrawPile"));
    private static final String[] TEXT = uiStrings.TEXT;
    
    private float startingDuration;
    
    public DiscardFromDrawPile(int numCards) {
        amount = numCards;
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    private void discardThem(ArrayList<AbstractCard> cards) {
        for (AbstractCard c : cards)
            addToTop(new DiscardSpecificCardAction(c, AbstractDungeon.player.drawPile)); 
    }
    
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        } 
        if (duration == startingDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                isDone = true;
                return;
            }
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                tmpGroup.addToRandomSpot(c);
            if (tmpGroup.size() <= amount) {
                discardThem(tmpGroup.group);
                isDone = true;
                return;
            } else {
                AbstractDungeon.gridSelectScreen.open(tmpGroup, amount, false, TEXT[0]);
                AbstractDungeon.gridSelectScreen.forClarity = false;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            discardThem(AbstractDungeon.gridSelectScreen.selectedCards);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}