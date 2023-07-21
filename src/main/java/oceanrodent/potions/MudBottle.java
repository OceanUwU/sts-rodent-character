package oceanrodent.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import oceanrodent.RodentMod;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class MudBottle extends CustomPotion {
    public static final String POTION_ID = makeID("MudBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public MudBottle() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.ENERGY);
        labOutlineColor = RodentMod.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        if (potency == 1) description = potionStrings.DESCRIPTIONS[0];
        else description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("grimy")), BaseMod.getKeywordDescription(makeID("grimy"))));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("tarnished")), BaseMod.getKeywordDescription(makeID("tarnished"))));
    }

    public void use(AbstractCreature abstractCreature) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard c : adp().hand.group) {
                    att(new Grime.TarnishAction(c, potency));
                    att(new Grime.Action(c, potency));
                }
            }
        });
    }

    public int getPotency(int ascensionlevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new Gunk();
    }
}