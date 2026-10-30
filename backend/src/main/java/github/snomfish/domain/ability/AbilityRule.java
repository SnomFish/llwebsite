package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.Event;

public class AbilityRule {
    

    private final List<Event> events;
    private final ICondition condition;
    private final IEffect effect;


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
}
