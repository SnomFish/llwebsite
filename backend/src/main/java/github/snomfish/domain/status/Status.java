package github.snomfish.domain.status;

import java.util.List;

import github.snomfish.functionality.event.RuleDefinition;
import github.snomfish.functionality.event.TriggerRule;

public class Status extends RuleDefinition<StatusId> {
    
    public Status(
        StatusId id,
        String name,
        List<TriggerRule> rules
    ) {
        super(id, name, rules);
    }
}
