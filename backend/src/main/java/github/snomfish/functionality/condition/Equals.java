package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Equals implements ICondition {
    

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
    public Equals deepCopy() {
        return new Equals(
            value1,
            value2
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {

        Object value1Value = resolve(value1, context);
        Object value2Value = resolve(value2, context);

        if (value1Value != null && value2Value != null && !value1Value.getClass().equals(value2Value.getClass())) { 
            throw new IllegalStateException( "Cannot compare values of different types: " + value1Value.getClass().getSimpleName() + " and " + value2Value.getClass().getSimpleName() ); 
        }

        return List.of(new Branch<>(
            value1Value == value2Value,
            1.0
        ));
    }


    private Object resolve(Object value, BattleContext context) {
        if (value instanceof Value dynamicValue) {
            return dynamicValue.get(context);
        }
        return value;
    }
}
