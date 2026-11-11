package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.ids.GateId;

public class GateCondition implements ICondition {
    

    private GateId gateId;
    private ContextSideId sideId;
    

    public GateCondition(
        GateId gateId,
        ContextSideId sideId
    ) {
        this.gateId = gateId;
        this.sideId = sideId;
    }


    public GateCondition(GateCondition copy) {
        this.gateId = copy.gateId;
        this.sideId = copy.sideId;
    }
    public GateCondition deepCopy() {
        return new GateCondition(this);
    }


    public double execute(BattleContext context) {
        return context.dispatchGate(gateId, sideId);

    }
}
