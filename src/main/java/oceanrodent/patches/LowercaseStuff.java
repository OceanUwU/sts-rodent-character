package oceanrodent.patches;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class LowercaseStuff {
    public static boolean run = false;

    @SpirePatch2(clz = BitmapFont.class, method = "draw", paramtypez = { Batch.class, CharSequence.class, float.class, float.class })
    @SpirePatch2(clz = BitmapFont.class, method = "draw", paramtypez = { Batch.class, CharSequence.class, float.class, float.class, float.class, int.class, boolean.class })
    @SpirePatch2(clz = BitmapFont.class, method = "draw", paramtypez = { Batch.class, CharSequence.class, float.class, float.class, int.class, int.class, float.class, int.class, boolean.class })
    @SpirePatch2(clz = BitmapFont.class, method = "draw", paramtypez = { Batch.class, CharSequence.class, float.class, float.class, int.class, int.class, float.class, int.class, boolean.class, String.class })
    public static class Patch1 {
        public static void Prefix(BitmapFont __instance, @ByRef CharSequence[] str) {
            if (run)
                str[0] = str[0].toString().toLowerCase();
        }
    }

    @SpirePatch(clz=MainMenuScreen.class, method=SpirePatch.CONSTRUCTOR, paramtypez={boolean.class})
    public static class MainMenuPatch {
        public static void Postfix() {
            run = false;
        }
    }
}