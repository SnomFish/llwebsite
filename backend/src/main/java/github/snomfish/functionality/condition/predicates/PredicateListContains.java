package github.snomfish.functionality.condition.predicates;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.BattleContext;

public class PredicateListContains<T> implements IPredicate {
	

    private Value<List<T>> list;
    private Object value;


    public PredicateListContains(
        Value<List<T>> list,
        T value
    ) {
        this.list = list;
        this.value = value;
    }
    public PredicateListContains(
        Value<List<T>> list,
        Value<T> value
    ) {
        this.list = list;
        this.value = value;
    }


    private PredicateListContains(PredicateListContains<T> copy) {
        this.list = copy.list;
        this.value = copy.value;
    }
    public PredicateListContains<T> deepCopy() {
        return new PredicateListContains<>(this);
    }


    public boolean execute(BattleContext context) {
        // does not need to be checked
        return list.get(context).contains(Value.resolve(value, context));
    }
}
