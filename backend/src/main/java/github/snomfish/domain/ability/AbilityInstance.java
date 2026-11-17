package github.snomfish.domain.ability;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.resolveConditions;

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
        if (!rule.events().contains(event)) return List.of(new Branch<>(context, 1));

        return resolveConditions(context, rule.conditions(), rule, this::executeRuleEffects);
    }
    public List<Branch<BattleContext>> executeRuleEffects(BattleContext context, Rule rule) {
        BattleContext outcome = context.deepCopy();

        AbilityId abilityId =           (AbilityId) Value.USER().ABILITY_ID().get(outcome);
        AbilityId actualAbilityId =     (AbilityId) Value.USER().ACTUAL_ABILITY_ID().get(outcome);
        
        Value.USER().ABILITY_HAS_ACTIVATED_THIS_BATTLE().set(outcome, true);
        Value.USER().ABILITY_HAS_ACTIVATED_THIS_SWAP().set(outcome, true);
        if (abilityId == actualAbilityId) {
            Value.USER().ACTUAL_ABILITY_HAS_ACTIVATED_THIS_BATTLE().set(outcome, true);
            Value.USER().ACTUAL_ABILITY_HAS_ACTIVATED_THIS_SWAP().set(outcome, true);
        }

        return rule.executeEffects(outcome);
    }


    public double handleGate(BattleContext context, IGateId gateId) {
        return ability().handleGate(context, gateId);
    }
    public HookReport handleInterceptor(BattleContext context, IRuleId ruleId, HookReport report) {
        return ability().handleHook(context, ruleId, report);
    }
}