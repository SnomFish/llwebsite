package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.event.EventId.USER_SWITCHED_IN_EVENT;

public class SetAbility implements IEffect {
    

    private Value abilityToBeSet;
    private Object abilityObject;


    private SetAbility(
        Value abilityToBeSet,
        Object abilityObject
    ) {
        this.abilityToBeSet = abilityToBeSet;
        this.abilityObject = abilityObject;
    }
    
    
    private SetAbility(SetAbility copy) { // works only if the values are Value or AbilityId
        this.abilityToBeSet = copy.abilityToBeSet;
        this.abilityObject = copy.abilityObject;
    }
    public SetAbility deepCopy() {
        return new SetAbility(this);
    }


    // AbilityId
    public static SetAbility user(AbilityId abilityId) {
        return new SetAbility(Value.USER_ABILITY_ID, abilityId);
    }
    public static SetAbility userActual(AbilityId abilityId) {
        return new SetAbility(Value.USER_ACTUAL_ABILITY_ID, abilityId);
    }
    public static SetAbility target(AbilityId abilityId) {
        return new SetAbility(Value.TARGET_ABILITY_ID, abilityId);
    }
    // Value
    public static SetAbility user(Value abilityValue) {
        return new SetAbility(Value.USER_ABILITY_ID, abilityValue);
    }
    public static SetAbility target(Value abilityValue) {
        return new SetAbility(Value.TARGET_ABILITY_ID, abilityValue);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        AbilityId type = Value.resolve(abilityObject, AbilityId.class, outcome);
        abilityToBeSet.set(outcome, type);

        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(outcome, 1));
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(USER_SWITCHED_IN_EVENT)); // calls the switched in event to retrigger abilities

        return outcomes;
    }
}
