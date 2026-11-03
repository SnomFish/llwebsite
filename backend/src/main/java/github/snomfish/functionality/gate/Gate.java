package github.snomfish.functionality.gate;

import github.snomfish.functionality.condition.ICondition;

public class Gate {
    

    private GateEvent event;
    private ICondition condition;


    public Gate(
        GateEvent event,
        ICondition condition
    ) {
        this.event = event;
        this.condition = condition;
    }


    private Gate(Gate copy) {
        this.event = copy.event;
        this.condition = copy.condition.deepCopy();
    }
    public Gate deepCopy() {
        return new Gate(this);
    }


    public GateEvent event() {return event;}
    public ICondition condition() {return condition;}
}
