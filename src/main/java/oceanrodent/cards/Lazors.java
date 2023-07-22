package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Lazors extends AbstractRodentCard {
    public final static String ID = makeID("Lazors");

    public Lazors() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 13;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            atb(new AbstractGameAction() {
                public void update() {
                    isDone = true;
                    AbstractMonster target = getRandomEnemy();
                    if (target == null) return;
                    dmgTop(target, AbstractGameAction.AttackEffect.NONE);
                    att(new VFXAction(new SmallLaserEffect(target.hb.cX, target.hb.cY, p.hb.cX, p.hb.cY), 0.1f));
                    att(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.8F));
                }
            });
    }

    public void upp() {
        upgradeDamage(3);
    }
}