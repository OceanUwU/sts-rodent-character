package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.actions.EasyXCostAction;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Cower extends AbstractRodentCard {
    public final static String ID = makeID("Cower");

    public Cower() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        cardsToPreview = new Safety();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (x, v) -> {
            AbstractCard c = cardsToPreview.makeStatEquivalentCopy();
            Grime.grime(c, x);
            att(new MakeTempCardInHandAction(c));
            return true;
        }));
    }

    public void upp() {
        cardsToPreview.upgrade();
    }
}