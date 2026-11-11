package github.snomfish.functionality.condition;

import github.snomfish.functionality.context.BattleContext;

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
        Double probabilityA = conditionA.execute(context);
        Double probabilityB = conditionB.execute(context);

        return (probabilityA + probabilityB) - (probabilityA * probabilityB);
    }
}
