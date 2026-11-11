package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ids.GenericId;

import static github.snomfish.domain.item.ItemId.*;

public class RemoveItem implements IEffect {
    

    private Value<ActiveLoomian> activeLoomianValue;
    private ContextSideId sideId;


    private RemoveItem(
        Value<ActiveLoomian> activeLoomianValue,
        ContextSideId sideId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.sideId = sideId;
    }
    public static RemoveItem fromUser() {
        return new RemoveItem(
            Value.USER().LOOMIAN(), 
            ContextSideId.USER
        );
    }
    public static RemoveItem fromTarget() {
        return new RemoveItem(
            Value.TARGET().LOOMIAN(),
            ContextSideId.TARGET
        );
    }


    private RemoveItem(RemoveItem copy) {
        this.activeLoomianValue = copy.activeLoomianValue;
        this.sideId = copy.sideId;
    }
    @Override 
    public RemoveItem deepCopy() {
        return new RemoveItem(this);
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        ActiveLoomian activeLoomian = (ActiveLoomian) activeLoomianValue.get(context);

        // does not check if the loomian can have it's item removed
        activeLoomian.setItemId(NO_ITEM);
        outcomes = context.dispatchEvent(GenericId.SELF_LOSES_ITEM, sideId);

        return outcomes;
    }
}
