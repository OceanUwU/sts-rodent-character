package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Splinter extends AbstractRodentCard {
    public final static String ID = makeID("Splinter");

    public Splinter() {
        super(ID, -2, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        hardy = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upp() {}
    public boolean canUpgrade() {return false;}

    public void onHardyReturn() {
        att(new ExhaustSpecificCardAction(this, adp().hand));
    }
}