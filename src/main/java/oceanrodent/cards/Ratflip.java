package oceanrodent.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import oceanrodent.characters.TheRodent;
import oceanrodent.powers.Encheesed;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class Ratflip extends AbstractRodentCard {
    public final static String ID = makeID("Ratflip");

    public Ratflip() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TheRodent)
            vfx(new RatflipEffect((TheRodent)p));
        blck();
        applyToEnemy(m, new Encheesed(m, magicNumber));
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(1);
    }

    public static class RatflipEffect extends AbstractGameEffect {
        private static final float DUR = 0.9f;
        private static final float JUMP_HEIGHT = 200f;

        private TheRodent rat;
        private Bone rootBone;
        private float timer = 0f;

        public RatflipEffect(TheRodent rat) {
            this.rat = rat;
            rootBone = rat.getSkeleton().getRootBone();
        }

        public void update() {
            timer += Gdx.graphics.getDeltaTime();
            float progress = timer / DUR;
            if (progress >= 1f) {
                rat.animY = 0f;
                rootBone.setRotation(0f);
                isDone = true;
            } else {
                rat.animY = JUMP_HEIGHT * (progress < 0.5f ? 1f - (float)Math.pow(1f - progress * 2f, 5) : 1f - (float)Math.pow((progress - 0.5f) * 2f, 5));
                rootBone.setRotation(360f * (1f - (float)Math.pow(1f - progress, 2)));
            }
        }

        public void render(SpriteBatch sb) {}
        public void dispose() {}
    }
}