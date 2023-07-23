package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Vault;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Careen extends AbstractRodentCard {
    public final static String ID = makeID("Careen");

    public Careen() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new Vault();
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 6;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.HAND));
        applyToSelf(new WreckPower(this, true, secondMagic, 0, a -> exDesc[0], a -> {
            AbstractCard c = cardsToPreview.makeStatEquivalentCopy();
            p.limbo.group.add(c);
            atb(new NewQueueCardAction(c, m, false, true));
            atb(new UnlimboAction(c, true));
        }));
    }

    public void upp() {
        upgradeSecondMagic(-2);
    }
}