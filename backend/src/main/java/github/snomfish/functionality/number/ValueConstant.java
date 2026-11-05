package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class ValueConstant implements INumber {

    
    private Value<? extends Number> value;
    

    public ValueConstant(Value<? extends Number> value) {
        this.value = value;
    }


    private ValueConstant(ValueConstant copy) {
        this.value = copy.value;
    }
    public ValueConstant deepCopy() {
        return new ValueConstant(this);
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        return List.of(new Branch<>(value.get(context).doubleValue(), 1.0));
    }
}