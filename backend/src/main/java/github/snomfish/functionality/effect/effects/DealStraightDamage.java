package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class DealStraightDamage implements IEffect {
    

    private INumber idamage;


    public DealStraightDamage(INumber idamage) {
        this.idamage = idamage;
    }


    @Override 
    public DealStraightDamage deepCopy() {
        return new DealStraightDamage(
            idamage.deepCopy()
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<Double>> damageBranches = idamage.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Double> damageBranch : damageBranches) {
            BattleContext outcome = context.deepCopy();

            double damage = Math.floor(damageBranch.value()); // quick n dirty solution to all my problems, could cause a bug
            // probably needs some events here

            double newTargetCurrentHealth = outcome.target().activeLoomian().currentHealth() - damage;
            outcome.user().activeLoomian().action().move().setDamage(damageBranch.value().intValue());
        }

        throw new RuntimeException("not fully implemented");
    }
}
