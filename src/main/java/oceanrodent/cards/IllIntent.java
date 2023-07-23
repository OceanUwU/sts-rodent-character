package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import oceanrodent.powers.AbstractEasyPower;
import oceanrodent.powers.WreckPower;
import oceanrodent.powers.interfaces.OnFinishWreckage;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class IllIntent extends AbstractRodentCard {
    public final static String ID = makeID("IllIntent");

    public IllIntent() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 1;
        baseThirdMagic = thirdMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IllIntentPower(p, magicNumber));
        applyToSelf(new WreckPower(this, true, thirdMagic, secondMagic, a -> exDesc[0] + a + exDesc[1], a -> {
            for (int i = 0; i < a; i++)
                atb(new ChannelAction(new Lightning()));
        }));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }

    public static class IllIntentPower extends AbstractEasyPower implements OnFinishWreckage {
        public static String POWER_ID = makeID("IllIntentPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public IllIntentPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onFinishWreckage(WreckPower wreckage) {
            flash();
            atb(new GainBlockAction(owner, owner, amount));
        }
    }
}