package oceanrodent.cards.cardvars;

import static oceanrodent.RodentMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanrodent.cards.AbstractRodentCard;

public class ThirdMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).isThirdMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).thirdMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractRodentCard) {
            ((AbstractRodentCard) card).isThirdMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).baseThirdMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).upgradedThirdMagic;
        }
        return false;
    }
}