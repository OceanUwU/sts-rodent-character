package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Sidestep extends AbstractRodentCard {
    public final static String ID = makeID("Sidestep");

    public Sidestep() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 0;
        hardy = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {return false;}

    public void onHardyReturn() {
        att(new DrawCardAction(secondMagic));
        att(new GainEnergyAction(magicNumber));
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}