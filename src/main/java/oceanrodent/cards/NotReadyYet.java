package oceanrodent.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class NotReadyYet extends AbstractRodentCard {
    public final static String ID = makeID("NotReadyYet");

    public NotReadyYet() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                att(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
                for (int i = 0; i < p.hand.size(); i++)
                    att(new GainBlockAction(p, p, block));
            }
        });
    }

    public void upp() {
        upgradeBlock(1);
    }
}