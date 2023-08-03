package oceanrodent;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.patches.CenterGridCardSelectScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.nio.charset.StandardCharsets;
import oceanrodent.cards.AbstractRodentCard;
import oceanrodent.cards.cardvars.*;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Junk;
import oceanrodent.potions.*;
import oceanrodent.relics.AbstractEasyRelic;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class RodentMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostUpdateSubscriber {

    public static final String modID = "oceanrodent";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(1.00f, 0.91f, 0.91f, 1);

    public static final String SHOULDER1 = makeCharacterPath("mainChar/shoulder.png");
    public static final String SHOULDER2 = makeCharacterPath("mainChar/shoulder2.png");
    public static final String CORPSE = makeCharacterPath("mainChar/corpse.png");
    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeImagePath("charSelect/charButton.png");
    private static final String CHARSELECT_PORTRAIT = makeImagePath("charSelect/charBG.png");
    
    public static boolean choosingRemoveCard = false;

    @SpireEnum
    public static RewardItem.RewardType REMOVECARD;

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public RodentMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheRodent.Enums.RODENT_COLOUR_OCEAN, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath)
    {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        RodentMod thismod = new RodentMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheRodent(TheRodent.characterStrings.NAMES[1], TheRodent.Enums.THE_RODENT_OCEAN), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheRodent.Enums.THE_RODENT_OCEAN);
                
        BaseMod.addPotion(Gunk.class, new Color(0.1f, 0.2f, 0.1f, 1f), new Color(0f,0f,0f,0f), new Color(0.5f, 0.1f, 0.1f, 1f), Gunk.POTION_ID, TheRodent.Enums.THE_RODENT_OCEAN);
        BaseMod.addPotion(Sugar.class, new Color(1.0f, 1.0f, 1.0f, 1f), new Color(0f,0f,0f,0f), new Color(0.8f, 0.8f, 0.8f, 1f), Sugar.POTION_ID, TheRodent.Enums.THE_RODENT_OCEAN);
        BaseMod.addPotion(MudBottle.class, new Color(0.25f, 0.2f, 0.15f, 1f), new Color(0f,0f,0f,0f), new Color(0f,0f,0f,0f), MudBottle.POTION_ID, TheRodent.Enums.THE_RODENT_OCEAN);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
            .packageFilter(AbstractEasyRelic.class)
            .any(AbstractEasyRelic.class, (info, relic) -> {
                if (relic.color == null) {
                    BaseMod.addRelic(relic, RelicType.SHARED);
                } else {
                    BaseMod.addRelicToCustomPool(relic, relic.color);
                }
                if (!info.seen) {
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                }
            });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        Junk.initialise();
        for (Junk.JunkCard junk : Junk.allJunk) {
            BaseMod.addCard(junk);
            UnlockTracker.unlockCard(junk.cardID);
        }
        new AutoAdd(modID)
            .packageFilter(AbstractRodentCard.class)
            .setDefaultSeen(true)
            .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public void receivePostUpdate() {
        if (choosingRemoveCard && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)Settings.WIDTH / 2f, (float)Settings.HEIGHT / 2f));
            AbstractDungeon.player.masterDeck.removeCard(card);
            choosingRemoveCard = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }
}
