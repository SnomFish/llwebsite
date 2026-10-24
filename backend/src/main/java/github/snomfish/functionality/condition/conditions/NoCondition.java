package github.snomfish.functionality.condition.conditions;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;

public class NoCondition implements ICondition {
    

    public NoCondition() {}


    @Override 
    public NoCondition deepCopy() {
        return new NoCondition();
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        return List.of(new Branch<>(true, 1.0));
    }
}
