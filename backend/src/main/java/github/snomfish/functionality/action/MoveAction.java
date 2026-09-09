package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public class MoveAction implements IAction {
    
    private Move move;


    public MoveAction(Move move) {
        this.move = move;
    }


    @Override
    public MoveAction deepCopy() {
        return new MoveAction(
            move.deepCopy()
        );
    } 


    @Override 
    public Move move() {
        return move;
    }
    @Override 
    public void setMove(Move move) {
        this.move = move;
    }
}
