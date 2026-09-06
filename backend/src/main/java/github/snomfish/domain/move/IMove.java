package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public interface IMove {
    

    List<Branch<BattleContext>> execute(BattleContext context);
}
