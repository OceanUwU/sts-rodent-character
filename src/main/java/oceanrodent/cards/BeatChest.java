package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class BeatChest extends AbstractRodentCard {
    public final static String ID = makeID("BeatChest");

    public BeatChest() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
        hardy = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 2; i++)
            dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    public void onHardyReturn() {
        applyToSelfTop(new StrengthPower(adp(), magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
    }
}