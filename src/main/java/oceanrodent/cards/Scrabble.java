package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Scrabble extends AbstractRodentCard {
    public final static String ID = makeID("Scrabble");

    public Scrabble() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        randomDmg(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new DiscardAction(p, p, 1, false));
    }

    public void upp() {
        upgradeDamage(2);
    }
}