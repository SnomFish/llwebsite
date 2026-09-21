package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.item.ItemId.*;

public class RemoveItem implements IEffect {
    

    private Value activeLoomianValue;
    private EventId removedItemEventId;


    private RemoveItem(
        Value activeLoomianValue,
        EventId removedItemEventId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.removedItemEventId = removedItemEventId;
    }
    public static RemoveItem fromUser() {
        return new RemoveItem(
            Value.USER, 
            EventId.USER_ITEM_REMOVED_EVENT
        );
    }
    public static RemoveItem fromTarget() {
        return new RemoveItem(
            Value.TARGET,
            EventId.TARGET_ITEM_REMOVED_EVENT
        );
    }


    private RemoveItem(RemoveItem copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
    }
    @Override 
    public RemoveItem deepCopy() {
        return new RemoveItem(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        ActiveLoomian activeLoomian = (ActiveLoomian) activeLoomianValue.get(context);

        if (activeLoomian.canHaveItemRemoved()) {
            activeLoomian.setItemId(NO_ITEM);
            outcomes = context.dispatchEvent(removedItemEventId);
        }

        return outcomes;
    }
}
