package github.snomfish.functionality.effect;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.effects.NoEffect;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

public class ConditionalEffect implements IEffect {
    

    private ICondition condition;
    private IEffect trueEffect;
    private IEffect falseEffect;


    public ConditionalEffect(
        ICondition condition,
        IEffect trueEffect
    ) {
        this.condition = condition;
        this.trueEffect = trueEffect;
        this.falseEffect = new NoEffect();
    }
    public ConditionalEffect(
        ICondition condition,
        IEffect trueEffect,
        IEffect falseEffect
    ) {
        this.condition = condition;
        this.trueEffect = trueEffect;
        this.falseEffect = falseEffect;
    }


    @Override 
    public ConditionalEffect deepCopy() {
        return new ConditionalEffect(
            condition.deepCopy(), 
            trueEffect.deepCopy(),
            falseEffect.deepCopy()
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {

        List<Branch<Boolean>> conditionBranches = condition.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        
        for (Branch<Boolean> branch : conditionBranches) {
            if (branch.value()) {
                outcomes.addAll(applyProbability(trueEffect.execute(context), branch));
            } else {
                outcomes.addAll(applyProbability(falseEffect.execute(context), branch));
            }
        }

        return outcomes;
    }
}
