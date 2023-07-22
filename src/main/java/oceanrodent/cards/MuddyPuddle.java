package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class MuddyPuddle extends AbstractRodentCard {
    public final static String ID = makeID("MuddyPuddle");

    public MuddyPuddle() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                doubleGrimeOnGroup(p.hand);
                doubleGrimeOnGroup(p.drawPile);
                doubleGrimeOnGroup(p.discardPile);
                doubleGrimeOnGroup(p.exhaustPile);
            }
        });
    }

    private static void doubleGrimeOnGroup(CardGroup g) {
        for (AbstractCard c : g.group)
            Grime.grime(c, Grime.grimeAmount(c));
    }

    public void upp() {
        exhaust = false;
    }
}