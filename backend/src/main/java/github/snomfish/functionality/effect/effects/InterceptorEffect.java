package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

public class InterceptorEffect implements IEffect {
    

    private RuleForAllId id;
    private ContextSideId sideId;
    private IEffect trueEffect;


    public InterceptorEffect(
        RuleForAllId id,
        ContextSideId sideId,
        IEffect trueEffect
    ) {
        this.id = id;
        this.sideId = sideId;
        this.trueEffect = trueEffect;
    }


    private InterceptorEffect(InterceptorEffect copy) {
        this.id = copy.id;
        this.sideId = copy.sideId;
        this.trueEffect = copy.trueEffect.deepCopy();
    }
    public InterceptorEffect deepCopy() {
        return new InterceptorEffect(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return context.dispatchInterceptor(id, sideId, trueEffect.execute(context));
    }
}
