package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class MultiplyValueEffect implements IEffect {
    

    private Value value;
    private INumber inumber;


    public MultiplyValueEffect(
        Value value,
        INumber inumber
    ) {
        this.value = value;
        this.inumber = inumber;
    }


    @Override 
    public MultiplyValueEffect deepCopy() {
        return new MultiplyValueEffect(
            value,
            inumber.deepCopy()
        );
    }


    // dont need to worry about deep copying as effects can mutate the battlecontext, 
    // (deepCopying only happens when something with a chance of occuring actually happens)
    // update NEVERMIND INUMBER IS HERE
    // INUMBER on execute always returns a List, so you are going to have to deepcopy
    // I think I did it, good job me
    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {

        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        List<Branch<Double>> deltaBranches = inumber.execute(context);

        double currentValue = (double) value.get(context);
        
        for (Branch<Double> deltaBranch : deltaBranches) {
            BattleContext copy = context.deepCopy();

            value.set(copy, deltaBranch.value() * currentValue);

            outcomes.add(new Branch<>(
                copy,
                deltaBranch.probability()
            ));
        }

        return outcomes;
    }
}
