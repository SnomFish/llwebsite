package github.snomfish.functionality.condition.variable;

import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

public class Or implements ICondition {
      

    private ICondition conditionA;
    private ICondition conditionB;


    public Or(ICondition conditionA, ICondition conditionB) {
        this.conditionA = conditionA;
        this.conditionB = conditionB;
    }


    private Or(Or copy) {
        this.conditionA = copy.conditionA.deepCopy();
        this.conditionB = copy.conditionB.deepCopy();
    }
    @Override
    public Or deepCopy() {
        return new Or(this);
    } 


    @Override
    public double execute(BattleContext context) {
        double probabilityA = conditionA.execute(context);
        double probabilityB = conditionB.execute(context);

        double probability = (probabilityA + probabilityB) - (probabilityA * probabilityB);
        checkProbability(probability);
        return probability;
    }
}
