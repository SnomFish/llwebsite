package github.snomfish.domain.item;

import java.util.List;

import github.snomfish.functionality.event.RuleDefinition;
import github.snomfish.functionality.event.TriggerRule;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item extends RuleDefinition<ItemId> {
    
    public Item(
        ItemId id,
        String name,
        List<TriggerRule> rules
    ) {
        super(id, name, rules);
    }
}
