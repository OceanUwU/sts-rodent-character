package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Encircle extends AbstractRodentCard {
    public final static String ID = makeID("Encircle");

    public Encircle() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (adp().hand.contains(this))
            att(new ModifyBlockAction(uuid, magicNumber));
    }

    public void upp() {
        hardy = true;
    }
}