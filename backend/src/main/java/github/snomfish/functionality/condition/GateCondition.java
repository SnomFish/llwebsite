package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.GateId;

public class GateCondition implements ICondition {
    

    private GateId gateId;
    

    public GateCondition(GateId gateId) {
        this.gateId = gateId;
    }


    public GateCondition(GateCondition copy) {
        this.gateId = copy.gateId;
    }
    public GateCondition deepCopy() {
        return new GateCondition(this);
    }


    public List<Branch<Boolean>> execute(BattleContext context) {
        return context.dispatchGate(gateId);

    }
}
