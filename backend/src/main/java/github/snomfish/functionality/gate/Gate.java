package github.snomfish.functionality.gate;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;

public class Gate {
    

    private List<GateEvent> events;
    private ICondition condition;


    private Gate(
        List<GateEvent> events,
        ICondition condition
    ) {
        this.events = events;
        this.condition = condition;
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


    // builder classes
    public static Gate build() {
        return new Gate(
            new ArrayList<>(),
            new NoCondition()
        );
    }
    public Gate event(GateEvent event) {
        this.events.add(event);
        return this;
    }
    public Gate condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
}
