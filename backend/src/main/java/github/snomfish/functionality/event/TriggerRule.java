package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class TriggerRule {
     

    private List<Event> events;
    private IEffect effect;


    public TriggerRule(List<Event> events, IEffect effect) {
        this.events = events;
        this.effect = effect;
    }
    public TriggerRule(Event event, IEffect effect) {
        this.events = List.of(event);
        this.effect = effect;
    }


    public List<Branch<BattleContext>> execute(BattleContext context, Event event) {
        
        if (!events.contains(event)) {
            return List.of(new Branch<>(context, 1.0));
        }
        return effect.execute(context);
    }
}
