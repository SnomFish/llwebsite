package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class WaitAction implements IAction {
	

	private int priority;


    public WaitAction(
        int priority
    ) {
        this.priority = priority;
    }

    
    protected WaitAction(WaitAction copy) {
        this.priority = copy.priority;
    }
    public WaitAction deepCopy() {
        return new WaitAction(this);
    }


    // getter
    public ActionId id() {return ActionId.SWITCH;}
    public int priority() {return priority;}
    public Move move() 			{throw new RuntimeException("Attempted to get move from a wait action");}
	public int switchTarget() 	{throw new RuntimeException("Attempted to get switchTarget from a wait action");}


	public void setPriority(int priority) {this.priority = priority;}
	public void setMove(Move move) 					{throw new RuntimeException("Attempted to set move from a wait action");}
	public void setSwitchTarget(int switchTarget) 	{throw new RuntimeException("Attempted to set switchTarget from a wait action");}


    public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		int currentEnergy = outcome.user().activeLoomian().currentEnergy();
		int maxEnergy = (int) outcome.user().activeLoomian().stats().energy();
		int restoration = maxEnergy / 3;
		
		restoration = Math.clamp(restoration, 0, maxEnergy-currentEnergy);
		outcome.user().activeLoomian().addToCurrentEnergy(restoration);

		return wrap(outcome);
	}
}
