package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class ApplyStatus implements IEffect {
    

    private Value<ActiveLoomian> activeLoomianValue;
    private ContextSideId sideId;
    private StatusId statusId;


    private ApplyStatus(
        Value<ActiveLoomian> activeLoomianValue,
        ContextSideId sideId,
        StatusId statusId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.sideId = sideId;
        this.statusId = statusId;
    }
    public static ApplyStatus toUser(StatusId statusId) {
        return new ApplyStatus(Value.USER().LOOMIAN(), ContextSideId.USER, statusId);
    }
    public static ApplyStatus toTarget(StatusId statusId) {
        return new ApplyStatus(Value.TARGET().LOOMIAN(), ContextSideId.TARGET, statusId);
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
        List<Branch<BattleContext>> outcomes = wrap(context);

        outcomes = flatMap(outcomes, this::executeStatus);
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMIAN_CHANGES_STATUS, sideId));
        
        return List.of(new Branch<>(context, 1.0)); 
    }


    private List<Branch<BattleContext>> executeStatus(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        ActiveLoomian loomian = activeLoomianValue.get(outcome);
        
        loomian.setStatusId(statusId);
        loomian.setStatusTurns(0);

        return wrap(outcome);
    }
}
