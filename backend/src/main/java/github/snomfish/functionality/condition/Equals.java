package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Equals<T> implements ICondition {
    

    private Object value1;
    private Object value2;


    public Equals(
        Object value1,
        Object value2
    ) {
        this.value1 = value1;
        this.value2 = value2;
    }


    // THERE IS NO DEEP COPYING HAPPENING HERE, BUGS WILL OCCUR LMAOOOOOOOOOO 
    @Override
    public Equals<T> deepCopy() {
        return new Equals<T>(
            value1,
            value2
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {

        T value1Value = Value.resolve(value1, context);
        T value2Value = Value.resolve(value2, context);

        return List.of(new Branch<>(
            value1Value == value2Value,
            1.0
        ));
    }
}
