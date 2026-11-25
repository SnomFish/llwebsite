package github.snomfish.functionality.condition.variable;

import java.util.List;

import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

public class AccuracyCheck implements ICondition {
    

    private INumber accuracyNumber;


    public AccuracyCheck(INumber accuracyNumber) {
        this.accuracyNumber = accuracyNumber;
    }


    private AccuracyCheck(AccuracyCheck copy) {
        this.accuracyNumber = copy.accuracyNumber.deepCopy();
    }
    public AccuracyCheck deepCopy() {
        return new AccuracyCheck(this);
    }

    
    public double execute(BattleContext context) {
        double probability = 1;

        probability *= executeAccuracyCheck(context);
        probability *= context.dispatchGate(GateForAllId.CAN_MOVE_HIT_LOOMIAN, ContextSideId.USER);

        return probability;
    }


    private double executeAccuracyCheck(BattleContext context) {
        List<Branch<Double>> accuracyBranches = accuracyNumber.execute(context);
        double probability = 0;

        context.dispatchRule(RuleForAllId.PRE_CHECK_ACCURACY, ContextSideId.USER);

        double cachedAccuracyModifier = (double) Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ACCURACY).get(context);

        for (Branch<Double> accuracyBranch : accuracyBranches) {
            double accuracyValue = Math.clamp(accuracyBranch.value() * cachedAccuracyModifier, 0, 1);

            probability *= accuracyValue * accuracyBranch.probability();
        }

        checkProbability(probability);
        return probability;
    }
}
