package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class TheStash extends AbstractRodentCard {
    public final static String ID = makeID("TheStash");

    public TheStash() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse() {
        cantUseMessage = exDesc[0];
        return false;
    }

    public void onHardyReturn() {
        att(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.HAND, cards -> {
            for (Junk.JunkCard junk : cards)
                Grime.grime(junk, secondMagic);
        }));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}