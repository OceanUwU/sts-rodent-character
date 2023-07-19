package oceanrodent.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.makeImagePath;

public class RatsVictoryEffect extends AbstractGameEffect {
    private static final float RAT_CREATION_INTERVAL = 0.16f;
    private ArrayList<Rat> rats = new ArrayList<>();
    private float timer = 0f;
    private float ratCreationTimer = 0f;

    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        ratCreationTimer += Gdx.graphics.getDeltaTime();
        if (ratCreationTimer > RAT_CREATION_INTERVAL) {
            ratCreationTimer -= RAT_CREATION_INTERVAL;
            rats.add(new Rat());
        }
        for (Rat rat : rats)
            rat.update();
        for (Rat rat : rats)
            if (rat.isDone) {
                rats.remove(rat);
                break;
            }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1, 1, 1, Math.min(timer, 1f)));
        for (Rat rat : rats)
            rat.render(sb);
    }

    public void dispose() {}
    
    private static class Rat {
        private static TextureRegion RAT_TEXTURE = new TextureRegion(TexLoader.getTexture(makeImagePath("vfx/rattop.png")));
        private static int W = RAT_TEXTURE.getTexture().getWidth();
        private static int H = RAT_TEXTURE.getTexture().getHeight();

        private static float spawnSize = 1.25f;
        private static float spawnSizeN = -spawnSize + 1f;

        private float startX, startY, endX, endY, x, y, angle, size, angleToDest, time, progress, wiggleSpeed, wiggleAmount;
        private float timer = 0f;
        private boolean isDone = false;
        private float timeOffset = MathUtils.random(100f);

        public Rat() {
            startX = Settings.WIDTH * MathUtils.random(spawnSizeN, spawnSize);
            startY = Settings.WIDTH * MathUtils.random(spawnSizeN, spawnSize);
            endX = Settings.WIDTH * MathUtils.random(spawnSizeN, spawnSize);
            endY = Settings.WIDTH * MathUtils.random(spawnSizeN, spawnSize);
            if (MathUtils.randomBoolean()) {
                if (MathUtils.randomBoolean()) {
                    startX = Settings.WIDTH * spawnSizeN;
                    endX = Settings.WIDTH * spawnSize;
                } else {
                    startX = Settings.WIDTH * spawnSize;
                    endX = Settings.WIDTH * spawnSizeN;
                }
            } else {
                if (MathUtils.randomBoolean()) {
                    startY = Settings.WIDTH * spawnSizeN;
                    endY = Settings.WIDTH * spawnSize;
                } else {
                    startY = Settings.WIDTH * spawnSize;
                    endY = Settings.WIDTH * spawnSizeN;
                }
            }
            time = MathUtils.random(2.0f, 6.0f);
            wiggleAmount = MathUtils.random(10f, 30f);
            wiggleSpeed = MathUtils.random(3.0f, 8.0f);
            size = MathUtils.random(0.8f, 1.6f);
            angleToDest = MathUtils.atan2(endY - startY, endX - startX) * MathUtils.radiansToDegrees;
        }

        public void update() {
            timer += Gdx.graphics.getDeltaTime();
            progress = timer / time;
            if (progress > 1f) isDone = true;
            x = startX + (endX - startX) * progress;
            y = startY + (endY - startY) * progress;
            angle = angleToDest + wiggleAmount * MathUtils.sin((timer + timeOffset) * wiggleSpeed);
        }

        public void render(SpriteBatch sb) {
            sb.draw(RAT_TEXTURE, x - W / 2f, y - H /2f, (float)W / 2f, (float)H / 2f, W, H, size * Settings.scale, size * Settings.scale, angle);
        }
    }
}