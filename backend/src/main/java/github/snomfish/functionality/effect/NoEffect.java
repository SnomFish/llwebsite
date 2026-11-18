package github.snomfish.functionality.effect;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class NoEffect implements IEffect {
    
    
    public NoEffect() {}


    @Override 
    public NoEffect deepCopy() {
        return new NoEffect();
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return List.of(new Branch<>(context, 1.0));
    }
}
