package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class MultiplyValue implements IEffect {
    

    private final Value value;
    private final INumber delta;


    public MultiplyValue(
        Value value,
        INumber delta
    ) {
        this.value = value;
        this.delta = delta;
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {

        double currentValue = (double) value.get(context);
        throw new UnsupportedOperationException("not implemented yet");        
    }
}
