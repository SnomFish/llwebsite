package github.snomfish.domain.mechanics;

import java.util.List;

import github.snomfish.functionality.event.RuleDefinition;
import github.snomfish.functionality.event.TriggerRule;

public class Mechanic extends RuleDefinition<MechanicId>{
    

    public Mechanic(
        MechanicId id,
        String name,
        List<TriggerRule> rules
    ) {
        super(id, name, rules);
    }
}
