package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.domain.stats.StatId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.number.INumber;

public class DealFormulaDamage implements IEffect {
    // deals formula damage to the target
    
    private StatId attackingStat;
    private StatId defendingStat;
    private INumber basePower;
    
 
    public DealFormulaDamage(
        StatId attackingStat,
        StatId defendingStat,
        INumber basePower
    ) {
        this.attackingStat = attackingStat;
        this.defendingStat = defendingStat;
        this.basePower = basePower;
    }


    @Override 
    public DealFormulaDamage deepCopy() {
        return new DealFormulaDamage(
            attackingStat, 
            defendingStat, 
            basePower.deepCopy()
        );
    } 


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<Double>> damageBranches = basePower.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        Move move = context.user().activeLoomian().action().move();
        double damage;
        double damageModifier;
        double typeModifier;
        double critModifier; // this needs to be fixed, currently crit Modifier just assumes it will always be active

        for (Branch<Double> damageBranch : damageBranches) {
            BattleContext outcome = context.deepCopy();

            //REMEMEBR ME
            damage = Math.floor(damageBranch.value()); // quick n dirty solution to all my problems, could cause a bug
            context.dispatchEvent(EventId.DAMAGE_MODIFIER);


            double newTargetCurrentHealth = outcome.target().activeLoomian().currentHealth() - damage;
            outcome.user().activeLoomian().action().move().setDamage(damageBranch.value());
        }

        return null;
    }
}
