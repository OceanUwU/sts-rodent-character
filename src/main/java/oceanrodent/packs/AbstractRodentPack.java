package oceanrodent.packs;

import oceanrodent.RodentMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import thePackmaster.packs.AbstractCardPack;
import thePackmaster.packs.PackPreviewCard;

import static oceanrodent.RodentMod.makeCardPath;
import static oceanrodent.RodentMod.makeImagePath;

public abstract class AbstractRodentPack extends AbstractCardPack {
    private String previewArtCardID;

    public AbstractRodentPack(String id, String previewArt, AbstractCardPack.PackSummary summary) {
        super(id, CardCrawlGame.languagePack.getUIString(id).TEXT[0], CardCrawlGame.languagePack.getUIString(id).TEXT[1], CardCrawlGame.languagePack.getUIString(id).TEXT[2], CardCrawlGame.languagePack.getUIString(id).TEXT[3], summary);
        previewArtCardID = previewArt;
        previewPackCard = this.makePreviewCard();
    }

    public PackPreviewCard makePreviewCard() {
        if (previewArtCardID == null) return super.makePreviewCard();
        return new PackPreviewCard(packID, makeCardPath(previewArtCardID.replace(RodentMod.modID + ":", "")+".png"), this);
    }

    public String getHatPath() {
        return makeImagePath("hats/"+packID.replace(RodentMod.modID + ":", "")+".png");
    }
}