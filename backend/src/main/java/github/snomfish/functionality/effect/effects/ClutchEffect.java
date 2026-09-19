package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ClutchEffect implements IEffect {
    

    public ClutchEffect() {}


    @Override 
    public ClutchEffect deepCopy() {
        return new ClutchEffect();
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        ActiveLoomian user = outcome.user().activeLoomian();
        ActiveLoomian target = outcome.target().activeLoomian();
        double damage = user.action().move().damage();

        damage = Math.min(damage, target.currentHealth()-1);
        outcome.user().activeLoomian().action().move().setDamage(damage);
        target.setHasAbilityActivated(true);

        return List.of(new Branch<>(outcome, 1.0));
    }
}
