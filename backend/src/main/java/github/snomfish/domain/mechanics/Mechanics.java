package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class Mechanics implements DeepCopyable<Mechanics> {
    

    private Map<MechanicId, MechanicInstance> mechanics;


    public Mechanics() {
        this.mechanics = new HashMap<>();
    }
    public Mechanics(Mechanics copy) {
        this.mechanics = new HashMap<>(copy.mechanics);
    }


    @Override 
    public Mechanics deepCopy() {
        return new Mechanics(this);
    }


    public void incrementTurnNumber() {
        for (MechanicInstance mechanic : mechanics.values()) {
            mechanic.incrementAge();
        }
    }


    public boolean has(MechanicId id) {
        return mechanics.containsKey(id);
    }
    public MechanicInstance get(MechanicId id) {
        return mechanics.get(id);
    }
    public void set(MechanicInstance instance) {
        mechanics.put(instance.id(), instance);
    }
    public void remove(MechanicId id) {
        mechanics.remove(id);
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, IRuleId event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        for (MechanicId id : mechanics.keySet()) {
            if (!mechanics.containsKey(id)) continue;

            Mechanic mechanic = MechanicRegistry.get(id);
            outcomes = flatMap(outcomes, c -> mechanic.handleRule(c, event));
        }

        return outcomes;
    }
}
