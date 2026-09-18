package github.snomfish.domain.item;

import static github.snomfish.functionality.Value.resolve;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.event.TriggerRule;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item {
    

    private final ItemId id;
    private final String name;
    private final List<TriggerRule> triggerRules;


    public Item(
        ItemId id,
        String name,
        List<TriggerRule> triggerRules
    ) {
        this.id = id;     
        this.name = name;
        this.triggerRules = triggerRules;
    }


    // getter
    public ItemId id() {return id;}
    public String name() {return name;}
    public List<TriggerRule> triggerRules() {return triggerRules;}


    public List<Branch<BattleContext>> handleEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        for (TriggerRule rule : triggerRules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }
        return outcomes;
    }
}
