package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Grime;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Defend extends AbstractRodentCard {
    public final static String ID = makeID("Defend");

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new SelectCardsInHandAction(1, "Tarnish", cards -> {
            if (cards.size() > 0)
                att(new Grime.TarnishAction(cards.get(0)));
        }));
    }

    public void upp() {
        upgradeBlock(3);
    }
}