package github.snomfish.functionality.condition;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.condition.ConditionUtil.applyLogicalOperator;
import static github.snomfish.functionality.condition.ConditionUtil.optimise;
import static github.snomfish.functionality.copy.DeepCopy.*;

public class Or implements ICondition {
    

    private List<ICondition> conditions;


    public Or(List<ICondition> conditions) {
        this.conditions = conditions;
    }


    @Override 
    public Or deepCopy() {
        return new Or(
            deepCopyList(conditions)
        );
    }


    // code also old as fuck
    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {

        List<List<Branch<Boolean>>> outcomeList = new ArrayList<>();

        for (ICondition condition : conditions) {
            outcomeList.add(condition.execute(context));    
        }

        List<Branch<Boolean>> outcomes = applyLogicalOperator(outcomeList, Boolean::logicalOr);

        return optimise(outcomes);
    }
}
