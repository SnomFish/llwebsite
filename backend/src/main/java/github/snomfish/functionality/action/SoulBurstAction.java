package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class SoulBurstAction implements IAction {
	

	public SoulBurstAction() {}


	private SoulBurstAction(SoulBurstAction copy) {}
	public SoulBurstAction deepCopy() {
		return new SoulBurstAction(this);
	}


	public ActionId id() 		{return ActionId.SOULBURST;}
	public int priority() 		{return 9;}
	public Move move() 			{throw new RuntimeException("Attempted to get move from a soulburst action");}
	public int switchTarget() 	{throw new RuntimeException("Attempted to get switchTarget from a soulburst action");}


	public void setPriority(int priority) 				{throw new RuntimeException("Attempted to set priority from a soulburst action");}
	public void setMove(Move move) 						{throw new RuntimeException("Attempted to set move from a soulburst action");}
	public void setSwitchTarget(int switchTarget) 		{throw new RuntimeException("Attempted to set switchTarget from a soulburst action");}


	public boolean isSelectable(BattleContext context) {
        return true;
    }


	public List<Branch<BattleContext>> execute(BattleContext context) {
		return wrap(context);
	}
}
