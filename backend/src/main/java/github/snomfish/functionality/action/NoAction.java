package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class NoAction implements IAction {


	public NoAction() {}


	private NoAction(NoAction copy) {}
	public NoAction deepCopy() {
		return new NoAction(this);
	}


	public ActionId id() 		{return ActionId.NO_ACTION;}
	public int priority() 		{return 0;}
	public Move move() 			{throw new RuntimeException("Attempted to get move from a no_action");}
	public int switchTarget() 	{throw new RuntimeException("Attempted to get switchTarget from a no_action");}


	public void setPriority(int priority) 			{throw new RuntimeException("Attempted to set priority from a no_action");}
	public void setMove(Move move) 					{throw new RuntimeException("Attempted to set move from a no_action");}
	public void setSwitchTarget(int switchTarget) 	{throw new RuntimeException("Attempted to set switchTarget from a no_action");}


	public boolean isSelectable(BattleContext context) {
        return true;
    }


	public List<Branch<BattleContext>> execute(BattleContext context) {
		return wrap(context);
	}
}
