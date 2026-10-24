package github.snomfish.functionality.condition.comparisons;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.ValueConstant;
import github.snomfish.functionality.Value;

import static github.snomfish.functionality.condition.comparisons.ComparatorId.*;

public class Comparator implements ICondition {
    


    private ComparatorId comparator;
    private INumber leftNumber;
    private INumber rightNumber;


    private Comparator(
        ComparatorId comparator,
        INumber leftNumber,
        INumber rightNumber
    ) {
        this.comparator = comparator;
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
    }
    private Comparator(Comparator copy) {
        this.comparator = copy.comparator;
        this.leftNumber = copy.leftNumber.deepCopy();
        this.rightNumber = copy.rightNumber.deepCopy();
    }


    public Comparator deepCopy() {
        return new Comparator(this);
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

                if (applyComparator(leftValue, rightValue)) {
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


    private Boolean applyComparator(
        double leftValue,
        double rightValue
    ) {
        return switch (comparator) {
            case EQUAL_TO -> (leftValue == rightValue);
            case LESS_THAN -> (leftValue < rightValue);
            case GREATER_THAN -> (leftValue > rightValue);
            case LESS_THAN_EQUAL_TO -> (leftValue <= rightValue);
            case GREATER_THAN_EQUAL_TO -> (leftValue >= rightValue);
        };
    }


    public static Comparator greaterThan(INumber leftNumber, INumber rightNumber) {
        return new Comparator(GREATER_THAN, leftNumber, rightNumber);
    }
    public static Comparator greaterThan(INumber leftNumber, Value rightNumber) {
        return new Comparator(GREATER_THAN, leftNumber, new ValueConstant(rightNumber));
    }
    public static Comparator greaterThan(Value leftNumber, INumber rightNumber) {
        return new Comparator(GREATER_THAN, new ValueConstant(leftNumber), rightNumber);
    }
    public static Comparator greaterThan(Value leftNumber, Value rightNumber) {
        return new Comparator(GREATER_THAN, new ValueConstant(leftNumber), new ValueConstant(rightNumber));
    }

    public static Comparator lessThan(INumber leftNumber, INumber rightNumber) {
        return new Comparator(LESS_THAN, leftNumber, rightNumber);
    }
    public static Comparator lessThan(INumber leftNumber, Value rightNumber) {
        return new Comparator(LESS_THAN, leftNumber, new ValueConstant(rightNumber));
    }
    public static Comparator lessThan(Value leftNumber, INumber rightNumber) {
        return new Comparator(LESS_THAN, new ValueConstant(leftNumber), rightNumber);
    }
    public static Comparator lessThan(Value leftNumber, Value rightNumber) {
        return new Comparator(LESS_THAN, new ValueConstant(leftNumber), new ValueConstant(rightNumber));
    }

    public static Comparator equaTo(INumber leftNumber, INumber rightNumber) {
        return new Comparator(EQUAL_TO, leftNumber, rightNumber);
    }
    public static Comparator equaTo(INumber leftNumber, Value rightNumber) {
        return new Comparator(EQUAL_TO, leftNumber, new ValueConstant(rightNumber));
    }
    public static Comparator equaTo(Value leftNumber, INumber rightNumber) {
        return new Comparator(EQUAL_TO, new ValueConstant(leftNumber), rightNumber);
    }
    public static Comparator equaTo(Value leftNumber, Value rightNumber) {
        return new Comparator(EQUAL_TO, new ValueConstant(leftNumber), new ValueConstant(rightNumber));
    }

    public static Comparator greaterThanEqualTo(INumber leftNumber, INumber rightNumber) {
        return new Comparator(GREATER_THAN_EQUAL_TO, leftNumber, rightNumber);
    }
    public static Comparator greaterThanEqualTo(INumber leftNumber, Value rightNumber) {
        return new Comparator(GREATER_THAN_EQUAL_TO, leftNumber, new ValueConstant(rightNumber));
    }
    public static Comparator greaterThanEqualTo(Value leftNumber, INumber rightNumber) {
        return new Comparator(GREATER_THAN_EQUAL_TO, new ValueConstant(leftNumber), rightNumber);
    }
    public static Comparator greaterThanEqualTo(Value leftNumber, Value rightNumber) {
        return new Comparator(GREATER_THAN_EQUAL_TO, new ValueConstant(leftNumber), new ValueConstant(rightNumber));
    }

    public static Comparator lessThanEqualTo(INumber leftNumber, INumber rightNumber) {
        return new Comparator(LESS_THAN_EQUAL_TO, leftNumber, rightNumber);
    }
    public static Comparator lessThanEqualTo(INumber leftNumber, Value rightNumber) {
        return new Comparator(LESS_THAN_EQUAL_TO, leftNumber, new ValueConstant(rightNumber));
    }
    public static Comparator lessThanEqualTo(Value leftNumber, INumber rightNumber) {
        return new Comparator(LESS_THAN_EQUAL_TO, new ValueConstant(leftNumber), rightNumber);
    }
    public static Comparator lessThanEqualTo(Value leftNumber, Value rightNumber) {
        return new Comparator(LESS_THAN_EQUAL_TO, new ValueConstant(leftNumber), new ValueConstant(rightNumber));
    }
}
