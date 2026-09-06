package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public interface INumber {
    

    List<Branch<Double>> execute(BattleContext context);
}
