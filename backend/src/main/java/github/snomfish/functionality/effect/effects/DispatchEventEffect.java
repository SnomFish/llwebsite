package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.eventIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class DispatchEventEffect implements IEffect {
    

    private RuleForAllId eventId;
    private ContextSideId sideId;


    public DispatchEventEffect(
        RuleForAllId eventId,
        ContextSideId sideId
    ) {
        this.eventId = eventId;
        this.sideId = sideId;
    }
    

    private DispatchEventEffect(DispatchEventEffect copy) {
        this.eventId = copy.eventId;
        this.sideId = copy.sideId;
    }
    public DispatchEventEffect deepCopy() {
        return new DispatchEventEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(eventId, sideId));
    
        return outcomes;
    }
}
