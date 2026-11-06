package github.snomfish.functionality.interceptor;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Event;

public class Interceptor {
    

    private List<InterceptorEvent> events;
    private ICondition condition;
    private IEffect effect;
    private int triggerPriority;


    private Interceptor() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
        this.effect = new NoEffect();
        this.triggerPriority = 0;
    }


    private Interceptor(Interceptor copy) {
        this.events = new ArrayList<>(copy.events);
        this.condition = copy.condition.deepCopy();
        this.effect = copy.effect.deepCopy();
        this.triggerPriority = copy.triggerPriority;
    }
    public Interceptor deepCopy() {
        return new Interceptor(this);
    }


    // getter
    public List<InterceptorEvent> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}
    public int triggerPriority() {return triggerPriority;}


    // builder functions
    public static Interceptor build() {
        return new Interceptor();
    }
    public Interceptor event(InterceptorId id, ContextSideId sideId) {
        this.events.add(new InterceptorEvent(id, sideId));
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
    public Interceptor triggerPriority(int triggerPriority) {
        this.triggerPriority = triggerPriority;
        return this;
    }
}
