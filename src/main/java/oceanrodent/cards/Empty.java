package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Empty extends AbstractRodentCard {
    public final static String ID = makeID("Empty");

    public Empty() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.DRAW));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}