package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.NipBlockPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class HereThereEverywhere extends AbstractRodentCard {
    public final static String ID = makeID("HereThereEverywhere");

    public HereThereEverywhere() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++)
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        applyToSelf(NipBlockPower.make(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}