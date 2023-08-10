package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;
import oceanrodent.powers.NipPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class OopsDroppedEm extends AbstractRodentCard {
    public final static String ID = makeID("OopsDroppedEm");

    public OopsDroppedEm() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
        cardsToPreview = new Stand();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(cardsToPreview, secondMagic);
        applyToSelf(new NipPower("DroppedEmPower", true, magicNumber, name, a -> exDesc[0] + a + exDesc[a == 1 ? 1 : 2], a -> atb(new Grime.GrimeRandomAction(a))));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}