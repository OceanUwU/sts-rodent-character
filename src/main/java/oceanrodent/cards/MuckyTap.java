package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class MuckyTap extends AbstractRodentCard {
    public final static String ID = makeID("MuckyTap");

    public MuckyTap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new SelectCardsInHandAction(magicNumber, exDesc[0], false, false, c -> Grime.canGrime(c), cards -> cards.forEach(c -> {
            att(new Grime.Action(c));
            att(new Grime.TarnishAction(c));
        })));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}