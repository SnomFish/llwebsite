package github.snomfish.functionality.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import github.snomfish.functionality.branch.Branch;

public class ConditionUtil {
    

    private ConditionUtil() {}


    // this does not work for complicated objects
    // for now .......
    // will probably need another interface for comparable
    // moved this to ConditionsUtil so it will always work, and it will only be used for bools
    public static List<Branch<Boolean>> optimise(List<Branch<Boolean>> branches) {
        double trueProbability = 0.0;
        double falseProbability = 0.0;
        
        for (Branch<Boolean> branch : branches) {
            if (branch.value()) {
                trueProbability += branch.probability();
            } else {
                falseProbability += branch.probability();
            }
        }
        List<Branch<Boolean>> result = new ArrayList<>(2);
        
        if (trueProbability > 0.0) result.add(new Branch<>(true, trueProbability));
        if (falseProbability > 0.0) result.add(new Branch<>(false, falseProbability));

        return result;
    }


    public static List<Branch<Boolean>> applyLogicalOperator(
        List<List<Branch<Boolean>>> branchesList,
        BinaryOperator<Boolean> operator
    ) {

        List<Branch<Boolean>> result = new ArrayList<>();

        // Start with an empty combination
        result.add(new Branch<>(true, 1.0));

        for (List<Branch<Boolean>> branches : branchesList) {
            List<Branch<Boolean>> next = new ArrayList<>();

            for (Branch<Boolean> existing : result) {
                for (Branch<Boolean> branch : branches) {

                    boolean value = operator.apply(
                            existing.value(),
                            branch.value()
                    );

                    double probability = existing.probability() * branch.probability();

                    next.add(new Branch<>(value, probability));
                }
            }

            result = next;
        }

        return result;
    }
}
