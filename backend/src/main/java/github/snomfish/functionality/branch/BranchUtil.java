package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import github.snomfish.functionality.context.BattleContext;

public class BranchUtil {


    private BranchUtil() {}


    // takes in a list of Branches and a function that creates more branches and applies that function to each original branch, 
    // then packs all the results into one list
    public static <A, B> List<Branch<B>> flatMap(
        List<Branch<A>> branches,
        Function<A, List<Branch<B>>> function
    ) {
        List<Branch<B>> outcomes = new ArrayList<>();
        for (Branch<A> branch : branches) {

            List<Branch<B>> newBranches = function.apply(branch.value());   
            for (Branch<B> newBranch : newBranches) {
                outcomes.add(new Branch<B>(
                    newBranch.value(), 
                    branch.probability() * newBranch.probability()
                ));
            }
        }
        return outcomes;
    }


    public static List<Branch<Boolean>> flatMap(
        BattleContext context,
        List<Branch<Boolean>> oldBranches,
        Function<BattleContext, List<Branch<Boolean>>> function
    ) {
        List<Branch<Boolean>> outcomes = new ArrayList<>();
        for (Branch<Boolean> oldBranch : oldBranches) {

            List<Branch<Boolean>> newBranches = function.apply(context);
            for (Branch<Boolean> newBranch : newBranches) {
                outcomes.add(new Branch<>(
                    oldBranch.value() && newBranch.value(),
                    oldBranch.probability() * newBranch.probability()
                ));
            }
        }
        return outcomes;
    }


    // much better, mutation but it is safe
    // I wish I used this more, it is worryingly how little I do
    public static <A, B> List<Branch<A>> applyProbability(List<Branch<A>> branches, Branch<B> pBranch) {
        for (Branch<A> branch : branches) {
            branch.multiplyProbability(pBranch.probability());
        }
        return branches;
    }




    // OPTIMIZATIONS
    public static List<Branch<Double>> condense(List<Branch<Double>> branches) {
        Map<Double, Double> map = new HashMap<>();
        for (Branch<Double> branch : branches) {
            Double key = branch.value();
            Double val = map.get(key);
            val += branch.probability();
            map.put(key, val);
        }

        List<Branch<Double>> outcomes = new ArrayList<>();
        for (Double value : map.keySet()) {
            Double probability = map.get(value);
            outcomes.add(new Branch<>(
                value,
                probability
            ));
        }

        return outcomes;
    }
}