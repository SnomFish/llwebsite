package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class MoveAction implements IAction {
    

    private int priority;
    private Move move;
    private boolean willSoulBurst;


    public MoveAction(
        int priority,
        Move move
    ) {
        this.priority = priority;
        this.move = move;
    }


    private MoveAction(MoveAction copy) {
        this.priority = copy.priority;
        this.move = copy.move.deepCopy();
        this.willSoulBurst = copy.willSoulBurst;
    }
    public MoveAction deepCopy() {
        return new MoveAction(this);
    } 


    // getter
    public ActionId id() {return ActionId.MOVE;}
    public int priority() {return priority;} 
    public Move move() {return move;}
    public int switchTarget() {throw new RuntimeException("Attempted to get switch target from a move action");}
    public boolean willSoulBurst() {return willSoulBurst;}


    // setter
    public void setPriority(int priority) {this.priority = priority;}
    public void setMove(Move move) {this.move = move;}
    public void setSwitchTarget(int switchTarget) {throw new RuntimeException("Attempted to get switch target from a move action");}
    public void setWillSoulBurst(boolean willSoulBurst) {this.willSoulBurst = willSoulBurst;}


    public boolean isSelectable(BattleContext context) {
        return true;
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return move.execute(context);
    }
}
