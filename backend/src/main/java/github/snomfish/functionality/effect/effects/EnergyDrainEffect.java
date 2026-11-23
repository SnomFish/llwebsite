package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class EnergyDrainEffect<T extends Number> implements IEffect {
    

    private Value<T> drainValue;
    private double drainFraction; // fraction of the damage dealt


    @SuppressWarnings("unchecked")
    public EnergyDrainEffect(double drainFraction) {
        this.drainValue = (Value<T>) Value.USER().MOVE().DAMAGE();
        this.drainFraction = drainFraction;
    }
    public EnergyDrainEffect(Value<T> drainValue, double drainFraction) {
        this.drainValue = drainValue;
        this.drainFraction = drainFraction;
    }


    private EnergyDrainEffect(EnergyDrainEffect<T> copy) {
        this.drainValue = copy.drainValue;
        this.drainFraction = copy.drainFraction;
    }
    public EnergyDrainEffect<T> deepCopy() {
        return new EnergyDrainEffect<>(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        outcomes = flatMap(outcomes, c -> new ResetModifier().userMove(MoveModifierId.ENERGY_DRAIN).execute(c));
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.PRE_DRAINING_ENERGY, ContextSideId.USER));
        outcomes = flatMap(outcomes, this::executeHealthDrain);
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMIAN_DRAINED_ENERGY, ContextSideId.USER));

        return outcomes;
    }


    private List<Branch<BattleContext>> executeHealthDrain(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        double energyDrain = (double) drainValue.get(outcome);
        energyDrain *= drainFraction;
        energyDrain *= Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_DRAIN).get(outcome);

        int maxEnergy = Value.USER().ENERGY().get(outcome).intValue();
        int currentEnergy = Value.USER().CURRENT_ENERGY().get(outcome);
        energyDrain = Math.clamp(energyDrain, 0, maxEnergy - currentEnergy);

        ActiveLoomian user = Value.USER().LOOMIAN().get(outcome);
        Move move = Value.USER().MOVE().get(outcome);
        user.addToCurrentEnergy((int) energyDrain);
        move.setEnergyDrained((int) energyDrain);

        return wrap(outcome);
    }
}
