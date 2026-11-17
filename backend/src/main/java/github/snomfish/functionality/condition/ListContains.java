package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.BattleContext;

public class ListContains<T> implements ICondition {
      

    private Value<List<T>> list;
    private Object value;


    public ListContains(
        Value<List<T>> list,
        T value
    ) {
        this.list = list;
        this.value = value;
    }
    public ListContains(
        Value<List<T>> list,
        Value<T> value
    ) {
        this.list = list;
        this.value = value;
    }


    private ListContains(ListContains<T> copy) {
        this.list = copy.list;
        this.value = copy.value;
    }
    public ListContains<T> deepCopy() {
        return new ListContains<>(this);
    }


    @Override 
    public double execute(BattleContext context) {
        // does not need to be checked
        return list.get(context).contains(Value.resolve(value, context)) ? 1 : 0;
    }
}
