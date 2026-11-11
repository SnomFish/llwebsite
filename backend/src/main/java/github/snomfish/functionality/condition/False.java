package github.snomfish.functionality.condition;

import github.snomfish.functionality.context.BattleContext;

public class False implements ICondition {
    

    public False() {}


    public False deepCopy() {
        return new False();
    }


    public double execute(BattleContext context) {
        return 0;
    } 
}
