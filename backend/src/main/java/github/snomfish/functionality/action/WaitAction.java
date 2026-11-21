package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class WaitAction implements IAction {
	

    private boolean willSoulBurst;


    public WaitAction() {}

    
    protected WaitAction(WaitAction copy) {
        this.willSoulBurst = copy.willSoulBurst;
    }
    public WaitAction deepCopy() {
        return new WaitAction(this);
    }


    // getter
    public ActionId id()            {return ActionId.SWITCH;}
    public int priority()           {return 8;}
    public Move move() 			    {throw new RuntimeException("Attempted to get move from a wait action");}
	public int switchTarget() 	    {throw new RuntimeException("Attempted to get switchTarget from a wait action");}
    public boolean willSoulBurst()  {return willSoulBurst;}


	public void setPriority(int priority)               {throw new RuntimeException("Attempted to set priority in a wait action");}
	public void setMove(Move move) 					    {throw new RuntimeException("Attempted to set move in a wait action");}
	public void setSwitchTarget(int switchTarget) 	    {throw new RuntimeException("Attempted to set switchTarget in a wait action");}
    public void setWillSoulBurst(boolean willSoulBurst) {this.willSoulBurst = willSoulBurst;}


    public boolean isSelectable(BattleContext context) {
        return true;
    }


    public void calcPriority(BattleContext context) {
        // priority for waiting is set at 8
    }


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
