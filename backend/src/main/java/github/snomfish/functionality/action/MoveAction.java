package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public class MoveAction implements IAction {
    
    private Move move;


    @Override 
    public Move getMove() {
        return move;
    }
    @Override 
    public void setMove(Move move) {
        this.move = move;
    }
}
