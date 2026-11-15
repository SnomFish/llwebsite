package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class RemoveMechanic implements IEffect {
        

    private Value<Mechanics> mechanicsValue;
    private IMechanicId mechanicId;


    private RemoveMechanic(
        Value<Mechanics> mechanicsValue,
        IMechanicId mechanicId
    ) {
        this.mechanicsValue = mechanicsValue;
        this.mechanicId = mechanicId;
    }


    public static RemoveMechanic fromUser(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(Value.USER().LOOMIAN_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromTarget(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(Value.TARGET().LOOMIAN_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromUserSide(SideMechanicId mechanicId) {
        return new RemoveMechanic(Value.USER().SIDE_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromTargetSide(SideMechanicId mechanicId) {
        return new RemoveMechanic(Value.TARGET().SIDE_MECHANICS(), mechanicId);
    }
    public static RemoveMechanic fromField(FieldMechanicId mechanicId) {
        return new RemoveMechanic(Value.FIELD_MECHANICS(), mechanicId);
    }


    private RemoveMechanic(RemoveMechanic copy) {
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
        mechanics = mechanicsValue.get(outcome);
        mechanics.remove(mechanicId);

        return List.of(new Branch<>(outcome, 1));
    }
}
