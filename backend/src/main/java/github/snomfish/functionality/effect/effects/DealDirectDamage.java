package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class DealDirectDamage implements IEffect {
    

    private INumber idamage;


    public DealDirectDamage(INumber idamage) {
        this.idamage = idamage;
    }


    @Override 
    public DealDirectDamage deepCopy() {
        return new DealDirectDamage(
            idamage.deepCopy()
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        List<Branch<Double>> damageBranches = idamage.execute(context);
        for (Branch<Double> damageBranch : damageBranches) {
            int damage = damageBranch.value().intValue();
            int currentHealth = context.target().activeLoomian().currentHealth();
            damageBranch.setValue((double) Math.clamp(damage, 1, currentHealth));
        }
        damageBranches = BranchUtil.condense(damageBranches);

        for (Branch<Double> damageBranch : damageBranches) {
            BattleContext outcome = context.deepCopy();
            int damage = damageBranch.value().intValue();
            double probability = damageBranch.probability();

            outcome.target().activeLoomian().addToCurrentHealth(-damage);
            outcomes.add(new Branch<>(
                outcome,
                probability
            ));
        }

        return outcomes;
    }
}
