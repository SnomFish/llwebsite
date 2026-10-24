package github.snomfish.functionality.condition.conditions;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;

public class And implements ICondition {
    

    private final ICondition leftCondition;
    private final ICondition rightCondition;


    public And(
        ICondition leftCondition,
        ICondition rightCondition
    ) {
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
    }
    private And(And copy) {
        this.leftCondition = copy.leftCondition.deepCopy();
        this.rightCondition = copy.rightCondition.deepCopy();
    }


    @Override
    public And deepCopy() {
        return new And(this);
    } 


    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {

        List<Branch<Boolean>> leftBranches = leftCondition.execute(context);
        List<Branch<Boolean>> rightBranches = rightCondition.execute(context);

        Double trueProbability = 0.0;
        Double falseProbability = 0.0;

        for (Branch<Boolean> leftBranch : leftBranches) {
            Boolean leftValue = leftBranch.value();
            Double leftProbability = leftBranch.probability();
            
            for (Branch<Boolean> rightBranch : rightBranches) {
                Boolean rightValue = rightBranch.value();
                Double rightProbability = rightBranch.probability();

                if (leftValue && rightValue) {
                    trueProbability += leftProbability * rightProbability;
                } else {
                    falseProbability += leftProbability * rightProbability;
                }
            }
        }

        return List.of(
            new Branch<>(true, trueProbability),
            new Branch<>(false, falseProbability)
        );
    }
}
