package oceanrodent.util;

import basemod.AutoAdd;
import oceanrodent.RodentMod;
import oceanrodent.cards.AbstractRodentCard;
import oceanrodent.characters.TheRodent;
import oceanrodent.packs.AbstractRodentPack;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.interfaces.EditPacksSubscriber;

public class PackLoader implements EditPacksSubscriber {
    @Override
    public void receiveEditPacks() {
        SpireAnniversary5Mod.allowCardClass(AbstractRodentCard.class);
        SpireAnniversary5Mod.allowCardColor(TheRodent.Enums.RODENT_COLOUR_OCEAN);
        new AutoAdd(RodentMod.modID)
            .packageFilter("oceanrodent.packs")
            .any(AbstractRodentPack.class, (info, pack) -> SpireAnniversary5Mod.declarePack(pack));
    }
}