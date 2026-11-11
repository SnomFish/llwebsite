package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.condition.ComparatorId.*;

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
    public ICondition deepCopy() {
        return new Comparator(this);
    }


    // builder functions
    public static Comparator greaterThanEqualTo() {
        return new Comparator(GREATER_THAN_EQUAL_TO, new Constant(0), new Constant(0));
    }
    public static Comparator lessThanEqualTo() {
        return new Comparator(LESS_THAN_EQUAL_TO, new Constant(0), new Constant(0));
    }
    public static Comparator greaterThan() {
        return new Comparator(GREATER_THAN, new Constant(0), new Constant(0));
    }
    public static Comparator lessThan() {
        return new Comparator(LESS_THAN, new Constant(0), new Constant(0));
    }
    public Comparator left(INumber leftNumber) {
        this.leftNumber = leftNumber;
        return this;
    }
    public Comparator right(INumber rightNumber) {
        this.rightNumber = rightNumber;
        return this;
    }


    public double execute(BattleContext context) {
        double numOfTrueComparisons = 0; // this is a double because I need to do division at the end
        double numOfComparisons = 0;

        List<Branch<Double>> leftBranches = leftNumber.execute(context);
        List<Branch<Double>> rightBranches = rightNumber.execute(context);

        for (Branch<Double> leftBranch : leftBranches) {
            for (Branch<Double> rightBranch : rightBranches) {
                
                numOfComparisons++;
                if (comparator.apply(leftBranch.value(), rightBranch.value())) {
                    numOfTrueComparisons++;
                }
            }
        }

        return numOfTrueComparisons / numOfComparisons;
    }
}
