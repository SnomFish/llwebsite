package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.SideId;
import github.snomfish.domain.mechanics.Mechanics;
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

public class RemoveMechanic implements IEffect {
        

    private ContextSideId sideId;
    private Value<Mechanics> mechanicsValue;
    private IMechanicId mechanicId;


    private RemoveMechanic(
        ContextSideId sideId,
        Value<Mechanics> mechanicsValue,
        IMechanicId mechanicId
    ) {
        this.sideId = sideId;
        this.mechanicsValue = mechanicsValue;
        this.mechanicId = mechanicId;
    }


    public static RemoveMechanic fromUser(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(ContextSideId.USER, Value.USER().LOOMIAN_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromTarget(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(ContextSideId.TARGET, Value.TARGET().LOOMIAN_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromUserSide(SideMechanicId mechanicId) {
        return new RemoveMechanic(ContextSideId.USER, Value.USER().SIDE_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromTargetSide(SideMechanicId mechanicId) {
        return new RemoveMechanic(ContextSideId.TARGET, Value.TARGET().SIDE_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromField(FieldMechanicId mechanicId) {
        return new RemoveMechanic(ContextSideId.USER, Value.FIELD_MECHANICS(), mechanicId);
    }


    private RemoveMechanic(RemoveMechanic copy) {
        this.sideId = copy.sideId;
        this.mechanicsValue = copy.mechanicsValue;
        this.mechanicId = copy.mechanicId;
    }
    @Override 
    public RemoveMechanic deepCopy() {
        return new RemoveMechanic(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        Mechanics mechanics = mechanicsValue.get(context);

        if (!mechanics.has(mechanicId)) return List.of(new Branch<>(context, 1));

        BattleContext outcome = context.deepCopy();
        SideId savedSideId = outcome.userState();
        ContextUtil.setFocus(outcome, sideId);
        List<Branch<BattleContext>> outcomes = mechanics.get(mechanicId).mechanic().onAppliedEffect().execute(outcome);
        outcomes = ContextUtil.setFocus(outcomes, savedSideId);
        return outcomes;
    }
}
