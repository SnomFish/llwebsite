package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ApplyMechanic implements IEffect {
    

    private Value<ActiveLoomian> activeLoomianValue;
    private MechanicId mechanicId;


    private ApplyMechanic(
        Value<ActiveLoomian> activeLoomianValue,
        MechanicId mechanicId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.mechanicId = mechanicId;
    }
    public static ApplyMechanic toUser(MechanicId mechanicId) {
        return new ApplyMechanic(Value.USER().LOOMIAN(), mechanicId);
    }
    public static ApplyMechanic toTarget(MechanicId mechanicId) {
        return new ApplyMechanic(Value.TARGET().LOOMIAN(), mechanicId);
    }


    private ApplyMechanic(ApplyMechanic copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
        this.mechanicId = copy.mechanicId;
    }
    @Override 
    public ApplyMechanic deepCopy() {
        return new ApplyMechanic(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        ActiveLoomian loomian = (ActiveLoomian) activeLoomianValue.get(context);
        MechanicInstance instance = new MechanicInstance(mechanicId);

        if (loomian.mechanics().has(mechanicId)) {

            context.log(loomian.species().name() + " already has " + mechanicId.name());
        } else {
            
            context.log("Applying mechanic " + mechanicId.name() + " to " + loomian.species().name());
            loomian.mechanics().set(instance);
        }
        
        return List.of(new Branch<>(context, 1.0)); 
    }
}
