package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.item.ItemId.*;
import static github.snomfish.functionality.event.EventId.*;

public class RemoveTargetItem implements IEffect {
    

    public RemoveTargetItem() {

    }


    @Override 
    public RemoveTargetItem deepCopy() {
        return new RemoveTargetItem();
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        ActiveLoomian activeLoomian = context.target().activeLoomian();

        if (activeLoomian.canHaveItemRemoved()) {
            context.dispatchEvent(TARGET_ITEM_REMOVED_EVENT);
            activeLoomian.setItemId(NO_ITEM);
        }

        return List.of(new Branch<>(outcome, 1.0));
    }
}
