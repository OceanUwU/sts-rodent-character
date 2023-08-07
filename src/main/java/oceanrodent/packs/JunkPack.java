package oceanrodent.packs;

import oceanrodent.cards.*;
import java.util.ArrayList;

import static oceanrodent.RodentMod.makeID;

public class JunkPack extends AbstractRodentPack {
    public static final String ID = makeID("JunkPack");

    public JunkPack() {
        super(ID, Empty.ID, new PackSummary(2, 3, 4, 2, 4, PackSummary.Tags.Exhaust));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(Litterpicking.ID);
        cards.add(Mine.ID);
        cards.add(StowAway.ID);

        cards.add(Fling.ID);
        cards.add(Encircle.ID);
        cards.add(Pilfer.ID);
        cards.add(Scavenge.ID);

        cards.add(SurpriseShiv.ID);
        cards.add(Empty.ID);
        cards.add(Hoard.ID);
        return cards;
    }
}