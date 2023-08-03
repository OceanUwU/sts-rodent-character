package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;

public class SludgeSmash extends AbstractRodentCard {
    public final static String ID = makeID("SludgeSmash");

    public SludgeSmash() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 7;
        baseDamage = 7;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    public void upp() {
        upgradeBlock(2);
        upgradeDamage(2);
    }
}