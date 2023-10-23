package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Mine extends AbstractRodentCard {
    public final static String ID = makeID("Mine");

    public Mine() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 1;
        previewsJunk = true;
        cardsToPreview = dummyCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.HAND));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}