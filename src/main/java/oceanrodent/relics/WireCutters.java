package oceanrodent.relics;

import basemod.abstracts.CustomReward;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import oceanrodent.RodentMod;
import oceanrodent.characters.TheRodent;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.makeImagePath;
import static oceanrodent.RodentMod.makeID;

public class WireCutters extends AbstractEasyRelic {
    public static final String ID = makeID("WireCutters");
    private static final int HP_LOSS = 10;

    public WireCutters() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_LOSS + DESCRIPTIONS[1];
    }

    @SpirePatch(clz=CombatRewardScreen.class, method="setupItemReward")
    public static class AddReward {
        @SpireInsertPatch(rloc=21)
        public static void Postfix(CombatRewardScreen __instance) {
            if (AbstractDungeon.player.hasRelic(ID))
                __instance.rewards.add(new RemoveCardReward());
        }
    }

    public static class RemoveCardReward extends CustomReward { //taken from downfall thanks lmao
        public static final String REWARD_ID = makeID("RemoveCardReward");
        public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(REWARD_ID).TEXT;

        public RemoveCardReward() {
            super(TexLoader.getTexture(makeImagePath("ui/removereward.png")), TEXT[0] + HP_LOSS + TEXT[1], RodentMod.REMOVECARD);
        }

        @Override
        public boolean claimReward() {
            AbstractDungeon.player.damage(new DamageInfo(null, HP_LOSS, DamageInfo.DamageType.HP_LOSS));
            if (AbstractDungeon.player.isDead) return true;
            if (AbstractDungeon.player.hasRelic(ID))
                AbstractDungeon.player.getRelic(ID).flash();
            RodentMod.choosingRemoveCard = true;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : AbstractDungeon.player.masterDeck.getPurgeableCards().group)
                tmp.addToTop(card);
            if (tmp.group.isEmpty())
                RodentMod.choosingRemoveCard = false;
            else
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[2], false, false, false, true);
            return true;
        }
    }
}