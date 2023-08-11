package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

import basemod.BaseMod;

public class RashPounce extends AbstractRodentCard {
    public final static String ID = makeID("RashPounce");

    public RashPounce() {
        this(0);
    }

    public RashPounce(int upgrades) {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 12;
        timesUpgraded = upgrades;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SMASH);
        atb(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
    }

    public void upp() {}
  
    @Override
    public void upgrade() {
        upgradeDamage(4 + timesUpgraded);
        timesUpgraded++;
        upgraded = true;
        name = cardStrings.NAME + "+" + timesUpgraded;
        initializeTitle();
    }
  
    public boolean canUpgrade() {
        return true;
    }
  
    public AbstractCard makeCopy() {
        return new RashPounce(timesUpgraded);
    }
}