package oceanrodent.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import oceanrodent.powers.WreckPower;

import static oceanrodent.RodentMod.makeID;
import static oceanrodent.util.Wiz.*;

public class SandAttack extends AbstractRodentCard {
    public final static String ID = makeID("SandAttack");

    public SandAttack() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 4;
        baseSecondMagic = secondMagic = 1;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new StrengthPower(m, -magicNumber));
        if (!m.hasPower(ArtifactPower.POWER_ID))
            applyToEnemy(m, new GainStrengthPower(m, magicNumber));
        applyToSelf(new WreckPower(this, true, secondMagic, magicNumber, a -> exDesc[0] + a + exDesc[1], a -> atb(new HealAction(p, p, a))));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}