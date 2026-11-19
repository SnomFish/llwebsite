package github.snomfish.functionality.action;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Loomian;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class SwitchAction implements IAction {
    

    private int priority;
    private int switchTarget;


    public SwitchAction(
        int priority,
        int switchTarget
    ) {
        this.priority = priority;
        this.switchTarget = switchTarget;
    }

    
    protected SwitchAction(SwitchAction copy) {
        this.priority = copy.priority;
        this.switchTarget = copy.switchTarget;
    }
    public SwitchAction deepCopy() {
        return new SwitchAction(this);
    }


    // getter
    public ActionId id() {return ActionId.SWITCH;}
    public int priority() {return priority;}
    public Move move() {throw new RuntimeException("Attempted to get active move from a switch action");}
    public int switchTarget() {return switchTarget;}


    // setter
    public void setPriority(int priority) {this.priority = priority;}
    public void setMove(Move move) {throw new RuntimeException("Attempted to set active move in a switch action");}
    public void setSwitchTarget(int switchTarget) {this.switchTarget = switchTarget;}


    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> finalOutcomes = new ArrayList<>();
        double probability = context.dispatchGate(GateForAllId.CAN_SWITCH, ContextSideId.USER);

        if (probability > 0) {
            List<Branch<BattleContext>> outcomes = wrap(context);
            outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMAIN_SWAPS_OUT, ContextSideId.USER));
            outcomes = applyProbability(flatMap(outcomes, this::executeSwitch), probability);
            finalOutcomes.addAll(outcomes);
        }
        if (probability < 1) {
            finalOutcomes.add(new Branch<>(context, 1-probability));
        }

        return finalOutcomes;
    }


    // the user should be the only one switching here
    public List<Branch<BattleContext>> executeSwitch(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        // I dont think there is an effect that will prevent the user from swapping into a loomian
        // between when the switch action is confirmed and when the switch actually happens

        List<Loomian> party = outcome.user().party();
        ActiveLoomian newActiveLoomian = new ActiveLoomian(party.get(switchTarget));
        outcome.user().setActiveLoomian(newActiveLoomian);

        List<Branch<BattleContext>> outcomes = wrap(outcome);
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMIAN_SWAPS_IN, ContextSideId.USER));

        return outcomes;
    }
}
