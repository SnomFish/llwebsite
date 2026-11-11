package github.snomfish.functionality.number;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

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

        double probability = condition.execute(context);

        // if condition is true add the true number's execute to the outcomes
        if (probability > 0) {
            outcomes.addAll(applyProbability(trueNumber.execute(context), probability));
        } 
        if (probability < 1) {
            outcomes.addAll(applyProbability(falseNumber.execute(context), probability));
        }

        return outcomes;
    }
}
