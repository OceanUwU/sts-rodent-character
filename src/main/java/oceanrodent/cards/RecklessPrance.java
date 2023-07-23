package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class RecklessPrance extends AbstractRodentCard {
    public final static String ID = makeID("RecklessPrance");

    public RecklessPrance() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard c : p.hand.group)
                    att(new Grime.TarnishAction(c));
            }
        });
        atb(new DiscardAction(p, p, magicNumber, false));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}