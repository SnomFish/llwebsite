package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.event.EventId.*;

public class HealthDrainEffect implements IEffect {
    

    private INumber drain; // fraction of the damage dealt


    public HealthDrainEffect(
        INumber drain
    ) {
        this.drain = drain;
    }
    public HealthDrainEffect(HealthDrainEffect copy) {
        this.drain = copy.drain.deepCopy();
    }


    @Override 
    public HealthDrainEffect deepCopy() {
        return new HealthDrainEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for(Branch<BattleContext> contextBranch : context.dispatchEvent(HEALTH_DRAIN_EVENT)) {
            for(Branch<Double> drainBranch : drain.execute(context)) {

                BattleContext outcome = contextBranch.value().deepCopy();
                ActiveLoomian user	 = outcome.user().activeLoomian();
                Move move			 = user.action().move();
                int healthDrain		 = (int) (move.damage() * move.healthDrainModifier() * drainBranch.value());
    
				healthDrain = Math.min(healthDrain, user.maxHealth() - user.currentHealth()); // prevents the drain from over healing
				user.addToCurrentHealth(healthDrain);

				outcomes.add(new Branch<>(
					outcome,
					contextBranch.probability() * drainBranch.probability() 
				));
            }
        }

        return outcomes;
    }
}
