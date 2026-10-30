package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class DispatchEventEffect implements IEffect {
    

    private EventId eventId;


    public DispatchEventEffect(EventId eventId) {
        this.eventId = eventId;
    }
    

    private DispatchEventEffect(DispatchEventEffect copy) {
        this.eventId = copy.eventId;
    }
    public DispatchEventEffect deepCopy() {
        return new DispatchEventEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(eventId));
    
        return outcomes;
    }
}
