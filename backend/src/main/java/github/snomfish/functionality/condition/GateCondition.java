package github.snomfish.functionality.condition;

import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.gateIds.GateForAllId;

public class GateCondition implements ICondition {
    

    private GateForAllId gateId;
    private ContextSideId sideId;
    

    public GateCondition(
        GateForAllId gateId,
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
