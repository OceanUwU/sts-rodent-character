package oceanrodent.packs;

import oceanrodent.cards.*;
import java.util.ArrayList;

import static oceanrodent.RodentMod.makeID;

public class HardyPack extends AbstractRodentPack {
    public static final String ID = makeID("HardyPack");

    public HardyPack() {
        super(ID, Splinter.ID, new PackSummary(2, 4, 3, 3, 2, PackSummary.Tags.Discard));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(BinLidShield.ID);
        cards.add(NimbleStrike.ID);
        cards.add(Scurry.ID);

        cards.add(NotReadyYet.ID);
        cards.add(Ratcrobratics.ID);
        cards.add(Sidestep.ID);
        cards.add(SummonCourage.ID);

        cards.add(BeatChest.ID);
        cards.add(Ratpack.ID);
        cards.add(SqueezeThrough.ID);
        return cards;
    }
}