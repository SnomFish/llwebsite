package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

public class AbilityRule {
    
    private EventId event;
    private ICondition condition;
    private IEffect effect;


    public AbilityRule(
        EventId event,
        ICondition condition,
        IEffect effect
    ) {
        this.event = event;
        this.condition = condition;
        this.effect = effect;
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
