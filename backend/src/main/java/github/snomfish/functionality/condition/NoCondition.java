package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class NoCondition implements ICondition {
    

    public NoCondition() {}


    @Override 
    public NoCondition deepCopy() {
        return new NoCondition();
    }


    @Override 
    public double execute(BattleContext context) {
        return 0;
    }
}
