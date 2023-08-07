package oceanrodent.packs;

import oceanrodent.cards.*;
import java.util.ArrayList;

import static oceanrodent.RodentMod.makeID;

public class GrimePack extends AbstractRodentPack {
    public static final String ID = makeID("GrimePack");

    public GrimePack() {
        super(ID, MuddyPuddle.ID, new PackSummary(3, 3, 4, 1, 4));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(Acclimatise.ID);
        cards.add(Hindstrike.ID);
        cards.add(GetStuckIn.ID);

        cards.add(Bury.ID);
        cards.add(LickEm.ID);
        cards.add(MuckyTap.ID);
        cards.add(SludgeSmash.ID);
        cards.add(BashAndDash.ID);

        cards.add(OopsDroppedEm.ID);
        cards.add(Piddle.ID);
        return cards;
    }
}