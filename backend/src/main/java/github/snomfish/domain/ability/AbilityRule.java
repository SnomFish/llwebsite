package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

public class AbilityRule {
    
    private List<EventId> events;
    private ICondition condition;
    private IEffect effect;


    public AbilityRule(
        List<EventId> events,
        ICondition condition,
        IEffect effect
    ) {
        this.events = events;
        this.condition = condition;
        this.effect = effect;
    }


    // getter
    public List<EventId> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}


    public List<Branch<BattleContext>> execute(BattleContext context, EventId event) {
        if (!events.contains(event)) {
            return List.of(new Branch<>(context, 1.0));
        }
        List<Branch<Boolean>> conditionBranches = condition.execute(context);
        return BranchUtil.flatMap(
            conditionBranches,
            conditionOutcome -> {
                if (conditionOutcome) {
                    return effect.execute(context);
                }

                return List.of(new Branch<>(context, 1.0));
            }
        );
    }
}
