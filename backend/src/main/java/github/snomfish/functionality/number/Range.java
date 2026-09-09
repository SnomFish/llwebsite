package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Range implements INumber {
    

    private List<Double> values;

    
    public Range(List<Double> values) {
        this.values = values;
    }


    @Override 
    public Range deepCopy() {
        return new Range(
            new ArrayList<>(values)
        );
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        
        List<Branch<Double>> outcomes = new ArrayList<>();

        double probability = 1 / values.size();
        for (Double value : values) {
            outcomes.add(new Branch<>(
                value,
                probability
            ));
        }

        return outcomes;
    }
}
