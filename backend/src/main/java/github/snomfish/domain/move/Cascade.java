package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

// for a move whos secondary effects change with the number of times its been hit
public record Cascade(
    List<IMove> moves

) implements IMove {

    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
