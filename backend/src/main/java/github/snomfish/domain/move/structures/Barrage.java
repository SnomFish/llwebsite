package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.domain.move.MoveEffect;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

// for a move who hits multiple times with the same move
public record Barrage (
    MoveEffect moveEffect,
    int minHits,
    int maxHits
    
) implements IMove {

    
    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
