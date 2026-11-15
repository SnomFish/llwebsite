package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;

public class Gate {
    

    private List<IGateId> events;
    private List<ICondition> conditions;


    private Gate() {
        this.events = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }


    // getters
    public List<IGateId> events() {return events;}
    public List<ICondition> conditions() {return conditions;}


    // builder classes
    public static Gate build() {
        return new Gate();
    }
    public Gate event(IGateId event) {
        this.events.add(event);
        return this;
    }
    public Gate condition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }


    public double execute(BattleContext context, IGateId gateId) {

        if (!events.contains(gateId)) return 1;

        double probability = 1;
        for (ICondition condition : conditions) {
            probability *= condition.execute(context);
        }

        return probability;
    }
}
