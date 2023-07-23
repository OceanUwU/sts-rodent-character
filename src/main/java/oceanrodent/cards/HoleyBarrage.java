package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.CheeseSeal;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class HoleyBarrage extends AbstractRodentCard {
    public final static String ID = makeID("HoleyBarrage");

    public HoleyBarrage() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(mo -> {
            applyToEnemy(mo, new Encheesed(mo, magicNumber));
            applyToEnemy(mo, new CheeseSeal(mo, 1));
        });
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}