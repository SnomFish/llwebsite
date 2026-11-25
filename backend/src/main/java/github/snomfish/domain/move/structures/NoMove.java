package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class NoMove extends IMove {
    

    public NoMove() {}


    @Override 
    public NoMove deepCopy() {
        return new NoMove();
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return wrap(context);
    }
}
