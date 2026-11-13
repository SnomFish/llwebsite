package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;

public class Interceptor {
    

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    private Interceptor() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
        this.effect = new NoEffect();
    }


    // getter
    public List<Event> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}


    // builder functions
    public static Interceptor build() {
        return new Interceptor();
    }
    public Interceptor event(IEventId id) {
        this.events.add(new Event(id, null));
        return this;
    }
    public Interceptor condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
    public Interceptor effect(IEffect effect) {
        this.effect = effect;
        return this;
    }
}
