package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class MoveEffect implements IMove {

    
    private IEffect effect;
    

    public MoveEffect(IEffect effect) {
        this.effect = effect;
    }


    @Override 
    public MoveEffect deepCopy() {
        return new MoveEffect(
            effect.deepCopy()
        );
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return effect.execute(context);
    }
}