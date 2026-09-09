package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface ICondition extends DeepCopyable<ICondition> {
    

    List<Branch<Boolean>> execute(BattleContext context);
}
