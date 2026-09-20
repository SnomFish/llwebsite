package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability {

    
    private final AbilityId id;
    private final String name;
    private final List<TriggerRule> rules;


    public Ability(
        AbilityId id,
        String name,
        List<TriggerRule> rules
    ) {
        this.id = id;
        this.name = name;
        this.rules = rules;
    }


    // getter
    public AbilityId id() {return id;}
    public String name() {return name;}
    public List<TriggerRule> triggerRules() {return rules;}


    public List<Branch<BattleContext>> handleEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        for (TriggerRule rule : rules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }
        return outcomes;
    }
}
