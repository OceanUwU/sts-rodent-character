package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class BinLidSwing extends AbstractRodentCard {
    public final static String ID = makeID("BinLidSwing");

    public BinLidSwing() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 13;
        baseDamage = 12;
        baseSecondMagic = secondMagic = 2;
        damageType = DamageInfo.DamageType.HP_LOSS;
        damageTypeForTurn = damageType;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new WreckPower(this, true, secondMagic, damage, a -> exDesc[0] + a + exDesc[1], a -> atb(new DamageAllEnemiesAction(p, a, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE))));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeDamage(3);
    }
}