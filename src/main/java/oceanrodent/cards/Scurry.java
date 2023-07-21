package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Scurry extends AbstractRodentCard {
    public final static String ID = makeID("Scurry");

    public Scurry() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 9;
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        baseThirdMagic = thirdMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DiscardAction(p, p, secondMagic, false));
        applyToSelf(new WreckPower(this, true, thirdMagic, magicNumber, a -> exDesc[0] + a + exDesc[1], a -> atb(new DrawCardAction(a))));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}