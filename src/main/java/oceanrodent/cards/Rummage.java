package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Rummage extends AbstractRodentCard {
   public final static String ID = makeID("Rummage");

    public Rummage() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.DRAW));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}