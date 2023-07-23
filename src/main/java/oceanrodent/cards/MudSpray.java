package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class MudSpray extends AbstractRodentCard {
    public final static String ID = makeID("MudSpray");

    public MudSpray() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        applyToSelf(new WreckPower(this, true, secondMagic, 0, a -> exDesc[0], a -> atb(new Grime.GroupAction(p.hand))));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeSecondMagic(-1);
    }
}