package github.snomfish.functionality.action;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.copy.DeepCopyable;

public interface IAction extends DeepCopyable<IAction> {
    
    // this has to be a Move object rather than an IMove I think
    // In the case of a combo/cascade move being used I wont know which move in the sequence is being used, and will allow for abilities
    // like brutal wrath, that triggers on crits, to only trigger on the only move in that sequence that crits
    Move move();
    void setMove(Move move);
}
