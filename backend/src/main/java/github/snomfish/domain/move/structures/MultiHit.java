package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.variable.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.resolveConditions;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

// for a move who hits multiple times with the same move
public class MultiHit extends IMove {


    private List<ICondition> conditions;
    private MoveEffect perHit;
    private INumber range;


    private MultiHit() {
        this.conditions = new ArrayList<>();
        this.perHit = new MoveEffect();
        this.range = new Constant(0);
    }
    

    public static MultiHit build() {
        return new MultiHit();
    }
    public MultiHit condition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }
    public MultiHit primary(IEffect primary) {
        this.perHit.addPrimary(primary);
        return this;
    }
    public MultiHit secondary(IEffect secondary) {
        this.perHit.addSecondary(secondary);
        return this;
    }
    public MultiHit range(INumber range) {
        this.range = range;
        return this;
    }


    private MultiHit(MultiHit copy) {
        this.perHit = copy.perHit.deepCopy();
        this.range = copy.range.deepCopy();
    }
    public MultiHit deepCopy() {
        return new MultiHit(this);
    }

    
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return resolveConditions(context, conditions, this::executeMove);
    }


    public List<Branch<BattleContext>> executeMove(BattleContext context) {
        List<Branch<BattleContext>> finalOutcomes = new ArrayList<>();
        List<Branch<Double>> hitCountBranches = range.execute(context);

        for (Branch<Double> hitCountBranch : hitCountBranches) {
            int hitCount = hitCountBranch.value().intValue();
            double hitCountProbability = hitCountBranch.probability();
            List<Branch<BattleContext>> outcomes = wrap(context);

            hitCount = Math.max(hitCount, 0); // prevents the hit count being 0
            for (int i = 0; i < hitCount; i ++) {
                outcomes = flatMap(outcomes, c -> perHit.execute(c));
            }
            applyProbability(outcomes, hitCountProbability);
            finalOutcomes.addAll(outcomes);
        }

        return finalOutcomes;
    }
}
