package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public class SwitchAction implements IAction {
    

    public SwitchAction() {}

    
    @Override 
    public SwitchAction deepCopy() {
        return new SwitchAction();
    }


    @Override 
    public Move move() {
        throw new RuntimeException("Attempted to get active move from a switch action");
    }
    @Override 
    public void setMove(Move move) {
        throw new RuntimeException("Attempted to set active move in a switch action");
    }
}
