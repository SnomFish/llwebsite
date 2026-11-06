package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.RuleId;

public class AbilityRule {
    

    private List<Event<RuleId>> events;
    private ICondition condition;
    private IEffect effect;


    public AbilityRule(
        List<Event<RuleId>> events, 
        ICondition condition,
        IEffect effect
    ) {
        this.events = events;
        this.condition = condition;
        this.effect = effect;
    }


    public List<Event<RuleId>> events() {return events;}
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
    public AbilityRule event(RuleId eventId, ContextSideId sideId) {
        this.events.add(new Event<>(eventId, sideId));
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
