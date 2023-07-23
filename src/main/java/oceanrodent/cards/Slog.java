package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.powers.AbstractEasyPower;
import oceanrodent.powers.WreckPower;
import oceanrodent.powers.interfaces.OnFinishWreckage;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Slog extends AbstractRodentCard {
    public final static String ID = makeID("Slog");

    public Slog() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 15;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToEnemy(m, new SlogPower(m, damage));
    }

    public void upp() {
        upgradeDamage(3);
    }

    public static class SlogPower extends AbstractEasyPower implements OnFinishWreckage {
        public static String POWER_ID = makeID("SlogPower");
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    
        public SlogPower(AbstractCreature owner, int amount) {
            super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, true, owner, amount);
        }
        
        public void updateDescription() {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }

        public void onFinishWreckage(WreckPower wreckage) {
            flash();
            atb(new LoseHPAction(owner, adp(), amount, AbstractGameAction.AttackEffect.NONE));
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
    
        public void atEndOfTurn(boolean isPlayer) {
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}