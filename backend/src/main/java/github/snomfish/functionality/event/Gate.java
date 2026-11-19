package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality._schematics.Conditions;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;

public class Gate {
    

    private List<IGateId> events;
    private List<ICondition> conditions;
    private int priority;


    private Gate() {
        this.events = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.priority = 0;
    }


    // getters
    public List<IGateId> events() {return events;}
    public List<ICondition> conditions() {return conditions;}
    public int priority() {return priority;}


    // builder classes
    public static Gate build() {
        return new Gate();
    }
    public Gate event(IGateId event) {this.events.add(event); return this;}
    public Gate condition(ICondition condition) {this.conditions.add(condition); return this;}
    public Gate priority(int priority) {this.priority = priority; return this;}


    public double execute(BattleContext context, IGateId gateId) {

        if (!events.contains(gateId)) return 1;

        return Conditions.resolve(context, conditions);
    }
}
