package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Belch extends AbstractRodentCard {
    public final static String ID = makeID("Belch");

    public Belch() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 2;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (upgraded)
            atb(new SelectCardsInHandAction(magicNumber, exDesc[0], false, false, c -> Grime.canGrime(c), cards -> cards.stream().forEach(c -> att(new Grime.Action(c)))));
        else
            atb(new Grime.GrimeRandomAction(magicNumber));
    }

    public void upp() {
        upgradeBlock(1);
    }
}