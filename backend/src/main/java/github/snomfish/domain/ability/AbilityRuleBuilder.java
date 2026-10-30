package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Event;

public class AbilityRuleBuilder {
    

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    public AbilityRuleBuilder() {
        events = new ArrayList<>();
        condition = new NoCondition();
        effect = new NoEffect();
    }


    public static AbilityRuleBuilder newRule() {
        return new AbilityRuleBuilder();
    }
    public AbilityRule build() {
        return new AbilityRule(
            events,
            condition,
            effect
        );
    }


    public AbilityRuleBuilder event(Event event) {
        this.events.add(event);
        return this;
    }
    public AbilityRuleBuilder condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
    public AbilityRuleBuilder effect(IEffect effect) {
        this.effect = effect;
        return this;
    }
}
