package github.snomfish.functionality.condition.conditions;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.ValueConstant;

public class LessThanEqualTo implements ICondition {
    
    
    private INumber leftNumber;
    private INumber rightNumber;


    public LessThanEqualTo(INumber leftNumber, INumber rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
    }
    public LessThanEqualTo(Value leftNumber, INumber rightNumber) {
        this.leftNumber = new ValueConstant(leftNumber);
        this.rightNumber = rightNumber;
    }
    public LessThanEqualTo(INumber leftNumber, Value rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = new ValueConstant(rightNumber);
    }
    public LessThanEqualTo(Value leftNumber, Value rightNumber) {
        this.leftNumber = new ValueConstant(leftNumber);
        this.rightNumber = new ValueConstant(rightNumber);
    }

    
    private LessThanEqualTo(LessThanEqualTo copy) {
        this.leftNumber = copy.leftNumber.deepCopy();
        this.rightNumber = copy.rightNumber.deepCopy();
    }


    public LessThanEqualTo deepCopy() {
        return new LessThanEqualTo(this);
    }


    public List<Branch<Boolean>> execute(BattleContext context) {
        
        List<Branch<Double>> leftBranches = leftNumber.execute(context);
        List<Branch<Double>> rightBranches = rightNumber.execute(context);

        Double trueProbability = 0.0;
        Double falseProbability = 0.0;

        for (Branch<Double> leftBranch : leftBranches) {
            Double leftValue = leftBranch.value();
            Double leftProbability = leftBranch.probability();
            
            for (Branch<Double> rightBranch : rightBranches) {
                Double rightValue = rightBranch.value();
                Double rightProbability = rightBranch.probability();

                if (leftValue <= rightValue) {
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
