package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public interface ICondition {
    

    List<Branch<Boolean>> execute(BattleContext context);
}
