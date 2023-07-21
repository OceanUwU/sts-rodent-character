package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class BinLidShield extends AbstractRodentCard {
   public final static String ID = makeID("BinLidShield");

    public BinLidShield() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 14;
        baseMagicNumber = magicNumber = 5;
        hardy = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void onHardyReturn() {
        att(new GainBlockAction(adp(), adp(), magicNumber));
    }

    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}