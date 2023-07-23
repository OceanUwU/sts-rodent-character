package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class ZipAround extends AbstractRodentCard {
    public final static String ID = makeID("ZipAround");

    public ZipAround() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 1;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            atb(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    AbstractMonster target = getRandomEnemy();
                    if (target == null) return;
                    dmgTop(target, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                }
            });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}