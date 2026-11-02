package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;

public abstract class Action {
    

    private int priority;


    public Action(
        int priority
    ) {
        this.priority = priority;
    }


    protected Action(Action copy) {
        this.priority = copy.priority;
    }


    // getter
    public int priority() {return priority;}
    public abstract Move move();


    // setter
    public void setPriority(int priority) {this.priority = priority;}
    public abstract void setMove(Move move);
}
