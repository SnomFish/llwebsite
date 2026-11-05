package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class ListContains<T> implements ICondition {
      

    private Value<List<T>> list;
    private Value<T> value;


    public ListContains(
        Value<List<T>> list,
        Value<T> value
    ) {
        this.list = list;
        this.value = value;
    }


    // both are type Value, so no deep copying is needed here
    @Override
    public ListContains<T> deepCopy() {
        return new ListContains<>(
            list,
            value
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        
        List<T> listValue = list.get(context);
        T valueValue = value.get(context);
        
        return List.of(new Branch<>(
            listValue.contains(valueValue),
            1.0
        ));
    }
}
