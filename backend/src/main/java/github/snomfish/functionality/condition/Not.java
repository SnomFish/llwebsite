package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Not implements ICondition {
    

    private ICondition condition;


    public Not(ICondition condition) {
        this.condition = condition;
    }


    @Override 
    public Not deepCopy() {
        return new Not(
            condition.deepCopy()
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        List<Branch<Boolean>> outcomes = condition.execute(context);

        for (Branch<Boolean> branch : outcomes) { // inverts the boolean value
            branch.setValue(branch.value() == false);
        }

        return outcomes;
    }
}
