package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ApplyStatus implements IEffect {
    

    private Value activeLoomianValue;
    private StatusId statusId;


    private ApplyStatus(
        Value activeLoomianValue,
        StatusId statusId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.statusId = statusId;
    }
    public static ApplyStatus toUser(StatusId statusId) {
        return new ApplyStatus(Value.USER, statusId);
    }
    public static ApplyStatus toTarget(StatusId statusId) {
        return new ApplyStatus(Value.TARGET, statusId);
    }


    private ApplyStatus(ApplyStatus copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
        this.statusId = copy.statusId;
    }
    @Override 
    public ApplyStatus deepCopy() {
        return new ApplyStatus(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        ActiveLoomian loomian = (ActiveLoomian) activeLoomianValue.get(context);
        // TODO: implement
        
        return List.of(new Branch<>(context, 1.0)); 
    }
}
