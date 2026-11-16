package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

// for a move who hits multiple times with the same move
public class Barrage implements IMove {


    private MoveEffect perHit;
    private INumber range;


    private Barrage() {
        this.perHit = MoveEffect.build();
        this.range = new Constant(0);
    }
    

    public static Barrage build() {
        return new Barrage();
    }
    public Barrage primary(IEffect primary) {
        this.perHit.primary(primary);
        return this;
    }
    public Barrage secondary(IEffect secondary) {
        this.perHit.secondary(secondary);
        return this;
    }
    public Barrage range(INumber range) {
        this.range = range;
        return this;
    }


    private Barrage(Barrage copy) {
        this.perHit = copy.perHit.deepCopy();
        this.range = copy.range.deepCopy();
    }
    public Barrage deepCopy() {
        return new Barrage(this);
    }

    
    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
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
