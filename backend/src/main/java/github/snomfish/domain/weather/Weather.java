package github.snomfish.domain.weather;

import java.util.List;

import github.snomfish.functionality.event.RuleDefinition;
import github.snomfish.functionality.event.TriggerRule;

public class Weather extends RuleDefinition<WeatherId> {
    
    public Weather(WeatherId id, String name, List<TriggerRule> rules) {
        super(id, name, rules);
    }
}
