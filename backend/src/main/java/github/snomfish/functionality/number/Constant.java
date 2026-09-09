package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Constant implements INumber {

    private Double value;
    

    public Constant(Double value) {
        this.value = value;
    }


    @Override 
    public Constant deepCopy() {
        return new Constant(
            value
        );
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        return List.of(new Branch<>(value, 1.0));
    }
}
