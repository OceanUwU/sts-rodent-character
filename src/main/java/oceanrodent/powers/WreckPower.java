package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.util.function.Consumer;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class WreckPower extends AbstractEasyPower {
    public static String POWER_ID = makeID("Wreck");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int idOffset = 0;

    public AbstractCard.CardType cardType;
    private String effectText;
    private Consumer<Integer> effect;
    private int power;
    
    public WreckPower(AbstractCard from, String effectText, boolean buff, int cardsNeeded, int power, Consumer<Integer> effect) {
        super(POWER_ID+from.type.toString(), powerStrings.NAME + ": " + from.name, buff ? PowerType.BUFF : PowerType.DEBUFF, true, adp(), cardsNeeded);
        this.power = power;
        this.effectText = effectText;
        cardType = from.type;
        ID += idOffset++;
        updateDescription();
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == cardType) {
            reducePower(1);
            if (amount == 0) {
                flash();
                atb(new RemoveSpecificPowerAction(owner, owner, this));
                effect.accept(power);
            }
        } 
    }
  
    public void atEndOfTurn(boolean isPlayer) {
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
        
    public void updateDescription() {
        if (cardType == null) return;
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1 + cardType.ordinal() * 2 + (amount == 1 ? 0 : 1)] + powerStrings.DESCRIPTIONS[7] + effectText.replace("!M!", Integer.toString(power));
    }
}