package github.snomfish.domain.item;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public interface IItem {
    

    List<Branch<BattleContext>> execute(BattleContext context);
}
