package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.item.ItemId.NO_ITEM;

public class ConsumeItem implements IEffect {
        

    private Value activeLoomianValue;
    private EventId removedItemEventId;


    private ConsumeItem(
        Value activeLoomianValue,
        EventId removedItemEventId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.removedItemEventId = removedItemEventId;
    }
    public static ConsumeItem user() {
        return new ConsumeItem(
            Value.USER, 
            EventId.USER_ITEM_CONSUMED_EVENT
        );
    }
    public static ConsumeItem target() {
        return new ConsumeItem(
            Value.TARGET,
            EventId.TARGET_ITEM_CONSUMED_EVENT
        );
    }


    private ConsumeItem(ConsumeItem copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
    }
    @Override 
    public ConsumeItem deepCopy() {
        return new ConsumeItem(this);
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
