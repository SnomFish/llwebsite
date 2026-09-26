package github.snomfish.functionality.condition;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;

public class GreaterThan implements ICondition {
        

    private Value value;
    private INumber number;


    public GreaterThan(
        Value value,
        INumber number
    ) {
        this.value = value;
        this.number = number;
    }


    public GreaterThan(GreaterThan copy) {
        this.value = copy.value;
        this.number = copy.number.deepCopy();
    }
    @Override
    public GreaterThan deepCopy() {
        return new GreaterThan(this);
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        List<Branch<Boolean>> outcomes = new ArrayList<>();
        List<Branch<Double>> numberBranches = number.execute(context);
        Double number1 = (Double) value.get(context);

        for (Branch<Double> numberBranch : numberBranches) {
            Double number2 = numberBranch.value();
            outcomes.add(new Branch<Boolean>(
                number1 > number2,
                numberBranch.probability()
            ));
        }

        return outcomes;
    }
}
