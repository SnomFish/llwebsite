package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.ids.RuleForAllId;
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

        context.dispatchEvent(RuleForAllId.PRE_CHECK_ACCURACY, ContextSideId.USER);

        double cachedAccuracyModifier = (double) Value.USER().MOVE().MOVE_MODIFIER(ModifierId.ACCURACY).get(context);

        for (Branch<Double> accuracyBranch : accuracyBranches) {
            double accuracyValue = Math.clamp(accuracyBranch.value() * cachedAccuracyModifier, 0, 1);

            probability *= accuracyValue * accuracyBranch.probability();
        }

        return probability;
    }
}
