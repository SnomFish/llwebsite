package github.snomfish.functionality.condition.comparisons;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.ValueConstant;

public class GreaterThan implements ICondition {
        

    private INumber leftNumber;
    private INumber rightNumber;


    public GreaterThan(INumber leftNumber, INumber rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
    }
    public GreaterThan(INumber leftNumber, double rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = new Constant(rightNumber);
    }
    public GreaterThan(INumber leftNumber, Value rightNumber) {
        this.leftNumber = leftNumber;
        this.rightNumber = new ValueConstant(rightNumber);
    }
    public GreaterThan(Double leftNumber, INumber rightNumber) {
        this.leftNumber = new Constant(leftNumber);
        this.rightNumber = rightNumber;
    }
    public GreaterThan(Double leftNumber, Double rightNumber) {
        this.leftNumber = new Constant(leftNumber);
        this.rightNumber = new Constant(rightNumber);
    }
    public GreaterThan(Double leftNumber, Value rightNumber) {
        this.leftNumber = new Constant(leftNumber);
        this.rightNumber = new ValueConstant(rightNumber);
    }
    public GreaterThan(Value leftNumber, INumber rightNumber) {
        this.leftNumber = new ValueConstant(leftNumber);
        this.rightNumber = rightNumber;
    }
    public GreaterThan(Value leftNumber, Double rightNumber) {
        this.leftNumber = new ValueConstant(leftNumber);
        this.rightNumber = new Constant(rightNumber);
    }
    public GreaterThan(Value leftNumber, Value rightNumber) {
        this.leftNumber = new ValueConstant(leftNumber);
        this.rightNumber = new ValueConstant(rightNumber);
    }
    


    public GreaterThan(GreaterThan copy) {
        this.leftNumber = copy.leftNumber.deepCopy();
        this.rightNumber = copy.rightNumber.deepCopy();
    }
    @Override
    public GreaterThan deepCopy() {
        return new GreaterThan(this);
    }


    @Override 
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

                if (leftValue > rightValue) {
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
