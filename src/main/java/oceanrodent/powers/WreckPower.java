package oceanrodent.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.function.Consumer;
import java.util.function.Function;
import oceanrodent.powers.interfaces.OnFinishWreckage;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.RodentMod.makeImagePath;
import static oceanrodent.util.Wiz.*;

public class WreckPower extends AbstractEasyPower {
    public static String POWER_ID = makeID("Wreck");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static int idOffset = 0;

    public AbstractCard.CardType cardType;
    private Function<Integer, String> effectText;
    private Consumer<Integer> effect;
    private int power;
    private int originalAmount;
    
    public WreckPower(AbstractCard from, boolean buff, int cardsNeeded, int power, Function<Integer, String> effectText, Consumer<Integer> effect) {
        super(POWER_ID+from.type.toString(), powerStrings.NAME + ": " + from.name, buff ? PowerType.BUFF : PowerType.DEBUFF, true, adp(), cardsNeeded+1);
        this.power = power;
        originalAmount = cardsNeeded;
        if (power > 0) {
            isTwoAmount = true;
            amount2 = power;
        }
        this.effectText = effectText;
        this.effect = effect;
        cardType = from.type;
        ID += idOffset++;
        updateDescription();
    }
  
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == cardType) {
            reducePower(1);
            updateDescription();
            if (amount == 0) {
                flash();
                for (int i = 0; i < originalAmount; i++)
                    vfx(new WreckBoulderEffect(i));
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

    public static class WreckBoulderEffect extends AbstractGameEffect {
        private static final TextureRegion TEX = new TextureRegion(TexLoader.getTexture(makeImagePath("vfx/wreckboulder.png")));
        private static final int W = TEX.getTexture().getWidth();
        private static final int H = TEX.getTexture().getHeight();
        private static final float ACCELERATION = -500f;
        private static final float FADE_RATE = 1.4f;
        private static final float ROTATION_RATE = 300f;

        private float x, y, targetY, yVel, xVel, delay, imgScale;
        private boolean fallen = false;

        public WreckBoulderEffect(int num) {
            super();
            color = Color.WHITE.cpy();
            rotation = MathUtils.random(0f, 360f);
            delay = num * 0.2f;
            yVel = MathUtils.random(-600f, -500f) * scale;
            y = Settings.HEIGHT;
            targetY = AbstractDungeon.floorY + MathUtils.random(-60f, 40f) * scale;
            renderBehind = targetY >= AbstractDungeon.floorY;
            x = AbstractDungeon.player.hb.cX + MathUtils.random(-200f, 200f) * scale;
            xVel = MathUtils.random(100f, 200f) * scale;
            imgScale = MathUtils.random(0.4f, 0.6f);
            if (MathUtils.randomBoolean()) xVel *= -1;
        }

        public void update() {
            if (delay > 0f)
                delay -= Gdx.graphics.getDeltaTime();
            if (delay <= 0f) {
                y += yVel * Gdx.graphics.getDeltaTime();
                yVel += ACCELERATION * Gdx.graphics.getDeltaTime() * scale;
                if (fallen) {
                    x += xVel * Gdx.graphics.getDeltaTime();
                    rotation += ROTATION_RATE * Gdx.graphics.getDeltaTime();
                    color.a -= FADE_RATE * Gdx.graphics.getDeltaTime();
                    isDone = color.a <= 0f;
                } else if (y <= targetY) {
                    y = targetY;
                    yVel = MathUtils.random(150f, 350f) * scale;
                    fallen = true;
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true); 
                }
            }
        }

        public void render(SpriteBatch sb) {
            sb.setColor(color);
            if (delay <= 0f)
                sb.draw(TEX, x - W / 2f, y - H /2f, (float)W / 2f, (float)H / 2f, W, H, scale * imgScale, scale * imgScale, rotation);
        }

        public void dispose() {}
    }
}