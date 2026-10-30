package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventSideId;

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


    public List<Branch<BattleContext>> handleEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));
        Ability ability = ability();

        if (hasTriggeredThisSwapIn && !hasTriggeredThisBattle) throw new RuntimeException("the ability instance is marked as having been triggered this swap in but not triggered this battle, this is not possible");
        if (ability.oncePerBattle() && hasTriggeredThisBattle) return outcomes;
        if (ability.oncePerSwapIn() && hasTriggeredThisSwapIn) return outcomes;

        for (AbilityRule rule : ability.rules()) {
            outcomes = flatMap(outcomes, c -> executeRule(c, event, rule));
        }

        return outcomes;
    }


    public List<Branch<BattleContext>> executeRule(BattleContext context, Event event, AbilityRule rule) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        if (!rule.events().contains(event)) return List.of(new Branch<>(context, 1));

        for (Branch<Boolean> conditionBranch : rule.condition().execute(context)) {
            if (conditionBranch.value()) { // if the condition is true
                BattleContext outcome = context.deepCopy();

				// idk how else to try and set the triggered booleans to true or false
                AbilityId userAbilityId =           (AbilityId) Value.USER_ABILITY_ID.get(context);
                AbilityId userActualAbilityId =     (AbilityId) Value.USER_ACTUAL_ABILITY_ID.get(context);
                AbilityId targetAbilityId =         (AbilityId) Value.TARGET_ABILITY_ID.get(context);
                AbilityId targetActualAbilityId = 	(AbilityId) Value.TARGET_ACTUAL_ABILITY_ID.get(context);
                
                if (event.sideId() == EventSideId.USER && userAbilityId == userActualAbilityId) {
                    Value.USER_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.USER_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
					Value.USER_ACTUAL_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.USER_ACTUAL_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
                } 
				else if (event.sideId() == EventSideId.USER && userAbilityId != userActualAbilityId) {
					Value.USER_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.USER_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
				}
				else if (event.sideId() == EventSideId.TARGET && targetAbilityId == targetActualAbilityId) {
                    Value.TARGET_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.TARGET_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
					Value.TARGET_ACTUAL_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.TARGET_ACTUAL_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
                } 
				else if (event.sideId() == EventSideId.TARGET && targetAbilityId != targetActualAbilityId) {
					Value.TARGET_ABILITY_HAS_TRIGGERED_THIS_BATTLE.set(outcome, true);
                    Value.TARGET_ABILITY_HAS_TRIGGERED_THIS_SWAP.set(outcome, true);
				}

				outcomes.addAll(rule.effect().execute(outcome));
            }
        }

		return outcomes;
    }
}
