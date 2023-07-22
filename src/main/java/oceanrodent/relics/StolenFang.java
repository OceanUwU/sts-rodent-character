package oceanrodent.relics;

import oceanrodent.characters.TheRodent;

import static oceanrodent.RodentMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class StolenFang extends AbstractEasyRelic {
    public static final String ID = makeID("StolenFang");
    private static final int CARDS_UPGRADED = 3;

    public StolenFang() {
        super(ID, RelicTier.STARTER, LandingSound.HEAVY, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS_UPGRADED + DESCRIPTIONS[1];
    }

    public void atBattleStart() {
        counter = CARDS_UPGRADED;
    }

    public void onCardDraw(AbstractCard drawnCard) {
        if (!grayscale && !drawnCard.upgraded && drawnCard.canUpgrade()) {
            drawnCard.upgrade();
            drawnCard.superFlash();
            flash();
            if (--counter <= 0) {
                counter = -1;
                grayscale = true;
            }
        }
    }

    public void onVictory() {
        counter = -1;
        grayscale = false;
    }
}