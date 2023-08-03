package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class CheesedToMeetYou extends AbstractRodentCard {
    public final static String ID = makeID("CheesedToMeetYou");

    public CheesedToMeetYou() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToEnemy(m, new Encheesed(m, magicNumber));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(2);
    }
}