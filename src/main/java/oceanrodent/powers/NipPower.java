package oceanrodent.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.util.function.Consumer;
import java.util.function.Function;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class NipPower extends AbstractEasyPower {
    private static String STRINGS_ID = makeID("Nip");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(STRINGS_ID);

    public AbstractCard.CardType cardType;
    private Function<Integer, String> effectText;
    private Consumer<Integer> effect;
    
    public NipPower(String ID, boolean buff, int amount, String name, Function<Integer, String> effectText, Consumer<Integer> effect) {
        super(makeID(ID), powerStrings.NAME + ": " + name, buff ? PowerType.BUFF : PowerType.DEBUFF, true, adp(), amount);
        this.effectText = effectText;
        this.effect = effect;
        updateDescription();
    }
  
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        effect.accept(amount);
    }
  
    public void atEndOfTurn(boolean isPlayer) {
        atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
        
    public void updateDescription() {
        if (effectText == null) return;
        description = powerStrings.DESCRIPTIONS[0] + effectText.apply(amount);
    }
}