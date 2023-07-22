package oceanrodent.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FirePotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.ArrayList;
import oceanrodent.mechanics.Junk;
import oceanrodent.mechanics.Junk.JunkCard;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Hoard extends AbstractRodentCard {
    public final static String ID = makeID("Hoard");

    public Hoard() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(null);
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class HoardPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("HoardPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        private static int IDOffset = 0;

        public HoardPower(AbstractCreature owner, int amount) {
            super(POWER_ID+IDOffset++, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onVictory() {
            if (amount > 0) {
                amount = Math.min(amount, Junk.allJunk.size());
                RewardItem reward = new RewardItem(new FirePotion());
                reward.type = RewardItem.RewardType.CARD;
                ReflectionHacks.setPrivate(reward, RewardItem.class, "isBoss", false);
                ArrayList<AbstractCard> cards = new ArrayList<>();
                for (JunkCard c : Junk.getRandomJunk(AbstractDungeon.cardRng, amount, true))
                    cards.add(c);
                reward.cards = cards;
                reward.text = powerStrings.DESCRIPTIONS[2];
                AbstractDungeon.getCurrRoom().rewards.add(reward);
            }
        }
    }
}