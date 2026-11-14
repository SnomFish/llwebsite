package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;

public class Gate {
    

    private List<IGateId> events;
    private ICondition condition;


    private Gate() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
    }


    // getters
    public List<IGateId> events() {return events;}
    public ICondition condition() {return condition;}


    // builder classes
    public static Gate build() {
        return new Gate();
    }
    public Gate event(IGateId event) {
        this.events.add(event);
        return this;
    }
    public Gate condition(ICondition condition) {
        this.condition = condition;
        return this;
    }


    public double execute(BattleContext context, IGateId gateId) {

        if (!events.contains(gateId)) return 1;

        return condition.execute(context);
    }
}
