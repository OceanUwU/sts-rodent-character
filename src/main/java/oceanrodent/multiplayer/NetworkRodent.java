package oceanrodent.multiplayer;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanrodent.RodentMod;
import oceanrodent.characters.TheRodent;
import oceanrodent.util.TexLoader;
import spireTogether.SpireTogetherMod;
import spireTogether.Unlockable;
import spireTogether.modcompat.generic.energyorbs.CustomizableEnergyOrbCustom;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.skins.AtlasSkin;
import spireTogether.skins.PlayerSkin;
import spireTogether.ui.elements.presets.Nameplate;
import spireTogether.util.BundleManager;

import static oceanrodent.RodentMod.*;

public class NetworkRodent extends NetworkCharPreset {
    public NetworkRodent() {
        super(new TheRodent(TheRodent.characterStrings.NAMES[1], TheRodent.Enums.THE_RODENT_OCEAN));
        energyOrb = new CustomizableEnergyOrbCustom(TheRodent.orbTextures, TheRodent.orbVfxTexture, TheRodent.orbRotationValues);
        lobbyScale = 1.6f;
    }

    public String GetThreeLetterID() {
        return "RAT";
    }

    public void GetSkins() {
        skins.add(GetDefaultSkin());
        skins.add(new RodentSkin("GREY", Unlockable.UnlockMethod.FREE));
        skins.add(new RodentSkin("YELLOW", Unlockable.UnlockMethod.FREE));
        skins.add(new RodentSkin("BLACK", Unlockable.UnlockMethod.FREE));
        skins.add(new RodentSkin("BROWN", Unlockable.UnlockMethod.FREE));
        skins.add(GetGhostSkin());
        skins.add(new RodentSkin("TWITCH", Unlockable.UnlockMethod.PROMOTION).SetBundles(new String[] { BundleManager.STREAMER }));
    }
  
    public PlayerSkin GetDefaultSkin() {
        return new RodentSkin("BASE", Unlockable.UnlockMethod.FREE);
    }

    public PlayerSkin GetGhostSkin() {
        return new RodentSkin("GHOST", Unlockable.UnlockMethod.ACHIEVEMENT).SetBundles(new String[] { BundleManager.GHOST });
    }

    public CharacterEntity CreateNew() {
        return new NetworkRodent();
    }

    public Texture GetNameplateIcon(String s) {
        return TexLoader.getTexture(makeImagePath("multiplayer/icon/"+s+".png"));
    }

    public Texture GetDefaultIcon() {
        return GetNameplateIcon("basic");
    }

    public Texture GetWhiteSpecialIcon() {
        return GetNameplateIcon("whiteSpecial");
    }

    public Color GetCharColor() {
        return RodentMod.characterColor.cpy();
    }
  
    public Nameplate GetNameplateUnlock() {
        return null;
    }
  
    @SpirePatch(clz=SpireTogetherMod.class, method="RegisterModdedChars", requiredModId="spireTogether")
    public static class Register {
        public static void Postfix() {
            SpireTogetherMod.allCharacterEntities.put(TheRodent.Enums.THE_RODENT_OCEAN, new NetworkRodent());
        }
    }

    public static class RodentSkin extends AtlasSkin {
        public RodentSkin(String ID, Unlockable.UnlockMethod unlockMethod) {
            super(ID, "", unlockMethod, TheRodent.Enums.THE_RODENT_OCEAN);
            scale = TheRodent.SIZE_SCALE;
        }

        public void LoadResources() {
            shoulderIMG = RodentMod.SHOULDER1;
            shoulder2IMG = RodentMod.SHOULDER2;
            corpseIMG = RodentMod.CORPSE;
            atlasLoc = makeImagePath("multiplayer/skins/"+ID.toLowerCase()+"/rat.atlas");
            jsonLoc = makeCharacterPath("mainChar/rat.json");
        }

        public boolean LoadSkinOnPlayer() {
            if (this.playerClass.equals(AbstractDungeon.player.chosenClass)) {
                ((TheRodent)AbstractDungeon.player).isMultiplayer = true;
                ReflectionHacks.privateMethod(AbstractCreature.class, "loadAnimation", String.class, String.class, float.class).invoke(AbstractDungeon.player, atlasLoc, jsonLoc, 1f / scale);
                ((TheRodent)AbstractDungeon.player).prepAnimation();
                return true;
            }
            return false;
        }
    }
}