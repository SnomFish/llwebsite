package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetValueEffect implements IEffect {
    

    private Value value;
    private Object newValue;


    public SetValueEffect(
        Value value,
        Object newValue
    ) {
        this.value = value;
        this.newValue = newValue;
    }


    // OMG THIS ONE CANNOT BE DEEP COPIED TOO LETS GOO LETS HOPE I ONLY USE SHALLOW VALUES
    // CHECK EQUALS.JAVA (CONDITION) TO SEE MORE OF THISSSS
    @Override 
    public SetValueEffect deepCopy() {
        return new SetValueEffect(
            value,
            newValue
        );
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
