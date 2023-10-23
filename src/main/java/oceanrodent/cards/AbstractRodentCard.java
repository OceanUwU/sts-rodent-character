package oceanrodent.cards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanrodent.characters.TheRodent;
import oceanrodent.mechanics.Junk;
import oceanrodent.util.CardArtRoller;

import static oceanrodent.RodentMod.makeImagePath;
import static oceanrodent.RodentMod.modID;
import static oceanrodent.util.Wiz.*;

public abstract class AbstractRodentCard extends CustomCard {

    protected CardStrings cardStrings;
    protected String[] exDesc;
    protected static AbstractCard dummyCard = new Strike();

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;
    
    public boolean hardy = false;
    
    private boolean needsArtRefresh = false;
    protected boolean previewsJunk = false;
    private static float junkChangeTimer = 0f;
    private static final float JUNK_CHANGE_INTERVAL = 2f;
    private static AbstractCard junkPreview;
    
    public AbstractRodentCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheRodent.Enums.RODENT_COLOUR_OCEAN);
    }

    public AbstractRodentCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        exDesc = cardStrings.EXTENDED_DESCRIPTION;
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        rotateJunkPreview();
        super.renderCardPreview(sb);
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        rotateJunkPreview();
        super.renderCardPreviewInSingleView(sb);
    }

    private void rotateJunkPreview() {
        if (previewsJunk) {
            junkChangeTimer -= Gdx.graphics.getDeltaTime();
            if (junkChangeTimer <= 0f) {
                junkChangeTimer += JUNK_CHANGE_INTERVAL;
                junkPreview = Junk.getRandomJunk();
            }
            cardsToPreview = junkPreview;
        }
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString = makeImagePath("cards/" + cardName + ".png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) return makeImagePath("ui/missing.png");
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void uDesc() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public void triggerOnManualDiscard() {
        if (hardy)
            att(new HardyReturnAction(this));
    }

    private static class HardyReturnAction extends AbstractGameAction {
        AbstractRodentCard c;

        public HardyReturnAction(AbstractRodentCard c) {
            this.c = c;
        }

        public void update() {
            if (adp().hand.size() < BaseMod.MAX_HAND_SIZE) {
                adp().discardPile.moveToHand(c);
                adp().hand.refreshHandLayout();
                adp().hand.applyPowers();
                att(new HandCheckAction());
                c.onHardyReturn();
                att(new AbstractGameAction() {
                    public void update() {
                        isDone = true;
                        c.flash();
                    }
                });
                att(new WaitAction(0.1f));
            }
            isDone = true;
        }
    }

    public void onHardyReturn() {}

    public abstract void upp();

    public void update() {
        super.update();
        if (needsArtRefresh)
            CardArtRoller.computeCard(this);
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    public void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    public void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    public void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    public void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void randomDmg(AbstractGameAction.AttackEffect fx) {
        atb(new AttackDamageRandomEnemyAction(this, fx));
    }

    public void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }

    protected void upMagic(int x) {
        upgradeMagicNumber(x);
    }

    protected void upSecondMagic(int x) {
        upgradeSecondMagic(x);
    }

    protected void upSecondDamage(int x) {
        upgradeSecondDamage(x);
    }
}
