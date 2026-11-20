package github.snomfish.functionality.action;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Loomian;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.variable.GateCondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class SwitchAction implements IAction {
    

    private int switchTarget;


    public SwitchAction(
        int switchTarget
    ) {
        this.switchTarget = switchTarget;
    }

    
    protected SwitchAction(SwitchAction copy) {
        this.switchTarget = copy.switchTarget;
    }
    public SwitchAction deepCopy() {
        return new SwitchAction(this);
    }


    // getter
    public ActionId id() 			{return ActionId.SWITCH;}
    public int priority() 			{return 10;}
    public Move move() 				{throw new RuntimeException("Attempted to get active move from a switch action");}
    public int switchTarget() 		{return switchTarget;}
	public boolean willSoulBurst() 	{return false;}


    // setter
    public void setPriority(int priority) 				{throw new RuntimeException("Attempted to set priority on a switch action");}
    public void setMove(Move move)                      {throw new RuntimeException("Attempted to set active move in a switch action");}
    public void setSwitchTarget(int switchTarget)       {this.switchTarget = switchTarget;}
	public void setWillSoulBurst(boolean willSoulBurst) {throw new RuntimeException("Attempted to set willSoulBurst from a no_action");}


    public static boolean isSelectable(BattleContext context, int targetInt) {
        double probability = new GateCondition(GateForAllId.CAN_SWITCH, ContextSideId.USER).execute(context);

        if (probability != 0 || probability != 1) throw new RuntimeException("Poor code, switch action requires that the can switch action returns true or false, but the gate system is used instead");
        
        return probability == 1;
    }


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
