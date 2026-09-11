package github.snomfish.functionality.condition;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.condition.ConditionUtil.optimise;

public class RandomCondition implements ICondition {
    

    private final INumber number;


    public RandomCondition (INumber number) {
        this.number = number;
    }


    @Override 
    public RandomCondition deepCopy() {
        return new RandomCondition(
            number.deepCopy()
        );
    }


    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {
        List<Branch<Double>> branches = number.execute(context);
        List<Branch<Boolean>> outcomes = new ArrayList<>();

        for (Branch<Double> branch : branches) {    
            Double value = Math.clamp(branch.value(), 0, 1);

            outcomes.add(new Branch<>(true, value));
            outcomes.add(new Branch<>(false, 1-value));
        }

        return optimise(outcomes);
    }
}
