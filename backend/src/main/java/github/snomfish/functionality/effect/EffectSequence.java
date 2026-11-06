package github.snomfish.functionality.effect;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;;

public class EffectSequence implements IEffect {
    

    private List<IEffect> effects = new ArrayList<>();


    public EffectSequence(List<IEffect> effects) {
        this.effects = effects;
    }
    public EffectSequence(IEffect a, IEffect b) {
        this.effects = List.of(a, b);
    }
    public EffectSequence(IEffect a, IEffect b, IEffect c) {
        this.effects = List.of(a, b, c);
    }
    public EffectSequence(IEffect a, IEffect b, IEffect c, IEffect d) {
        this.effects = List.of(a, b, c, d);
    }


    @Override 
    public EffectSequence deepCopy() {
        return new EffectSequence(
            deepCopyList(effects)
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        for (IEffect effect : effects) {
            outcomes = flatMap(outcomes, c -> effect.execute(c));
        }

        return outcomes;
    }
}
