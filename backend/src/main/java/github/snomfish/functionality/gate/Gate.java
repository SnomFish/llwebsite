package github.snomfish.functionality.gate;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;

public class Gate {
    

    private List<GateEvent> events;
    private ICondition condition;
    private IEffect failureEffect; // the effect that happens if the condition fails


    private Gate(
        List<GateEvent> events,
        ICondition condition,
        IEffect failureEffect
    ) {
        this.events = events;
        this.condition = condition;
        this.failureEffect = failureEffect;
    }


    private Gate(Gate copy) {
        this.events = new ArrayList<>(copy.events);
        this.condition = copy.condition.deepCopy();
    }
    public Gate deepCopy() {
        return new Gate(this);
    }


    // getters
    public List<GateEvent> events() {return events;}
    public ICondition condition() {return condition;}
    public IEffect failureEffect() {return failureEffect;}


    // builder classes
    public static Gate build() {
        return new Gate(
            new ArrayList<>(),
            new NoCondition(),
            new NoEffect()
        );
    }
    public Gate event(GateId eventId, ContextSideId sideId) {
        this.events.add(new GateEvent(eventId, sideId));
        return this;
    }
    public Gate condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
    public Gate failureEffect(IEffect failureEffect) {
        this.failureEffect = failureEffect;
        return this;
    }
}
