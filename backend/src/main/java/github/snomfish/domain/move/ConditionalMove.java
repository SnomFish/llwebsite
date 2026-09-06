package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;

public record ConditionalMove(
    IMove move,
    ICondition condition

) implements IMove {

    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}