package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Hindstrike extends AbstractRodentCard {
    public final static String ID = makeID("Hindstrike");

    public Hindstrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 3;
        baseMagicNumber = magicNumber = 1;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++)
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        atb(new SelectCardsInHandAction(magicNumber, exDesc[0], false, false, c -> Grime.canGrime(c), cards -> cards.stream().forEach(c -> att(new Grime.Action(c)))));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}