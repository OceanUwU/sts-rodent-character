package oceanrodent.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Ratpack extends AbstractRodentCard {
    public final static String ID = makeID("Ratpack");

    public Ratpack() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
        atb(new BetterDiscardPileToHandAction(magicNumber));
        atb(new DrawCardAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}