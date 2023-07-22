package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Piddle extends AbstractRodentCard {
    public final static String ID = makeID("Piddle");

    public Piddle() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard c : p.drawPile.group) {
                    att(new DiscardSpecificCardAction(c, p.drawPile));
                    att(new Grime.Action(c));
                }
            }
        });
    }

    public void upp() {
        isInnate = true;
    }
}