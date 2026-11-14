package github.snomfish.domain.mechanics;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Report;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class MechanicInstance implements DeepCopyable<MechanicInstance> {
    

    private final MechanicId id;
    private int age;


    public MechanicInstance(MechanicId id) {
        this.id = id;
        this.age = 0;
    }


    private MechanicInstance(MechanicInstance copy) {
        this.id = copy.id;
        this.age = copy.age;
    }
    public MechanicInstance deepCopy() {
        return new MechanicInstance(this);
    }


    // getters
    public MechanicId id() {return id;}
    public Mechanic mechanic() {return MechanicRegistry.get(id);}
    public int age() {return age;}


    // setters
    public void age(int age) {this.age = age;}


    public void incrementAge() {
        this.age++;
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, IRuleId ruleId) {
        return mechanic().handleRule(context, ruleId);
    }
    public double handleGate(BattleContext context, IGateId gateId) {
        return mechanic().handleGate(context, gateId);
    }
    public Report handleInterceptor(BattleContext context, IRuleId ruleId, Report report) {
        return mechanic().handleInterceptor(context, ruleId, report);
    }
}
