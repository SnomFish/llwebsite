package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;

public class Probability implements ICondition {
    

    private final INumber probability;


    public Probability (INumber probability) {
        this.probability = probability;
    }


    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {
        throw new UnsupportedOperationException("not implemented yet lmao");
    }
}
