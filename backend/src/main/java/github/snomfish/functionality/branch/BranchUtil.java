package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import github.snomfish.functionality._schematics.Conditions;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Rule;

import static github.snomfish.functionality._schematics.Conditions.checkProbability;

public class BranchUtil {


    private BranchUtil() {}


    public static <A> List<Branch<A>> wrap(A a) {
        return List.of(new Branch<>(a, 1));
    }
    public static <A> List<Branch<A>> wrap(A a, double probability) {
        return List.of(new Branch<>(a, probability));
    }


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
    public static <A> List<Branch<A>> applyProbability(List<Branch<A>> branches, double probability) {
        for (Branch<A> branch : branches) {
            branch.multiplyProbability(probability);
        }
        return branches;
    }

    


    // FUCKING PARSE IN THIS::[FUNCTION_NAME] OTHERWISE THIS WILL NOT WORK
    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        List<ICondition> conditions,
        Function<BattleContext, List<Branch<BattleContext>>> function
    ) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

		double probability = Conditions.resolve(context, conditions);

		if (probability > 0) {
			outcomes.addAll(applyProbability(function.apply(context), probability));
		}
		if (probability < 1) {
			outcomes.add(new Branch<>(context, 1-probability));
		}

        return outcomes;
    }
    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        List<ICondition> conditions,
        Rule rule,
        BiFunction<BattleContext, Rule, List<Branch<BattleContext>>> function
    ) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

		double probability = Conditions.resolve(context, conditions);

		if (probability > 0) {
			outcomes.addAll(applyProbability(function.apply(context, rule), probability));
		}
		if (probability < 1) {
			outcomes.add(new Branch<>(context, 1-probability));
		}

        return outcomes;
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