package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public class MoveAction extends Action {
    

    private Move move;


    public MoveAction(
        int priority,
        Move move
    ) {
        super(priority);
        this.move = move;
    }


    private MoveAction(MoveAction copy) {
        super(copy);
        this.move = copy.move.deepCopy();
    }
    @Override
    public MoveAction deepCopy() {
        return new MoveAction(this);
    } 


    // getter
    @Override 
    public Move move() {
        return move;
    }


    // setter
    @Override 
    public void setMove(Move move) {
        this.move = move;
    }
}
