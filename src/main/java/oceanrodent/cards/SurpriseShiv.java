package oceanrodent.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class SurpriseShiv extends AbstractRodentCard {
    public final static String ID = makeID("SurpriseShiv");

    public SurpriseShiv() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 2;
        baseMagicNumber = magicNumber = 2;
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }

    @SpirePatch(clz=CardGroup.class, method="moveToExhaustPile")
    public static class TriggerIt {
        public static void Prefix(CardGroup __instance, AbstractCard exhausted) {
            for (AbstractCard c : adp().hand.group)
                if (c instanceof SurpriseShiv && c != exhausted) {
                    c.flash();
                    att(new ModifyDamageAction(c.uuid, c.magicNumber));
                }
        }
    }
}