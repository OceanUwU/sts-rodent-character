package oceanrodent.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.mechanics.Junk;
import oceanrodent.powers.AbstractEasyPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Fling extends AbstractRodentCard {
    public final static String ID = makeID("Fling");

    public Fling() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 3;
        previewsJunk = true;
        cardsToPreview = dummyCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new Junk.MakeAction(magicNumber, Junk.MakeAction.Location.HAND));
        applyToEnemy(m, new FlingPower(m, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }

    public static class FlingPower extends AbstractEasyPower {
        public static String POWER_ID = makeID("FlingPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public FlingPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, true, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onPlayerDraw() {
            flash();
            atb(new LoseHPAction(owner, null, amount));
        }
    
        public void atEndOfTurn(boolean isPlayer) {
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }

        @SpirePatch(clz=AbstractPlayer.class, method="draw", paramtypez={int.class})
        public static class Trigger {
            @SpireInsertPatch(rloc=14)
            public static void Insert(AbstractPlayer __instance) {
                forAllMonstersLiving(mo -> mo.powers.forEach(p -> {
                    if (p instanceof FlingPower)
                        ((FlingPower)p).onPlayerDraw();
                }));
            }
        }
    }
}