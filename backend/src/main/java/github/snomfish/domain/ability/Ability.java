package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.event.RuleDefinition;
import github.snomfish.functionality.event.TriggerRule;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability extends RuleDefinition<AbilityId> {

    public Ability(AbilityId id, String name, List<TriggerRule> rules) {
        super(id, name, rules);
    }
}
