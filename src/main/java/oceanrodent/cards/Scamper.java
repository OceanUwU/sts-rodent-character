package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Scamper extends AbstractRodentCard {
    public final static String ID = makeID("Scamper");

    public Scamper() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 11;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
        applyToSelf(new WreckPower(this, true, secondMagic, magicNumber, a -> exDesc[0] + a + exDesc[1], a -> atb(new GainEnergyAction(a))));
    }

    public void upp() {
        upgradeDamage(4);
    }
}