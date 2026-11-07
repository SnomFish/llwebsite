package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.ids.GenericAntiId;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.event.ids.RuleId;

public class AbilityRule {
    

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    public AbilityRule(
        List<Event> events, 
        ICondition condition,
        IEffect effect
    ) {
        this.events = events;
        this.condition = condition;
        this.effect = effect;
    }


    public List<Event> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}


    // builder functions
    public static AbilityRule build() {
        return new AbilityRule(
            new ArrayList<>(),
            new NoCondition(),
            new NoEffect()
        );
    }
    public AbilityRule event(RuleId ruleId, ContextSideId sideId) {
        this.events.add(new Event(ruleId, sideId));
        return this;
    }
    public AbilityRule event(IsolatedId isolatedId) {
        this.events.add(new Event(isolatedId, null));
        return null;
    }
    public AbilityRule event(GenericId genericId) {
        this.events.add(new Event(genericId, null));
        return this;
    }
    public AbilityRule event(GenericAntiId genericId) {
        this.events.add(new Event(genericId, null));
        return this;
    }

    public AbilityRule condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
    public AbilityRule effect(IEffect effect) {
        this.effect = effect;
        return this;
    }
}
