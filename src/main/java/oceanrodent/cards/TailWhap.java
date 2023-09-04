package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class TailWhap extends AbstractRodentCard {
    public final static String ID = makeID("TailWhap");

    public TailWhap() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseBlock = 8;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToSelf(new WreckPower(this, true, secondMagic, block, a -> exDesc[0] + a + exDesc[1], a -> atb(new GainBlockAction(p, p, a))));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}