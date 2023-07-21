package oceanrodent.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.List;
import oceanrodent.cards.Belch;
import oceanrodent.cards.Defend;
import oceanrodent.cards.Rummage;
import oceanrodent.cards.Strike;
import oceanrodent.effects.RatsVictoryEffect;
import oceanrodent.relics.TodoItem;
import oceanrodent.util.TexLoader;

import static oceanrodent.RodentMod.*;
import static oceanrodent.characters.TheRodent.Enums.RODENT_COLOUR_OCEAN;

public class TheRodent extends CustomPlayer {
    static final String ID = makeID("rodentcharacter");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    private static final Float SIZE_SCALE = 0.6f;
    private static final Float ANIMATION_SPEED = 1.0f;
    private static boolean endEffectStarted = false;

    public TheRodent(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), new float[]{24f, 36f, 16f, 12f, 0f}), new SpineAnimation(makeCharacterPath("mainChar/rat.atlas"), makeCharacterPath("mainChar/rat.json"), 1f / SIZE_SCALE));
        initializeClass(null, SHOULDER1, SHOULDER2, CORPSE, getLoadout(), 20.0F, -10.0F, 166.0F, 90.0F, new EnergyManager(3));
        loadNewAnimation("rat");
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    public void loadNewAnimation(String version) {
        animation = new SpineAnimation(makeCharacterPath("mainChar/"+version+".atlas"), makeCharacterPath("mainChar/"+version+".json"), 1f / SIZE_SCALE);
        SpineAnimation spine = (SpineAnimation)animation;
        loadAnimation(spine.atlasUrl, spine.skeletonUrl, spine.scale);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.5F);
        e.setTimeScale(ANIMATION_SPEED);
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - currentBlock > 0) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            AnimationState.TrackEntry e2 = state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(ANIMATION_SPEED);
            e2.setTimeScale(ANIMATION_SPEED);
        }

        super.damage(info);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],75, 75, 1, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            retVal.add(Strike.ID);
        for (int i = 0; i < 4; i++)
            retVal.add(Defend.ID);
        retVal.add(Rummage.ID);
        retVal.add(Belch.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(TodoItem.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    private static final String[] orbTextures = {
        makeCharacterPath("mainChar/orb/layer1.png"),
        makeCharacterPath("mainChar/orb/layer2.png"),
        makeCharacterPath("mainChar/orb/layer3.png"),
        makeCharacterPath("mainChar/orb/layer4.png"),
        makeCharacterPath("mainChar/orb/layer4.png"),
        makeCharacterPath("mainChar/orb/layer6.png"),
        makeCharacterPath("mainChar/orb/layer1d.png"),
        makeCharacterPath("mainChar/orb/layer2d.png"),
        makeCharacterPath("mainChar/orb/layer3d.png"),
        makeCharacterPath("mainChar/orb/layer4d.png"),
        makeCharacterPath("mainChar/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return RODENT_COLOUR_OCEAN;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheRodent(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public String getSensoryStoneText() {
        return TEXT[3];
    }

    @Override
    public Texture getCutsceneBg() {
        return TexLoader.getTexture(makeImagePath("ending/pinkBg.jpg"));
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        endEffectStarted = false;
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(makeImagePath("ending/1.png"), "POWER_FLIGHT"));
        panels.add(new CutscenePanel(makeImagePath("ending/2.png"), "WATCHER_HEART_PUNCH"));
        panels.add(new CutscenePanel(makeImagePath("ending/3.png")));
        return panels;
    }

    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        if (!endEffectStarted) {
            effects.add(new RatsVictoryEffect());
            endEffectStarted = true;
        }
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_RODENT_OCEAN;
        @SpireEnum(name = "RODENT_COLOUR_OCEAN")
        public static AbstractCard.CardColor RODENT_COLOUR_OCEAN;
        @SpireEnum(name = "RODENT_COLOUR_OCEAN")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
