package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.domain.move.MoveEffect;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public record SingleHt (
    // eventually will need a before hit/miss for the move ruthless feast
    MoveEffect onHit,
    MoveEffect onMiss,
    MoveEffect after

) implements IMove {


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}