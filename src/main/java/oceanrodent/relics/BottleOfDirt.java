package oceanrodent.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;

public class BottleOfDirt extends AbstractEasyRelic {
    public static final String ID = makeID("BottleOfDirt");
    private static final int CARDS_GRIMED = 3;
  
    private boolean cardSelected = true;
    private int griming;

    public BottleOfDirt() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS_GRIMED + DESCRIPTIONS[1];
    }

    public void onEquip() {
        CardGroup grimableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (Grime.canGrime(c))
                grimableCards.addToTop(c);
        if (grimableCards.size() > 0) {
            cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            } 
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            griming = Math.min(CARDS_GRIMED, grimableCards.size());
            AbstractDungeon.gridSelectScreen.open(grimableCards, griming, DESCRIPTIONS[2] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }
  
    public void update() {
        super.update();
        if (!cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == griming) {
            cardSelected = true;
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                Grime.grime(c);
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        } 
    }
}