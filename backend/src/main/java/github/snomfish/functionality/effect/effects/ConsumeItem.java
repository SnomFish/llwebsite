package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.eventIds.RuleForAllId;

import static github.snomfish.domain.item.ItemId.NO_ITEM;

public class ConsumeItem implements IEffect {
        

    private Value<ActiveLoomian> activeLoomianValue;
    private ContextSideId sideId;


    private ConsumeItem(
        Value<ActiveLoomian> activeLoomianValue,
        ContextSideId sideId
    ) {
        this.activeLoomianValue = activeLoomianValue;
        this.sideId = sideId;
    }
    public static ConsumeItem user() {
        return new ConsumeItem(
            Value.USER().LOOMIAN(), 
            ContextSideId.USER
        );
    }
    public static ConsumeItem target() {
        return new ConsumeItem(
            Value.TARGET().LOOMIAN(),
            ContextSideId.TARGET
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

        // currently does not check if the item can be consumed
        activeLoomian.setItemId(NO_ITEM);
        outcomes = context.dispatchEvent(RuleForAllId.LOOMIAN_CONSUMED_ITEM, sideId);

        return outcomes;
    }
}
