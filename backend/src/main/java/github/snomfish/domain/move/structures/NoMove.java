package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class NoMove implements IMove {
    

    public NoMove() {}


    @Override 
    public NoMove deepCopy() {
        return new NoMove();
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return List.of(new Branch<>(context, 1.0));
    }
}
