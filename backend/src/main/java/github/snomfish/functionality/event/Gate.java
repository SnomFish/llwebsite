package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.gateIds.GateForAllId;

public class Gate {
    

    private List<Event> events;
    private ICondition condition;


    private Gate(
        List<Event> events,
        ICondition condition
    ) {
        this.events = events;
        this.condition = condition;
    }


    // getters
    public List<Event> events() {return events;}
    public ICondition condition() {return condition;}


    // builder classes
    public static Gate build() {
        return new Gate(
            new ArrayList<>(),
            new NoCondition()
        );
    }
    public Gate event(GateForAllId eventId, ContextSideId sideId) {
        this.events.add(new Event(eventId, sideId));
        return this;
    }
    public Gate condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
}
