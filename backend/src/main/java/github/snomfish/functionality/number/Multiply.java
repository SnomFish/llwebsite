package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Multiply implements INumber {
    

    private INumber left;
    private INumber right;


    public Multiply(
        INumber left,
        INumber right
    ) {
        this.left = left;
        this.right = right;
    }
    public Multiply(Multiply copy) {
        this.left = copy.left.deepCopy();
        this.right = copy.right.deepCopy();
    }


    @Override 
    public Multiply deepCopy() {
        return new Multiply(this);
    }


    @Override
    public List<Branch<Double>> execute(BattleContext context) {
        List<Branch<Double>> outcomes = new ArrayList<>();

        for (Branch<Double> leftBranch : left.execute(context)) {
            Double leftValue = leftBranch.value();
            Double leftProbability = leftBranch.probability();

            for (Branch<Double> rightBranch : right.execute(context)) {
                Double rightValue = rightBranch.value();
                Double rightProbability = rightBranch.probability();

                outcomes.add(new Branch<>(
                    leftValue * rightValue,
                    leftProbability * rightProbability
                ));
            }
        }

        return outcomes;
    }
}
