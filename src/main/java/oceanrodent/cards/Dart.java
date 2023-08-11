package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Dart extends AbstractRodentCard {
    public final static String ID = makeID("Dart");

    public Dart() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new WreckPower(this, true, secondMagic, magicNumber, a -> exDesc[0] + a + exDesc[1], a -> forAllMonstersLiving(mo -> applyToEnemy(mo, new WeakPower(mo, a, false)))));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}