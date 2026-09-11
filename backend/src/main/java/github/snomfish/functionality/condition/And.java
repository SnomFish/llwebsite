package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.copy.DeepCopy.*;

public class And implements ICondition {
    

    private final List<ICondition> conditions;


    public And(List<ICondition> conditions) {
        this.conditions = conditions;
    }


    @Override
    public And deepCopy() {
        return new And(
            deepCopyList(conditions)
        );
    } 

    // this code is old as fuck, consider running the first condition, then discarding/auto returning any that are false, then only focus on the ones that are true still
    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {
        

        return outcomes;
    }
}
