package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;

public class ConditionalNumber implements INumber {
    

    private ICondition condition;
    private INumber trueNumber;
    private INumber falseNumber;


    public ConditionalNumber(
        ICondition condition,
        INumber trueNumber,
        INumber falseNumber
    ) {
        this.condition = condition;
        this.trueNumber = trueNumber;
        this.falseNumber = falseNumber;
    }
    private ConditionalNumber(ConditionalNumber copy) {
        this.condition = copy.condition.deepCopy();
        this.trueNumber = copy.trueNumber.deepCopy();
        this.falseNumber = copy.falseNumber.deepCopy();
    }


    public ConditionalNumber deepCopy() {
        return new ConditionalNumber(this);
    }


    public List<Branch<Double>> execute(BattleContext context) {
        List<Branch<Double>> outcomes = new ArrayList<>();

        List<Branch<Boolean>> conditionBranches = condition.execute(context);
        for (Branch<Boolean> conditionBranch : conditionBranches) {

            // if condition is true add the true number's execute to the outcomes
            if (conditionBranch.value()) {
                outcomes.addAll(BranchUtil.applyProbability(trueNumber.execute(context), conditionBranch));
            } else {
                outcomes.addAll(BranchUtil.applyProbability(falseNumber.execute(context), conditionBranch));
            }
        }

        return outcomes;
    }
}
