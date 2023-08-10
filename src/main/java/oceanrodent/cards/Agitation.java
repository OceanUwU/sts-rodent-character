package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import oceanrodent.powers.NipPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Agitation extends AbstractRodentCard {
    public final static String ID = makeID("Agitation");

    public Agitation() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < secondMagic; i++)
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new NipPower("Agitation", true, magicNumber, name, a -> exDesc[0] + a + exDesc[1], a -> applyToSelf(new VigorPower(p, a))));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}