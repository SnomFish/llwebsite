package github.snomfish.functionality.condition;

import github.snomfish.functionality.context.BattleContext;

public class True implements ICondition {
    

    public True() {}


    public True deepCopy() {
        return new True();
    }


    public double execute(BattleContext context) {
        // does not need to be checked
        return 1.0;
    } 
}
