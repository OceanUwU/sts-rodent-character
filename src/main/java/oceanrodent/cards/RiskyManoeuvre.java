package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class RiskyManoeuvre extends AbstractRodentCard {
    public final static String ID = makeID("RiskyManoeuvre");

    public RiskyManoeuvre() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseSecondMagic = secondMagic = 1;
        cardsToPreview = new Splinter();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        shuffleIn(cardsToPreview, secondMagic);
    }

    public void upp() {
        upgradeDamage(5);
    }
}