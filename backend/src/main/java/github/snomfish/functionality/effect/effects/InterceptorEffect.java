package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.InterceptorId;

public class InterceptorEffect implements IEffect {
    

    private InterceptorId id;
    private IEffect trueEffect;


    public InterceptorEffect(
        InterceptorId id,
        IEffect trueEffect
    ) {
        this.id = id;
        this.trueEffect = trueEffect;
    }


    private InterceptorEffect(InterceptorEffect copy) {
        this.id = copy.id;
        this.trueEffect = copy.trueEffect.deepCopy();
    }
    public InterceptorEffect deepCopy() {
        return new InterceptorEffect(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return context.dispatchInterceptor(id, trueEffect);
    }
}
