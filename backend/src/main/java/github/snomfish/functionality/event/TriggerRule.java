package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class TriggerRule {
     

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    public TriggerRule(
        List<Event> events,
        ICondition condition,
        IEffect effect
    ) {
        this.events = events;
        this.condition = condition;
        this.effect = effect;
    }


    public List<Branch<BattleContext>> execute(BattleContext context, Event event) {
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
