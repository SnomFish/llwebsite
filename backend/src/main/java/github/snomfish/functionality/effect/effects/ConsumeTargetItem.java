package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.item.ItemId.*;

public class ConsumeTargetItem implements IEffect {
    

    public ConsumeTargetItem() {

    }


    @Override 
    public ConsumeTargetItem deepCopy() {
        return new ConsumeTargetItem();
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        ActiveLoomian activeLoomian = context.target().activeLoomian();

        if (true) {
            context.dispatchEvent(EventId.TARGET_ITEM_CONSUMED);
            activeLoomian.setItemId(NO_ITEM);
        }

        return List.of(new Branch<>(outcome, 1.0));
    }
}