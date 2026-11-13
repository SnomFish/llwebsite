package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.move.Move;
import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ids.ToUserId;
import github.snomfish.functionality.number.INumber;

public class EnergyDrainEffect implements IEffect {
    

    private INumber drain; // fraction of the damage dealt


    public EnergyDrainEffect(
        INumber drain
    ) {
        this.drain = drain;
    }
    public EnergyDrainEffect(EnergyDrainEffect copy) {
        this.drain = copy.drain.deepCopy();
    }


    @Override 
    public EnergyDrainEffect deepCopy() {
        return new EnergyDrainEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for(Branch<BattleContext> contextBranch : context.dispatchEvent(ToUserId.SELF_PRE_DRAINING_ENERGY, ContextSideId.USER)) {
            for(Branch<Double> drainBranch : drain.execute(context)) {

                BattleContext outcome = contextBranch.value().deepCopy();
                ActiveLoomian user	 = outcome.user().activeLoomian();
                Move move			 = user.action().move();
                int energyDrain      = (int) (move.damage() * move.modifiers().get(ModifierId.ENERGY_DRAIN) * drainBranch.value());
    
				energyDrain = Math.min(energyDrain, user.maxEnergy() - user.currentEnergy()); // prevents the drain from over healing
				user.addToCurrentEnergy(energyDrain);

				outcomes.add(new Branch<>(
					outcome,
					contextBranch.probability() * drainBranch.probability() 
				));
            }
        }

        return outcomes;
    }
}
