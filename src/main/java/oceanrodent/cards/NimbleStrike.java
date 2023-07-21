package oceanrodent.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Collections;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class NimbleStrike extends AbstractRodentCard {
    public final static String ID = makeID("NimbleStrike");

    public NimbleStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 1;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        ArrayList<AbstractCard> drawCards = p.drawPile.group;
        Collections.shuffle(drawCards);
        atb(new SelectCardsAction(drawCards, magicNumber, exDesc[0], cards -> cards.stream().forEach(c -> att(new DiscardSpecificCardAction(c, p.drawPile)))));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}