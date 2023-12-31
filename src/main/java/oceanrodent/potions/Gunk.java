package oceanrodent.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import oceanrodent.RodentMod;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Gunk extends CustomPotion {
    public static final String POTION_ID = makeID("Gunk");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Gunk() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.ENERGY);
        labOutlineColor = RodentMod.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("junk")), BaseMod.getKeywordDescription(makeID("junk"))));
    }

    public void use(AbstractCreature abstractCreature) {
        atb(new Junk.MakeAction(potency, Junk.MakeAction.Location.HAND));
    }

    public int getPotency(int ascensionlevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new Gunk();
    }
}