package github.snomfish.functionality.effect;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

// eveything that comes into effect should be constant, the only way an effect branches is through events
// events should be calling logic that returns branches
public interface IEffect {
    

    List<Branch<BattleContext>> execute(BattleContext context);
}