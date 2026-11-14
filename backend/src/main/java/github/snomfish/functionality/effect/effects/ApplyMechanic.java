package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ApplyMechanic implements IEffect {
    

    private Value<MechanicInstance> mechanicInstanceValue;
    private IMechanicId mechanicId;


    private ApplyMechanic(
        Value<MechanicInstance> mechanicInstanceValue,
        IMechanicId mechanicId
    ) {
        this.mechanicInstanceValue = mechanicInstanceValue;
        this.mechanicId = mechanicId;
    }
    public static ApplyMechanic toUser(LoomianMechanicId mechanicId) {
        return new ApplyMechanic(Value.USER().LOOMIAN_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toTarget(LoomianMechanicId mechanicId) {
        return new ApplyMechanic(Value.TARGET().LOOMIAN_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toUserSide(SideMechanicId mechanicId) {
        return new ApplyMechanic(Value.USER().SIDE_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toTargetSide(SideMechanicId mechanicId) {
        return new ApplyMechanic(Value.TARGET().SIDE_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toField(FieldMechanicId mechanicId) {
        return new ApplyMechanic(Value.FIELD_MECHANIC(mechanicId), mechanicId);
    }


    private ApplyMechanic(ApplyMechanic copy) {
        this.mechanicInstanceValue = copy.mechanicInstanceValue;
        this.mechanicId = copy.mechanicId;
    }
    @Override 
    public ApplyMechanic deepCopy() {
        return new ApplyMechanic(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        MechanicInstance instance = mechanicInstanceValue.get(context);

        if (instance != null) {
            context.log("unable to add mechanic");
            return List.of(new Branch<>(context, 1.0)); 

        } else {
            context.log("applying mechanic");
            BattleContext outcome = context.deepCopy();
            instance = new MechanicInstance(mechanicId);
            mechanicInstanceValue.set(outcome, instance);
            return List.of(new Branch<>(outcome, 1.0));

        }
        
    }
}
