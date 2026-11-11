package github.snomfish.functionality.condition;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.BattleContext;

public class Equals<T> implements ICondition {
    

    private Object object1;
    private Object object2;


    public Equals(
        Object object1,
        Object object2
    ) {
        this.object1 = object1;
        this.object2 = object2;
    }


    // THERE IS NO DEEP COPYING HAPPENING HERE, BUGS WILL OCCUR LMAOOOOOOOOOO 
    @Override
    public Equals<T> deepCopy() {
        return new Equals<T>(
            object1,
            object2
        );
    }


    @Override 
    public double execute(BattleContext context) {

        T value1 = Value.resolve(object1, context);
        T value2 = Value.resolve(object2, context);

        return value1 == value2 ? 1 : 0;
    }
}
