package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class BashAndDash extends AbstractRodentCard {
    public final static String ID = makeID("BashAndDash");

    public BashAndDash() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 14;
        baseSecondMagic = secondMagic = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, damage, damageTypeForTurn);
        AbstractRodentCard c = this;
        atb(new AbstractGameAction() {
            public void update() {
                isDone = true;
                if (m.isDeadOrEscaped()) return;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
                m.damage(info);
                if (m.lastDamageTaken > 0) {
                    applyToSelfTop(new WreckPower(c, true, secondMagic, m.lastDamageTaken, a -> exDesc[0] + a + exDesc[1], a -> atb(new GainBlockAction(p, p, a))));
                    if (m.hb != null)
                        att(new VFXAction(new WallopEffect(m.lastDamageTaken, m.hb.cX, m.hb.cY)));
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                    AbstractDungeon.actionManager.clearPostCombatActions();
                else
                    att(new WaitAction(0.1f));
            }
        });
    }

    public void upp() {
        upgradeSecondMagic(-1);
    }
}