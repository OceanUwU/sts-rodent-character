package oceanrodent.cards.cardvars;

import static oceanrodent.RodentMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanrodent.cards.AbstractRodentCard;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractRodentCard) {
            ((AbstractRodentCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).upgradedSecondMagic;
        }
        return false;
    }
}