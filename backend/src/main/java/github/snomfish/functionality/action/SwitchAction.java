package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public class SwitchAction extends Action {
    

    public SwitchAction(
        int priority
    ) {
        super(priority);
    }

    
    protected SwitchAction(SwitchAction copy) {
        super(copy);
    }
    @Override 
    public SwitchAction deepCopy() {
        return new SwitchAction(this);
    }


    // getter
    @Override 
    public Move move() {
        throw new RuntimeException("Attempted to get active move from a switch action");
    }


    // setter
    @Override 
    public void setMove(Move move) {
        throw new RuntimeException("Attempted to set active move in a switch action");
    }
}
