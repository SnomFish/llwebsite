package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.item.ItemId.*;

public class RemoveUserItem implements IEffect {
    

    public RemoveUserItem() {

    }


    @Override 
    public RemoveUserItem deepCopy() {
        return new RemoveUserItem();
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        ActiveLoomian activeLoomian = context.user().activeLoomian();

        if (activeLoomian.canHaveItemRemoved()) {
            context.dispatchEvent(EventId.USER_ITEM_REMOVED);
            activeLoomian.setItemId(NO_ITEM);
        }

        return List.of(new Branch<>(outcome, 1.0));
    }
}