package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class GetStuckIn extends AbstractRodentCard {
    public final static String ID = makeID("GetStuckIn");

    public GetStuckIn() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean upped = upgraded;
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                for (AbstractCard c : p.hand.group) {
                    if (upped) c.upgrade();
                    att(new Grime.Action(c));
                }
            }
        });
    }

    public void upp() {}
}