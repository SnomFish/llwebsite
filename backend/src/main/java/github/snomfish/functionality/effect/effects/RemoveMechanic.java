package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class RemoveMechanic implements IEffect {
        

    private Value<ActiveLoomian> activeLoomianValue;
    private LoomianMechanicId mechanicId;


    private RemoveMechanic(
        Value<ActiveLoomian> activeLoomianValue,
        LoomianMechanicId mechanicId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.mechanicId = mechanicId;
    }
    public static RemoveMechanic fromUser(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(Value.USER().LOOMIAN(), mechanicId);
    }
    public static RemoveMechanic fromTarget(LoomianMechanicId mechanicId) {
        return new RemoveMechanic(Value.TARGET().LOOMIAN(), mechanicId);
    }


    private RemoveMechanic(RemoveMechanic copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
        this.mechanicId = copy.mechanicId;
    }
    @Override 
    public RemoveMechanic deepCopy() {
        return new RemoveMechanic(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        ActiveLoomian loomian = (ActiveLoomian) activeLoomianValue.get(context);
        loomian.mechanics().remove(mechanicId);
        
        return List.of(new Branch<>(context, 1.0)); 
    }
}
