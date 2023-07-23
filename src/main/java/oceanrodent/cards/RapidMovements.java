package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ChokePower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class RapidMovements extends AbstractRodentCard {
    public final static String ID = makeID("RapidMovements");

    public RapidMovements() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new ChokePower(m, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}