package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;

public class And implements ICondition {
    

    private final List<ICondition> conditions;


    public And(List<ICondition> conditions) {
        this.conditions = conditions;
    }


    @Override
    public List<Branch<Boolean>> execute(BattleContext context) {
        List<Branch<Boolean>> outcomes = List.of(
            new Branch<>(true, 1.0)
        );

        for (ICondition condition : conditions) {
            List<Branch<Boolean>> conditionOutcomes = condition.execute(context);

            List<Branch<Boolean>> newOutcomes = new java.util.ArrayList<>();

            for (Branch<Boolean> existing : outcomes) {
                for (Branch<Boolean> next : conditionOutcomes) {

                    boolean value = existing.value() && next.value();
                    double probability =
                        existing.probability() * next.probability();

                    newOutcomes.add(new Branch<>(value, probability));
                }
            }

            outcomes = newOutcomes;
        }

        return outcomes;
    }
}
