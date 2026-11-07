package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class Mechanics implements DeepCopyable<Mechanics> {
    

    private Map<MechanicId, Boolean> mechanics;


    public Mechanics() {
        this.mechanics = new HashMap<>();
        for (MechanicId key : MechanicId.values()) {
            mechanics.put(key, false);
        }
    }
    public Mechanics(Mechanics copy) {
        this.mechanics = new HashMap<>(copy.mechanics);
    }


    @Override 
    public Mechanics deepCopy() {
        return new Mechanics(this);
    }


    public Boolean has(MechanicId id) {
        return mechanics.get(id);
    }


    public void add(MechanicId id) {
        mechanics.put(id, true);
    }


    public void remove(MechanicId id) {
        mechanics.put(id, false);
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        for (MechanicId id : mechanics.keySet()) {
            if (mechanics.get(id) == false) continue;
            Mechanic mechanic = MechanicRegistry.get(id);
            outcomes = flatMap(outcomes, c -> mechanic.handleRule(c, event));
        }

        return outcomes;
    }
}
