package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class RestAction implements IAction {
	

	private int priority;
    private boolean willSoulBurst;


    public RestAction(
        int priority
    ) {
        this.priority = priority;
    }

    
    protected RestAction(RestAction copy) {
        this.priority = copy.priority;
        this.willSoulBurst = copy.willSoulBurst;
    }
    public RestAction deepCopy() {
        return new RestAction(this);
    }


    // getter
    public ActionId id()            {return ActionId.SWITCH;}
    public int priority()           {return priority;}
    public Move move() 			    {throw new RuntimeException("Attempted to get move from a rest action");}
	public int switchTarget() 	    {throw new RuntimeException("Attempted to get switchTarget from a rest action");}
    public boolean willSoulBurst()  {return willSoulBurst;}


	public void setPriority(int priority)               {this.priority = priority;}
	public void setMove(Move move) 					    {throw new RuntimeException("Attempted to set move from a rest action");}
	public void setSwitchTarget(int switchTarget) 	    {throw new RuntimeException("Attempted to set switchTarget from a rest action");}
    public void setWillSoulBurst(boolean willSoulBurst) {this.willSoulBurst = willSoulBurst;}


    public boolean isSelectable(BattleContext context) {
        return true;
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		int currentEnergy = outcome.user().activeLoomian().currentEnergy();
		int maxEnergy = (int) outcome.user().activeLoomian().stats().energy();
		int restoration = (maxEnergy * 2) / 3;
		
		restoration = Math.clamp(restoration, 0, maxEnergy-currentEnergy);
		outcome.user().activeLoomian().addToCurrentEnergy(restoration);

        MechanicInstance restingInstance = new MechanicInstance(LoomianMechanicId.RESTING, 1);
        outcome.user().activeLoomian().mechanics().set(restingInstance);

		return wrap(outcome);
	}
}
