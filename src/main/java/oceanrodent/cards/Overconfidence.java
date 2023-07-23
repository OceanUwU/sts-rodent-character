package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;

public class Overconfidence extends AbstractRodentCard {
    public final static String ID = makeID("Overconfidence");

    public Overconfidence() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 28;
        baseSecondDamage = secondDamage = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.currentHealth > (float)m.currentHealth * 0.5f)
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        else
            altDmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    public void upp() {
        upgradeDamage(7);
        upgradeSecondDamage(1);
    }
}