package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public record Move(
    TypeId type,
    IEffect effect

) implements IMove {

    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
