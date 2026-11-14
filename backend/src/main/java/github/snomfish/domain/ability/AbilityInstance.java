package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class AbilityInstance implements DeepCopyable<AbilityInstance> {
    

    private AbilityId abilityId;
    private boolean hasTriggeredThisBattle;
    private boolean hasTriggeredThisSwapIn;


    public AbilityInstance(
        AbilityId abilityId
    ) {
        this.abilityId = abilityId;
        this.hasTriggeredThisBattle = false;
        this.hasTriggeredThisSwapIn = false;
    }


    private AbilityInstance(AbilityInstance copy) {
        this.abilityId = copy.abilityId;
        this.hasTriggeredThisBattle = copy.hasTriggeredThisBattle;
        this.hasTriggeredThisSwapIn = copy.hasTriggeredThisSwapIn;
    }
    public AbilityInstance deepCopy() {
        return new AbilityInstance(this);
    }


    // getter
    public AbilityId abilityId() {return abilityId;}
    public Ability ability() {return AbilityRegistry.get(abilityId);}
    public boolean hasTriggeredThisBattle() {return hasTriggeredThisBattle;}
    public boolean hasTriggeredThisSwapIn() {return hasTriggeredThisSwapIn;}


    // setter
    public void setAbilityId(AbilityId abilityId) {this.abilityId = abilityId;}
    public void setTriggeredThisBattle(boolean triggered) {hasTriggeredThisBattle = triggered;}
    public void setTriggeredThisSwapIn(boolean triggered) {hasTriggeredThisSwapIn = triggered;}


    public List<Branch<BattleContext>> handleRule(BattleContext context, IRuleId event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));
        Ability ability = ability();

        if (hasTriggeredThisSwapIn && !hasTriggeredThisBattle) throw new RuntimeException("the ability instance is marked as having been triggered this swap in but not triggered this battle, this is not possible");
        if (ability.oncePerBattle() && hasTriggeredThisBattle) return outcomes;
        if (ability.oncePerSwapIn() && hasTriggeredThisSwapIn) return outcomes;

        for (Rule rule : ability.rules()) {
            outcomes = flatMap(outcomes, c -> executeRule(c, event, rule));
        }

        return outcomes;
    }


    public List<Branch<BattleContext>> executeRule(BattleContext context, IRuleId event, Rule rule) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        if (!rule.events().contains(event)) return List.of(new Branch<>(context, 1));

        double probability = rule.condition().execute(context);

        if (probability > 0) { // if the condition is true
            BattleContext outcome = context.deepCopy();

            AbilityId abilityId =           (AbilityId) Value.USER().ABILITY_ID().get(context);
            AbilityId actualAbilityId =     (AbilityId) Value.USER().ACTUAL_ABILITY_ID().get(context);
            
            Value.USER().ABILITY_HAS_ACTIVATED_THIS_BATTLE().set(context, true);
            Value.USER().ABILITY_HAS_ACTIVATED_THIS_SWAP().set(context, true);
            if (abilityId == actualAbilityId) {
                Value.USER().ACTUAL_ABILITY_HAS_ACTIVATED_THIS_BATTLE().set(context, true);
                Value.USER().ACTUAL_ABILITY_HAS_ACTIVATED_THIS_SWAP().set(context, true);
            }

            outcomes.addAll(applyProbability(rule.effect().execute(outcome), probability));
        }
        if (probability < 1) {
            BattleContext outcome = context.deepCopy();
            outcomes.add(new Branch<>(outcome, 1-probability));
        }

		return outcomes;
    }


    public double handleGate(BattleContext context, IGateId gateId) {
        return ability().handleGate(context, gateId);
    }
}