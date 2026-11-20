package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import github.snomfish.functionality._schematics.Conditions;
import github.snomfish.functionality.condition.variable.GateCondition;
import github.snomfish.functionality.condition.variable.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.GateForAllId;

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
    public static List<Branch<BattleContext>> flatMap(
        List<Branch<BattleContext>> branches,
        IEffect effect
    ) {
        return flatMap(branches, c -> effect.execute(c));
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
        Supplier<List<Branch<BattleContext>>> trueFunction,
        Supplier<List<Branch<BattleContext>>> falseFunction
    ) {
        double probability = Conditions.resolve(context, conditions);

        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        if (probability > 0) {
            outcomes.addAll(applyProbability(trueFunction.get(), probability));
        }
        if (probability < 1) {
            outcomes.addAll(applyProbability(falseFunction.get(), 1 - probability));
        }

        return outcomes;
    }
    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        List<ICondition> conditions,
        Function<BattleContext, List<Branch<BattleContext>>> function
    ) {
        return resolveConditions(context, conditions,
            () -> function.apply(context),
            () -> wrap(context)
        );
    }
    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        List<ICondition> conditions,
        Function<BattleContext, List<Branch<BattleContext>>> trueFunction,
        Function<BattleContext, List<Branch<BattleContext>>> falseFunction
    ) {
        return resolveConditions(context, conditions,
            () -> trueFunction.apply(context),
            () -> falseFunction.apply(context)
        );
    }

    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        List<ICondition> conditions,
        Rule rule,
        BiFunction<BattleContext, Rule, List<Branch<BattleContext>>> function
    ) {
        return resolveConditions(context, conditions,
            () -> function.apply(context, rule),
            () -> wrap(context)
        );
    }

    public static List<Branch<BattleContext>> resolveConditions(
        BattleContext context,
        GateForAllId gateForAllId,
        ContextSideId sideId,
        Function<BattleContext, List<Branch<BattleContext>>> function
    ) {
        return resolveConditions(context, List.of(new GateCondition(gateForAllId, sideId)),
			() -> function.apply(context),
            () -> wrap(context)
		);
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