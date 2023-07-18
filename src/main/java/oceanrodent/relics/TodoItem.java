package oceanrodent.relics;

import static oceanrodent.RodentMod.makeID;

import oceanrodent.characters.TheRodent;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }
}