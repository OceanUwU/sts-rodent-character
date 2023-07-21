package oceanrodent.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.characters.TheRodent;
import oceanrodent.powers.AbstractEasyPower;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class TallForm extends AbstractRodentCard {
    public final static String ID = makeID("TallForm");

    public TallForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        isEthereal = true;
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TallFormPower(p, magicNumber));
    }

    public void upp() {
        isEthereal = false;
    }

    public static class TallFormPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("TallFormPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public TallFormPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }

        public void onInitialApplication() {
            if (adp() instanceof TheRodent)
                ((TheRodent)adp()).loadNewAnimation("rattall");
        }

        public void onVictory() {
            if (adp() instanceof TheRodent)
                ((TheRodent)adp()).loadNewAnimation("rat");
        }

        public void onUseCard(AbstractCard c, UseCardAction action) {
            AbstractMonster m = getRandomEnemy();
            if (m != null)
                applyToEnemy(m, new Encheesed(m, amount));
            flash();
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
    }
}