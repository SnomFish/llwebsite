package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

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
