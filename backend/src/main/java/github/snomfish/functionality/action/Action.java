package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.move.MoveRegistry;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class Action {
	

	private IAction mainAction;
	private boolean willSoulburst;


	private Action(
		IAction mainAction,
		boolean willSoulburst
	) {
		this.mainAction = mainAction;
		this.willSoulburst = willSoulburst;
	}


	public static Action selectNoAction() {
		return new Action(new NoAction(), false);
	}
	public static Action selectSwitch(BattleContext context, int switchTarget) {
		// can select switch
		return new Action(new SwitchAction(switchTarget), false);
	}
	public static Action selectWait(BattleContext context) {
		// can select wait()
		return new Action(new WaitAction(), false);
	}
	public static Action selectRest(BattleContext context) {
		// can select rest()
		return new Action(new RestAction(), false);
	}
	public static Action selectMove(BattleContext context, int moveSlotI) {
		// can select move()
		MoveId moveId = context.user().activeLoomian().moves().get(moveSlotI);
		Move move = MoveRegistry.get(moveId);
		return new Action(new MoveAction(move), false);
	}

	public static Action selectSoulburstWait(BattleContext context) {
		// can select soulburst()
		// can select wait()
		return new Action(new WaitAction(), true);
	}
	public static Action selectSoulburstRest(BattleContext context) {
		// can select soulburst()
		// can select rest()
		return new Action(new RestAction(), true);
	}
	public static Action selectSoulburstMove(BattleContext context, int moveSlotI) {
		// can select soulburst()
		// can select move()
		MoveId moveId = context.user().activeLoomian().moves().get(moveSlotI);
		Move move = MoveRegistry.get(moveId);
		return new Action(new MoveAction(move), true);
	}
	public static Action selectSoulburstSoulMove(BattleContext context) {
		// can select soulburst()
		MoveId moveId = context.user().activeLoomian().species().soulMoveId();
		Move move = MoveRegistry.get(moveId);
		return new Action(new MoveAction(move), true);
	}



	private Action(Action copy) {
		this.mainAction = copy.mainAction.deepCopy();
		this.willSoulburst = copy.willSoulburst;
	}
	public Action deepCopy() {
		return new Action(this);
	}


	// getter
	public IAction mainAction() {return mainAction;}
	public boolean willSoulburst() {return willSoulburst;}


	// setter
	public void setMainAction(IAction mainAction) {this.mainAction = mainAction;}
	public void setWillSoulBurst(boolean willSoulburst) {this.willSoulburst = willSoulburst;}


	public void calcPriority(BattleContext context) {
		this.mainAction.calcPriority(context);
	}


	public List<Branch<BattleContext>> executeActionsWithPriority(int priority, BattleContext context) {
		// THEORETICALLY THE SWITCH, WAIT, REST AND SOULBURST ACITONS SHOULD OCCUR AT A CONSTANT PRIORITY
		if (priority == mainAction.priority()) {
			BattleContext outcome = context.deepCopy();
			outcome.user().activeLoomian().setActedThisTurn(true);
			return mainAction.execute(outcome);
		}
		if (priority == 9 && willSoulburst) {
			return executeSoulburst(context);
		}
		return null;
	}


	private List<Branch<BattleContext>> executeSoulburst(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		outcome.user().soulburst();
		
		List<Branch<BattleContext>> outcomes = wrap(outcome);
		outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMIAN_SWAPS_IN, ContextSideId.USER));

		return outcomes;
	}
}
