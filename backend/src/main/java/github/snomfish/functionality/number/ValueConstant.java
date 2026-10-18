package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class ValueConstant implements INumber {

    
    private Value value;
    

    public ValueConstant(Value value) {
        this.value = value;
    }


    @Override 
    public ValueConstant deepCopy() {
        return new ValueConstant(
            value
        );
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        Object object = value.get(context);
        Double val;
        if (object instanceof Number) {
            val = (double) object;
        } else {
            throw new RuntimeException("Value \"" + value.name() + "\"; returns a non number into ValueConstant");
        }

        return List.of(new Branch<>(val, 1.0));
    }
}