package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface INumber extends DeepCopyable<INumber> {
    

    List<Branch<Double>> execute(BattleContext context);
}
