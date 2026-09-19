package github.snomfish.functionality.condition.conditions;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.condition.ConditionUtil.optimise;

public class AccuracyCheck implements ICondition {
    

    private INumber accuracy;


    public AccuracyCheck(INumber accuracy) {
        this.accuracy = accuracy;
    }


    @Override 
    public AccuracyCheck deepCopy() {
        return new AccuracyCheck(
            accuracy.deepCopy()
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        List<Branch<Double>> accuracyBranches = accuracy.execute(context);
        List<Branch<Boolean>> outcomes = new ArrayList<>();

        context.dispatchEvent(EventId.ACCURACY_CHECK_EVENT);

        double cachedAccuracyModifier = (double) Value.MOVE_ACCURACY_MODIFIER.get(context);

        for (Branch<Double> branch : accuracyBranches) {
            double accuracyValue = Math.clamp(branch.value() * cachedAccuracyModifier, 0, 1);

            outcomes.add(new Branch<>(true, accuracyValue));
            outcomes.add(new Branch<>(false, 1-accuracyValue));
        }

        return optimise(outcomes);
    }
}
