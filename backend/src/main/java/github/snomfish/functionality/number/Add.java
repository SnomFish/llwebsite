package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;

public class Add implements INumber {
    

    private List<INumber> numbers;


    public Add(List<INumber> numbers) {
        this.numbers = numbers;
    }


    @Override
    public List<Branch<Double>> execute(BattleContext context) {
        List<Branch<Double>> outcomes = List.of(
            new Branch<>(0.0, 1.0)
        );

        for (INumber number : numbers) {
            List<Branch<Double>> numberOutcomes = number.execute(context);

            List<Branch<Double>> newOutcomes = new java.util.ArrayList<>();

            for (Branch<Double> existing : outcomes) {
                for (Branch<Double> next : numberOutcomes) {

                    double value = existing.getValue() + next.getValue();
                    double probability =
                        existing.getProbability() * next.getProbability();

                    newOutcomes.add(new Branch<>(value, probability));
                }
            }

            outcomes = newOutcomes;
        }

        return BranchUtil.compact(outcomes);
    }
}
