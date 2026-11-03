package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class FalseCondition implements ICondition {
    

    public FalseCondition() {}


    public FalseCondition deepCopy() {
        return new FalseCondition();
    }


    public List<Branch<Boolean>> execute(BattleContext context) {
        return List.of(new Branch<>(false, 1));
    } 
}
