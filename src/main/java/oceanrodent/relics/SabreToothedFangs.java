package oceanrodent.relics;

import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanrodent.characters.TheRodent;
import org.apache.commons.lang3.StringUtils;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class SabreToothedFangs extends AbstractEasyRelic {
    public static final String ID = makeID("SabreToothedFangs");

    public SabreToothedFangs() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.canUpgrade()) {
            if (!c.exhaust && c.type != AbstractCard.CardType.POWER)
                flash();
            atb(new UpgradeSpecificCardAction(c));
        }
    }
    
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(StolenFang.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++)
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, StolenFang.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
        } else
            super.obtain();
    }
  
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(StolenFang.ID);
    }
}