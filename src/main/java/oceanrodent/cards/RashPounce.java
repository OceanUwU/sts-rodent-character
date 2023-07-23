package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

import basemod.BaseMod;

public class RashPounce extends AbstractRodentCard {
    public final static String ID = makeID("RashPounce");

    public RashPounce() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SMASH);
        atb(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
    }

    public void upp() {
        upgradeDamage(4);
    }
}