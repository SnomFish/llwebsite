package github.snomfish.functionality.effect.effects;

import static github.snomfish.domain.status.StatusId.NO_STATUS;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ClearStatus implements IEffect {
    

    private Value<ActiveLoomian> activeLoomianValue;


    private ClearStatus(Value<ActiveLoomian> activeLoomianValue) {
        this.activeLoomianValue = activeLoomianValue;
    }
    public static ClearStatus toUser() {
        return new ClearStatus(Value.USER().LOOMIAN());
    }
    public static ClearStatus toTarget() {
        return new ClearStatus(Value.TARGET().LOOMIAN());
    }


    private ClearStatus(ClearStatus copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
    }
    @Override 
    public ClearStatus deepCopy() {
        return new ClearStatus(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        ActiveLoomian loomian = (ActiveLoomian) activeLoomianValue.get(outcome);
        loomian.setStatusId(NO_STATUS);
        loomian.setStatusTurns(0);
        
        return List.of(new Branch<>(outcome, 1.0)); 
    }
}
