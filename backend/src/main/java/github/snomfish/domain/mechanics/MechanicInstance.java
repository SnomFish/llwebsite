package github.snomfish.domain.mechanics;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class MechanicInstance implements DeepCopyable<MechanicInstance> {
    

    private final IMechanicId id;
    private int maxAge;
    private int age;


    public MechanicInstance(
        IMechanicId id,
        int maxAge
    ) {
        this.id = id;
        this.maxAge = maxAge;
        this.age = 0;
    }


    private MechanicInstance(MechanicInstance copy) {
        this.id = copy.id;
        this.maxAge = copy.maxAge;
        this.age = copy.age;
    }
    public MechanicInstance deepCopy() {
        return new MechanicInstance(this);
    }


    // getters
    public IMechanicId id() {return id;}
    public Mechanic mechanic() {return MechanicRegistry.get(id);}
    public int maxAge() {return maxAge;}
    public int age() {return age;}


    // setters
    public void maxAge(int maxAge) {this.maxAge = maxAge;}
    public void age(int age) {this.age = age;}


    public void incrementAge() {
        this.age++;
    }


    public HookReport handleHook(BattleContext context, IRuleId ruleId, HookReport report) {
        return mechanic().handleHook(context, ruleId, report);
    }
    public double handleGate(BattleContext context, IGateId gateId) {
        return mechanic().handleGate(context, gateId);
    }
    public boolean handleDoor(BattleContext context, IGateId gateId) {
        return mechanic().handleDoor(context, gateId);
    }
}
