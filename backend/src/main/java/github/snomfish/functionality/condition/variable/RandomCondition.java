package github.snomfish.functionality.condition.variable;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

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
    public double execute(BattleContext context) {
        double probability = 0;
        
        // ASSUMES THE PROBABILITIES SUM TO 1
        for (Branch<Double> branch : number.execute(context)) {
            probability += Math.clamp(branch.value(), 0, 1) * branch.probability();    
        }

        checkProbability(probability);
        return probability;
    }
}
