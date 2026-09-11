package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public record Event (
    EventId id,
    EventTarget target
) {


    // target.getFunction() points towards one of the three dispatch functions in battleContext
    // that correspond to dispatching the event to the user, target or both 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return target.getFunction().apply(context, id);
    }
}
