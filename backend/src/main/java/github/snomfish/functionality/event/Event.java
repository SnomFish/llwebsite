package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public record Event (
    EventId id,
    EventTarget target
) {


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return target.getFunction().apply(context, id);
    }
}
