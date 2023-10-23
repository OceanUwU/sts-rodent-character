package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Pilfer extends AbstractRodentCard {
    public final static String ID = makeID("Pilfer");

    public Pilfer() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
        previewsJunk = true;
        cardsToPreview = dummyCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, secondMagic, false));
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.DRAW));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }
}