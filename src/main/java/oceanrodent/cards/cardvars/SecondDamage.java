package oceanrodent.cards.cardvars;

import static oceanrodent.RodentMod.makeID;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanrodent.cards.AbstractRodentCard;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractRodentCard) {
            ((AbstractRodentCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractRodentCard) {
            return ((AbstractRodentCard) card).upgradedSecondDamage;
        }
        return false;
    }
}