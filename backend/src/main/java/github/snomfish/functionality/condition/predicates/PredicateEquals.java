package github.snomfish.functionality.condition.predicates;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.BattleContext;

public class PredicateEquals<T> implements IPredicate {
	

    private Object object1;
    private Object object2;


    public PredicateEquals(
        Object object1,
        Object object2
    ) {
        this.object1 = object1;
        this.object2 = object2;
    }


    // THERE IS NO DEEP COPYING HAPPENING HERE, BUGS WILL OCCUR IF NON RAW/NON VALUE TYPES ARE PUT HERE
    // LMAOOO
    public PredicateEquals<T> deepCopy() {
        return new PredicateEquals<T>(
            object1,
            object2
        );
    }


    public boolean execute(BattleContext context) {

        T value1 = Value.resolve(object1, context);
        T value2 = Value.resolve(object2, context);

        // does not need to be checked
        return value1 == value2;
    }
}
