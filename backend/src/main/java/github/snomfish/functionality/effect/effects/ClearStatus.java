package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Loomian;
import github.snomfish.domain.Side;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;
import static github.snomfish.domain.status.StatusId.NO_STATUS;

public class ClearStatus implements IEffect {
    

    public enum Mode {
        ACTIVE,
        PARTY
    }
    private Mode mode;
    private Value<Side> sideValue;


    private ClearStatus(
        Mode mode,
        Value<Side> sideValue
    ) {
        this.mode = mode;
        this.sideValue = sideValue;
    }
    public static ClearStatus user() {
        return new ClearStatus(Mode.ACTIVE, Value.USER());
    }
    public static ClearStatus target() {
        return new ClearStatus(Mode.ACTIVE, Value.TARGET());
    }
    public static ClearStatus userParty() {
        return new ClearStatus(Mode.PARTY, Value.USER());
    }
    public static ClearStatus targetParty() {
        return new ClearStatus(Mode.PARTY, Value.TARGET());
    }


    private ClearStatus(ClearStatus copy) {
        this.mode = copy.mode;
        this.sideValue = copy.sideValue;
    }
    public ClearStatus deepCopy() {
        return new ClearStatus(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return switch (mode) {
            case ACTIVE ->  executeOnLoomian(context);
            case PARTY ->   executeOnParty(context);
        };
    }


    private List<Branch<BattleContext>> executeOnLoomian(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        ActiveLoomian loomian = sideValue.LOOMIAN().get(outcome);
        loomian.setStatusId(NO_STATUS);
        loomian.setStatusTurns(0);

        return wrap(outcome);
    }


    private List<Branch<BattleContext>> executeOnParty(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        ActiveLoomian activeLoomian = sideValue.LOOMIAN().get(outcome);
        activeLoomian.setStatusId(NO_STATUS);
        activeLoomian.setStatusTurns(0);

        for (Loomian loomain : sideValue.PARTY().get(outcome)) {
            loomain.setStatusId(NO_STATUS);
            // partied loomians do not store their status turns
        }

        return wrap(outcome);
    }
}
