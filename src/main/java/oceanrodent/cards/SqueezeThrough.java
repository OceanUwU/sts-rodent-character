package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class SqueezeThrough extends AbstractRodentCard {
    public final static String ID = makeID("SqueezeThrough");

    public SqueezeThrough() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 18;
        baseSecondMagic = secondMagic = 2;
        cardsToPreview = new Splinter();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(cardsToPreview, secondMagic);
        blck();
    }

    public void upp() {
        upgradeBlock(6);
    }
}