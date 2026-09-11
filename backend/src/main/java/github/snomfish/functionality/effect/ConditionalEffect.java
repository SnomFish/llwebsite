package github.snomfish.functionality.effect;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

public class ConditionalEffect implements IEffect {
    

    private ICondition condition;
    private IEffect effect;


    public ConditionalEffect(
        ICondition condition,
        IEffect effect
    ) {
        this.condition = condition;
        this.effect = effect;
    }


    @Override 
    public ConditionalEffect deepCopy() {
        return new ConditionalEffect(
            condition.deepCopy(), 
            effect.deepCopy()
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {

        List<Branch<Boolean>> conditionBranches = condition.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        
        for (Branch<Boolean> branch : conditionBranches) {
            if (branch.value()) {
                outcomes.addAll(applyProbability(effect.execute(context), branch));
            } else {
                outcomes.add(new Branch<>(
                    context,
                    branch.probability()
                ));
            }
        }

        return outcomes;
    }
}
