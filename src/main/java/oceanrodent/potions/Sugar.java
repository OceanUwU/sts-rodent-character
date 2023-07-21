package oceanrodent.potions;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import oceanrodent.RodentMod;
import oceanrodent.powers.NipPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Sugar extends CustomPotion {
    public static final String POTION_ID = makeID("Sugar");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Sugar() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.ENERGY);
        labOutlineColor = RodentMod.characterColor;
    }

    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("nip")), BaseMod.getKeywordDescription(makeID("nip"))));
    }

    public void use(AbstractCreature abstractCreature) {
        applyToSelf(new NipPower("SugarPower", canUse, potency, name, a -> potionStrings.DESCRIPTIONS[2] + a + potionStrings.DESCRIPTIONS[3], a -> atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(a, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE))));
    }

    public int getPotency(int ascensionlevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new Gunk();
    }
}