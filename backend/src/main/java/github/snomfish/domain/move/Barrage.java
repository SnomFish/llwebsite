package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

// for a move who hits multiple times with the same move
public record Barrage(
    IMove move,
    int minHits,
    int maxHits
    
) implements IMove {

    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
