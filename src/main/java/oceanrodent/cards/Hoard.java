package oceanrodent.cards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import oceanrodent.RodentMod;
import oceanrodent.mechanics.Junk;
import oceanrodent.mechanics.Junk.JunkCard;
import oceanrodent.powers.AbstractEasyPower;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.RodentMod.makeImagePath;
import static oceanrodent.util.Wiz.*;

public class Hoard extends AbstractRodentCard {
    public final static String ID = makeID("Hoard");

    public Hoard() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HoardPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class HoardPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("HoardPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        private static int IDOffset = 0;

        public HoardPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.BUFF, false, owner, amount);
            ID += IDOffset++;
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onVictory() {
            if (amount > 0)
                AbstractDungeon.getCurrRoom().rewards.add(new JunkReward(amount));
        }

        public static class JunkReward extends CustomReward {
            private static final Texture TEXTURE = TexLoader.getTexture(makeImagePath("ui/junkreward.png"));
            
            public JunkReward(int numCards) {
                super(TEXTURE, powerStrings.DESCRIPTIONS[2], RodentMod.JUNKCARDREWARD);
                numCards = Math.min(numCards, Junk.allJunk.size());
                cards = new ArrayList<>();
                for (JunkCard c : Junk.getRandomJunk(AbstractDungeon.cardRng, numCards, true))
                    cards.add(c);
            }

            public JunkReward(ArrayList<AbstractCard> cards) {
                this(0);
                this.cards = cards;
            }

            @Override
            public boolean claimReward() {
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
                    AbstractDungeon.cardRewardScreen.open(cards, this, powerStrings.DESCRIPTIONS[3]);
                    AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
                }
                return false;
            }
        }
    }
}