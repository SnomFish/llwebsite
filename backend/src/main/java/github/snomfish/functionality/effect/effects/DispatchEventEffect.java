package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ids.ToUserId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class DispatchEventEffect implements IEffect {
    

    private ToUserId genericId;
    private ContextSideId sideId;


    public DispatchEventEffect(
        ToUserId genericId,
        ContextSideId sideId
    ) {
        this.genericId = genericId;
        this.sideId = sideId;
    }
    

    private DispatchEventEffect(DispatchEventEffect copy) {
        this.genericId = copy.genericId;
        this.sideId = copy.sideId;
    }
    public DispatchEventEffect deepCopy() {
        return new DispatchEventEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(genericId, sideId));
    
        return outcomes;
    }
}
