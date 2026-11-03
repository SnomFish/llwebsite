package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class TrueCondition implements ICondition {
    

    public TrueCondition() {}


    public TrueCondition deepCopy() {
        return new TrueCondition();
    }


    public List<Branch<Boolean>> execute(BattleContext context) {
        return List.of(new Branch<>(true, 1));
    } 
}
