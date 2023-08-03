package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.EnergizedRodent;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class GatherSurroundings extends AbstractRodentCard {
    public final static String ID = makeID("GatherSurroundings");

    public GatherSurroundings() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new WreckPower(this, true, secondMagic, magicNumber, a -> exDesc[0] + a + exDesc[1], a -> applyToSelf(new EnergizedRodent(p, a))));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}