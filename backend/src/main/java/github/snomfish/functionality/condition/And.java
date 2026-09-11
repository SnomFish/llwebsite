package github.snomfish.functionality.condition;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.condition.ConditionUtil.applyLogicalOperator;
import static github.snomfish.functionality.condition.ConditionUtil.optimise;
import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class And implements ICondition {
    

    private final List<ICondition> conditions;


    public And(List<ICondition> conditions) {
        if (conditions == null) throw new RuntimeException("Cannot parse null into And condition");
        if (conditions.size() == 0) throw new RuntimeException("And cannot have no conditions");
        
        this.conditions = conditions;
    }


    @Override
    public And deepCopy() {
        return new And(
            deepCopyList(conditions)
        );
    } 

    // this code is old as fuck, consider running the first condition, then discarding/auto returning any that are false, then only focus on the ones that are true still
    // deleted all of the code, gonna try and rewrite it
    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {

        List<List<Branch<Boolean>>> outcomeList = new ArrayList<>();

        for (ICondition condition : conditions) {
            outcomeList.add(condition.execute(context));    
        }

        List<Branch<Boolean>> outcomes = applyLogicalOperator(outcomeList, Boolean::logicalAnd);

        return optimise(outcomes);
    }
}
