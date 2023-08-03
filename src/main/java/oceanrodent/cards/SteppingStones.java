package oceanrodent.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import oceanrodent.mechanics.Grime;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class SteppingStones extends AbstractRodentCard {
    public final static String ID = makeID("SteppingStones");

    public SteppingStones() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SteppingStonesPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }

    public static class SteppingStonesPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("SteppingStonesPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public SteppingStonesPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            if (amount == 1) description = powerStrings.DESCRIPTIONS[0];
            else description = powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
        }

        public void onManualDiscardCard(AbstractCard card) {
            flash();
            atb(new Grime.Action(card, amount));
        }

        @SpirePatch(clz=GameActionManager.class, method="incrementDiscard")
        public static class Trigger {
            @SpireInsertPatch(rloc=4)
            public static void Insert() {
                for (AbstractPower p : adp().powers)
                    if (p instanceof SteppingStonesPower)
                        ((SteppingStonesPower)p).onManualDiscardCard(adp().discardPile.getTopCard());
            }
        }
    }
}