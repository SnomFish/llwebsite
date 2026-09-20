package github.snomfish.domain.status;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;

public class Status {
    

    private final StatusId id;
    private final String name;
    private final List<TriggerRule> rules;


    public Status(
        StatusId id,
        String name,
        List<TriggerRule> rules
    ) {
        this.id = id;
        this.name = name;
        this.rules = rules;
    }


    // getter
    public StatusId id() {return id;}
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
