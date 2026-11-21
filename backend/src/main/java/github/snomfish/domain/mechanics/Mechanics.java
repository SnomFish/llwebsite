package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.RuleReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class Mechanics implements DeepCopyable<Mechanics> {
    

    private Map<IMechanicId, MechanicInstance> mechanics;


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


    public boolean has(IMechanicId id) {
        return mechanics.containsKey(id);
    }
    public MechanicInstance get(IMechanicId id) {
        return mechanics.get(id);
    }
    public void set(MechanicInstance instance) {
        mechanics.put(instance.id(), instance);
    }
    public void remove(IMechanicId id) {
        mechanics.remove(id);
    }


    public void dispatchRule(RuleReport report, IRuleId ruleId) {
        for (MechanicInstance instance : mechanics.values()) {
            report.record(instance.mechanic().rules(), ruleId);
        }
    }
    public void dispatchHook(HookReport report, IRuleId hookId) {
        for (MechanicInstance instance : mechanics.values()) {
            report.record(instance.mechanic().hooks(), hookId);
        }
    }
    public double dispatchGate(BattleContext context, IGateId gateId) {
        double probability = 1;
        for (MechanicInstance instance : mechanics.values()) {
            probability *= instance.handleGate(context, gateId);
        }
        return probability;
    }
    public boolean dispatchDoor(BattleContext context, IGateId gateId) {
        for (MechanicInstance instance : mechanics.values()) {
            if (!instance.handleDoor(context, gateId)) return false;
        }
        return true;
    }
}
