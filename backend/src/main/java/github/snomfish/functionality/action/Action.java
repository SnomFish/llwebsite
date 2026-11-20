package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Action {
	

	private IAction action;
	private boolean willSoulBurst;


	private Action(
		IAction action,
		boolean willSoulBurst
	) {
		this.action = new NoAction();
		this.willSoulBurst = false;
	}


	public static Action selectSwitch(int switchTarget) {
		return new Action(new SwitchAction(switchTarget), false);
	}
	public static boolean canSelectSwitch(BattleContext context, int switchTarget) {
		return SwitchAction.isSelectable(context, switchTarget);
	} 


	private Action(Action copy) {
		this.action = copy.action.deepCopy();
		this.willSoulBurst = copy.willSoulBurst;
	}
	public Action deepCopy() {
		return new Action(this);
	}


	public IAction action() {return action;}
	public boolean willSoulBurst() {return willSoulBurst;}


	public List<Branch<BattleContext>> executeSoulBurst(BattleContext context) {
		return null;
	}
	public List<Branch<BattleContext>> execute(BattleContext context) {
		return action.execute(context);
	}
}
