package oceanrodent.util;

import oceanrodent.RodentMod;
import oceanrodent.characters.TheRodent;
import com.badlogic.gdx.Gdx;
import java.util.Arrays;
import java.util.List;
import skindex.itemtypes.CustomizableItem;
import skindex.registering.SkindexPlayerSkinRegistrant;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerAtlasSkin;
import skindex.skins.player.PlayerAtlasSkinData;
import skindex.skins.player.PlayerSkin;
import skindex.unlockmethods.FreeUnlockMethod;

import static oceanrodent.RodentMod.makeID;

public class Skindexer implements SkindexPlayerSkinRegistrant {    
    public static void register() {
        SkindexRegistry.subscribe(new Skindexer());
    }
    
    public List<PlayerSkin> getDefaultPlayerSkinsToRegister() {
        return Arrays.asList(new RodentSkin(makeID("skinBase")));
    }

    public List<PlayerSkin> getPlayerSkinsToRegister() {
        return Arrays.asList(
            new RodentSkin(makeID("black")),
            new RodentSkin(makeID("brown")),
            new RodentSkin(makeID("grey")),
            new RodentSkin(makeID("yellow")),
            new RodentSkin(makeID("ghost"))
        );
    }

    public static class RodentSkin extends PlayerAtlasSkin {

        public RodentSkin(String id) {
            super(new RodentSkinData(id));
        }

        @Override
        public CustomizableItem makeCopy() {
            return new RodentSkin(id);
        }

        private static class RodentSkinData extends PlayerAtlasSkinData {
            private static String skinResourceFolder = RodentMod.modID + "Resources/images/char/mainChar/skins/";

            public RodentSkinData(String id) {
                skeletonUrl = RodentMod.modID + "Resources/images/char/mainChar/rat.json";
                if (id.equals(makeID("skinBase"))) {
                    atlasUrl = RodentMod.modID + "Resources/images/char/mainChar/rat.atlas";
                    resourceDirectoryUrl = RodentMod.modID + "Resources/images/char/mainChar/";
                } else {
                    String path = id.replace(RodentMod.modID + ":", "");
                    if (Gdx.files.internal(skinResourceFolder + path + "/rat.json").exists())
                        skeletonUrl = skinResourceFolder + path + "/rat.json";
                    atlasUrl = skinResourceFolder + path + "/rat.atlas";
                    resourceDirectoryUrl = skinResourceFolder + path + "/";
                }
                defaultAnimName = "Idle";

                this.id = id;
                name = id; //CardCrawlGame.languagePack.getUIString(id).TEXT[0];
                this.scale = TheRodent.SIZE_SCALE;

                unlockMethod = FreeUnlockMethod.methodId;
                playerClass = TheRodent.Enums.THE_RODENT_OCEAN.name();
            }
        }
    }
}