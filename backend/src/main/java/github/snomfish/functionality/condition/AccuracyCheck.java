package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.ids.RuleId;
import github.snomfish.functionality.number.INumber;

public class AccuracyCheck implements ICondition {
    

    private INumber accuracyNumber;


    public AccuracyCheck(INumber accuracyNumber) {
        this.accuracyNumber = accuracyNumber;
    }


    @Override 
    public AccuracyCheck deepCopy() {
        return new AccuracyCheck(
            accuracyNumber.deepCopy()
        );
    }


    @Override 
    public double execute(BattleContext context) {
        List<Branch<Double>> accuracyBranches = accuracyNumber.execute(context);
        double probability = 0;

        context.dispatchEvent(RuleId.ACCURACY_CHECK_EVENT);

        double cachedAccuracyModifier = (double) Value.USER().MOVE().ACCURACY_MODIFIER().get(context);

        for (Branch<Double> accuracyBranch : accuracyBranches) {
            double accuracyValue = Math.clamp(accuracyBranch.value() * cachedAccuracyModifier, 0, 1);

            probability *= accuracyValue * accuracyBranch.probability();
        }

        return probability;
    }
}
