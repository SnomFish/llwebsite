package github.snomfish.functionality.effect.effects;

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

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class HealthDrainEffect<T extends Number> implements IEffect {
    

    private Value<T> drainValue;
    private double drainFraction; // fraction of the damage dealt


    @SuppressWarnings("unchecked")
    public HealthDrainEffect(double drainFraction) {
        this.drainValue = (Value<T>) Value.USER().MOVE().DAMAGE();
        this.drainFraction = drainFraction;
    }
    public HealthDrainEffect(Value<T> drainValue, double drainFraction) {
        this.drainValue = drainValue;
        this.drainFraction = drainFraction;
    }


    private HealthDrainEffect(HealthDrainEffect<T> copy) {
        this.drainValue = copy.drainValue;
        this.drainFraction = copy.drainFraction;
    }
    public HealthDrainEffect<T> deepCopy() {
        return new HealthDrainEffect<>(this);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        outcomes = flatMap(outcomes, c -> new ResetModifier().userMove(MoveModifierId.HEALTH_DRAIN).execute(c));
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.PRE_DRAINING_HEALTH, ContextSideId.USER));
        outcomes = flatMap(outcomes, this::executeHealthDrain);
        outcomes = flatMap(outcomes, c -> c.dispatchRule(RuleForAllId.LOOMIAN_DRAINED_HEALTH, ContextSideId.USER));

        return outcomes;
    }


    private List<Branch<BattleContext>> executeHealthDrain(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        double healthDrain = (double) drainValue.get(outcome);
        healthDrain *= drainFraction;
        healthDrain *= Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.HEALTH_DRAIN).get(outcome);

        int maxHealth = Value.USER().HEALTH().get(outcome).intValue();
        int currentHealth = Value.USER().CURRENT_HEALTH().get(outcome);
        healthDrain = Math.clamp(healthDrain, 0, maxHealth - currentHealth);

        ActiveLoomian user = Value.USER().LOOMIAN().get(outcome);
        Move move = Value.USER().MOVE().get(outcome);
        user.addToCurrentHealth((int) healthDrain);
        move.setHealthDrained((int) healthDrain);

        return wrap(outcome);
    }
}
