package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Parkour extends AbstractRodentCard {
    public final static String ID = makeID("Parkour");

    public Parkour() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 7;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new WreckPower(this, true, secondMagic, block, a -> exDesc[0] + a + exDesc[1], a -> atb(new GainBlockAction(p, p, a))));
    }

    public void upp() {
        upgradeBlock(2);
    }
}