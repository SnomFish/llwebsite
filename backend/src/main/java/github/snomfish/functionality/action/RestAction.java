package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class RestAction implements IAction {


    public RestAction() {}

    
    protected RestAction(RestAction copy) {}
    public RestAction deepCopy() {
        return new RestAction(this);
    }


    // getter
    public ActionId id()            {return ActionId.SWITCH;}
    public int priority()           {return 8;}
    public Move move() 			    {throw new RuntimeException("Attempted to get move from a rest action");}
	public int switchTarget() 	    {throw new RuntimeException("Attempted to get switchTarget from a rest action");}


	public void setPriority(int priority)               {throw new RuntimeException("Attempted to set priority in a rest action");}
	public void setMove(Move move) 					    {throw new RuntimeException("Attempted to set move in a rest action");}
	public void setSwitchTarget(int switchTarget) 	    {throw new RuntimeException("Attempted to set switchTarget in a rest action");}


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
