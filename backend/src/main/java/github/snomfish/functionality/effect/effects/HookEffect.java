package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

public class HookEffect implements IEffect {
    

    private RuleForAllId id;
    private ContextSideId sideId;
    private IEffect trueEffect;


    public HookEffect(
        RuleForAllId id,
        ContextSideId sideId,
        IEffect trueEffect
    ) {
        this.id = id;
        this.sideId = sideId;
        this.trueEffect = trueEffect;
    }


    private HookEffect(HookEffect copy) {
        this.id = copy.id;
        this.sideId = copy.sideId;
        this.trueEffect = copy.trueEffect.deepCopy();
    }
    public HookEffect deepCopy() {
        return new HookEffect(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return context.dispatchHook(id, sideId, trueEffect.execute(context));
    }
}
