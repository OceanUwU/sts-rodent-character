package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class TakeAWhiff extends AbstractRodentCard {
    public final static String ID = makeID("TakeAWhiff");

    public TakeAWhiff() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                att(new GainEnergyAction((int)p.hand.group.stream().filter(c -> c instanceof Junk.JunkCard).count()));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}