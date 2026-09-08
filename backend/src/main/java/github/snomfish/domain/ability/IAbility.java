package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.EventId;

public interface IAbility {


    List<Branch<BattleContext>> handleEvent(BattleContext context, EventId event);
}
