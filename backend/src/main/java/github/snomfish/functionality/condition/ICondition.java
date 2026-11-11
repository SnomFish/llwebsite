package github.snomfish.functionality.condition;

import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface ICondition extends DeepCopyable<ICondition> {
    

    // the Duble will always be between 0 and 1 and represents the probability of of true
    double execute(BattleContext context);
}
