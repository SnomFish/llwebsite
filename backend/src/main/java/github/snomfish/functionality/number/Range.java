package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Range implements INumber {
    

    private List<INumber> numbers;

    
    private Range() {
        this.numbers = new ArrayList<>();
    }


    public static Range build() {
        return new Range();
    }
    public Range add(INumber number) {
        this.numbers.add(number);
        return this;
    }


    private Range(Range copy) {
        this.numbers = deepCopyList(numbers);
    }
    public Range deepCopy() {
        return new Range(this);
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        
        List<Branch<Double>> outcomes = new ArrayList<>();

        double probability = 1 / numbers.size();
        for (INumber number : numbers) {
            outcomes.addAll(applyProbability(number.execute(context), probability));
        }

        return outcomes;
    }
}
