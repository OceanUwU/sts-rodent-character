package oceanrodent.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;

public class Stand extends AbstractRodentCard {
    public final static String ID = makeID("Stand");

    public Stand() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (baseBlock > -1)
            blck();
    }

    public void upp() {
        baseBlock = 3;
    }
}