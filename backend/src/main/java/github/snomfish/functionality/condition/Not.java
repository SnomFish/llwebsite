package github.snomfish.functionality.condition;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

import github.snomfish.functionality.context.BattleContext;

public class Not implements ICondition {
    

    private ICondition condition;


    public Not(ICondition condition) {
        this.condition = condition;
    }


    private Not(Not copy) {
        this.condition = copy.condition.deepCopy();
    }
    @Override 
    public Not deepCopy() {
        return new Not(this);
    }


    @Override 
    public double execute(BattleContext context) {
        double probability = 1 - condition.execute(context);
        checkProbability(probability);
        return probability;
    }
}
