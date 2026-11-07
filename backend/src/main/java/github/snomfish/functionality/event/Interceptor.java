package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.ids.InterceptorId;

public class Interceptor {
    

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    private Interceptor() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
        this.effect = new NoEffect();
    }


    private Interceptor(Interceptor copy) {
        this.events = new ArrayList<>(copy.events);
        this.condition = copy.condition.deepCopy();
        this.effect = copy.effect.deepCopy();
    }
    public Interceptor deepCopy() {
        return new Interceptor(this);
    }


    // getter
    public List<Event> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}


    // builder functions
    public static Interceptor build() {
        return new Interceptor();
    }
    public Interceptor event(InterceptorId id, ContextSideId sideId) {
        this.events.add(new Event(id, sideId));
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
