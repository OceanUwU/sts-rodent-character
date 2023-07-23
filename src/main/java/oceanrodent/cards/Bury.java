package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Bury extends AbstractRodentCard {
    public final static String ID = makeID("Bury");

    public Bury() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, exDesc[0], false, false, c -> c.canUpgrade(), cards -> {
            if (cards.size() > 0) {
                AbstractCard c = cards.get(0);
                if (Grime.canGrime(c))
                    applyToSelfTop(new WreckPower(this, true, secondMagic, magicNumber,
                        a -> a == 1 ? exDesc[1] + c.name + exDesc[2] : exDesc[1] + c.name + exDesc[3] + a + exDesc[4],
                        a -> atb(new Grime.Action(c, a))));
                att(new UpgradeSpecificCardAction(c));
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}