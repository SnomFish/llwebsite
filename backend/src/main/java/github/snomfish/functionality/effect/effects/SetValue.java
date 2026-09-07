package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetValue implements IEffect {
    

    private final Value value;
    private final Object newValue;


    public SetValue(
        Value value,
        Object newValue
    ) {
        this.value = value;
        this.newValue = newValue;
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {

        value.set(context, newValue);

        return List.of(
            new Branch<>(
                context,
                1.0
            )
        );
    }
}
