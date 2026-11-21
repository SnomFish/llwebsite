package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
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

        int targetCurrentHealth  = Value.TARGET().CURRENT_HEALTH().get(outcome);
        int damage               = Value.USER().MOVE().DAMAGE().get(outcome);

        damage = (int)Math.min(damage, targetCurrentHealth-1);
        Value.USER().MOVE().DAMAGE().set(outcome, damage);

        return List.of(new Branch<>(outcome, 1.0));
    }
}
