package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.stats.StatId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public record EnemyNormalDamage (
    StatId attackingStat,
    StatId defendingStat,
    INumber basePower

) implements IEffect {
    

    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        throw new UnsupportedOperationException("not implemented");  
    }
}
