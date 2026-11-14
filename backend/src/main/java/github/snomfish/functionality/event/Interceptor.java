package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.events.Event;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class Interceptor {
    

    private List<IRuleId> events;
    private ICondition condition;
    private IEffect effect;


    private Interceptor() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
        this.effect = new NoEffect();
    }


    // getter
    public List<IRuleId> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect effect() {return effect;}


    // builder functions
    public static Interceptor build() {
        return new Interceptor();
    }
    public Interceptor event(IRuleId id) {
        this.events.add(id);
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
