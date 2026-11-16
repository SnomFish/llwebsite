package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.SideId;
import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.context.ContextUtil;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class ApplyMechanic implements IEffect {
    

    private ContextSideId sideId;
    private Value<MechanicInstance> mechanicInstanceValue;
    private IMechanicId mechanicId;
    private int maxAge;


    private ApplyMechanic(
        ContextSideId sideId,
        Value<MechanicInstance> mechanicInstanceValue,
        IMechanicId mechanicId
    ) {
        this.sideId = sideId;
        this.mechanicInstanceValue = mechanicInstanceValue;
        this.mechanicId = mechanicId;
        this.maxAge = 999;
    }
    public static ApplyMechanic toUser(LoomianMechanicId mechanicId) {
        return new ApplyMechanic(ContextSideId.USER, Value.USER().LOOMIAN_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toTarget(LoomianMechanicId mechanicId) {
        return new ApplyMechanic(ContextSideId.TARGET, Value.TARGET().LOOMIAN_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toUserSide(SideMechanicId mechanicId) {
        return new ApplyMechanic(ContextSideId.USER, Value.USER().SIDE_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toTargetSide(SideMechanicId mechanicId) {
        return new ApplyMechanic(ContextSideId.TARGET, Value.TARGET().SIDE_MECHANIC(mechanicId), mechanicId);
    }
    public static ApplyMechanic toField(FieldMechanicId mechanicId) {
        return new ApplyMechanic(ContextSideId.USER, Value.FIELD_MECHANIC(mechanicId), mechanicId);
    }

    public ApplyMechanic withMaxAge(int maxAge) {this.maxAge = maxAge; return this;}


    private ApplyMechanic(ApplyMechanic copy) {
        this.sideId = copy.sideId;
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
            return wrap(context); 

        } else {
            context.log("applying mechanic");
            BattleContext outcome = context.deepCopy();
            instance = new MechanicInstance(mechanicId, maxAge);
            mechanicInstanceValue.set(outcome, instance);

            SideId savedSideId = outcome.userState();
            ContextUtil.setFocus(outcome, sideId);
            List<Branch<BattleContext>> outcomes = instance.mechanic().onAppliedEffect().execute(outcome);
            outcomes = ContextUtil.setFocus(outcomes, savedSideId);
            return outcomes;

        }
        
    }
}
