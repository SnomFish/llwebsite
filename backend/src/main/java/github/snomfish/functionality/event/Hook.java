package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.NoEffect;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class Hook {
    

    private List<IRuleId> events;
    private List<ICondition> conditions;
    private IEffect effect;


    private Hook() {
        this.events = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.effect = new NoEffect();
    }


    // getter
    public List<IRuleId> events() {return events;}
    public List<ICondition> conditions() {return conditions;}
    public IEffect effect() {return effect;}


    // builder functions
    public static Hook build() {
        return new Hook();
    }
    public Hook event(IRuleId id) {
        this.events.add(id);
        return this;
    }
    public Hook condition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }
    public Hook effect(IEffect effect) {
        this.effect = effect;
        return this;
    }
}
