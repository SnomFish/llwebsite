package github.snomfish.functionality.condition;

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
        return 1 - condition.execute(context);
    }
}
