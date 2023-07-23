package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

import basemod.BaseMod;

public class RecyclingBin extends AbstractRodentCard {
    public final static String ID = makeID("RecyclingBin");

    public RecyclingBin() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                att(new Junk.MakeAction(p.hand.size() * magicNumber, Junk.MakeAction.Location.HAND));
                att(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
            }
        });
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}