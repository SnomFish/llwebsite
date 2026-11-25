package github.snomfish.functionality.condition.variable;

import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

public class Or implements ICondition {
      

    private ICondition conditionA;
    private ICondition conditionB;


    public Or(ICondition a, ICondition b) {
        this.conditionA = a;
        this.conditionB = b;
    }
    public Or(ICondition a, ICondition b, ICondition c) {
        this.conditionA = a;
        this.conditionB = new Or(b, c);
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
