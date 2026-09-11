package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface IMove extends DeepCopyable<IMove> {
    

    List<Branch<BattleContext>> execute(BattleContext context);
}
