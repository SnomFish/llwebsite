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

        double probability = condition.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        
        if (probability != 0) {
            outcomes.addAll(applyProbability(trueEffect.execute(context), probability));
        } else {
            outcomes.addAll(applyProbability(falseEffect.execute(context), probability));
        }

        return outcomes;
    }
}
