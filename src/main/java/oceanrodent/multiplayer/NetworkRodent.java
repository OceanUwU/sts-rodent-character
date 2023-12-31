package oceanrodent.multiplayer;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import oceanrodent.RodentMod;
import oceanrodent.characters.TheRodent;
import oceanrodent.util.TexLoader;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerSkin;
import spireTogether.SpireTogetherMod;
import spireTogether.modcompat.generic.energyorbs.CustomizableEnergyOrbCustom;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.ui.elements.presets.Nameplate;

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

    public PlayerSkin GetGhostSkin() {
        return SkindexRegistry.getPlayerSkinByClassAndId(playerClass, makeID("ghost"));
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
}