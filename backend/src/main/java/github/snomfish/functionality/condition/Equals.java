package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Equals<T> implements ICondition {
    

    private T value1;
    private T value2;


    public Equals(
        T value1,
        T value2
    ) {
        this.value1 = value1;
        this.value2 = value2;
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        return List.of(new Branch<>(
            value1 == value2,
            1.0
        ));
    }
}
