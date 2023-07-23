package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.function.Consumer;
import java.util.function.Function;
import oceanrodent.powers.interfaces.OnFinishWreckage;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class WreckPower extends AbstractEasyPower {
    public static String POWER_ID = makeID("Wreck");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int idOffset = 0;

    public AbstractCard.CardType cardType;
    private Function<Integer, String> effectText;
    private Consumer<Integer> effect;
    private int power;
    
    public WreckPower(AbstractCard from, boolean buff, int cardsNeeded, int power, Function<Integer, String> effectText, Consumer<Integer> effect) {
        super(POWER_ID+from.type.toString(), powerStrings.NAME + ": " + from.name, buff ? PowerType.BUFF : PowerType.DEBUFF, true, adp(), cardsNeeded);
        this.power = power;
        this.effectText = effectText;
        this.effect = effect;
        cardType = from.type;
        ID += idOffset++;
        updateDescription();
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == cardType) {
            reducePower(1);
            updateDescription();
            if (amount == 0) {
                flash();
                atb(new RemoveSpecificPowerAction(owner, owner, this));
                effect.accept(power);
                for (AbstractPower p : adp().powers)
                    if (p instanceof OnFinishWreckage)
                        ((OnFinishWreckage)p).onFinishWreckage(this);
                forAllMonstersLiving(mo -> {
                    for (AbstractPower p : mo.powers)
                        if (p instanceof OnFinishWreckage)
                            ((OnFinishWreckage)p).onFinishWreckage(this);
                });
                
            }
        } 
    }
  
    public void atEndOfTurn(boolean isPlayer) {
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
        
    public void updateDescription() {
        if (cardType == null) return;
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1 + cardType.ordinal() * 2 + (amount == 1 ? 0 : 1)] + powerStrings.DESCRIPTIONS[7] + effectText.apply(power);
    }
}