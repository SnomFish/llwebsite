package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

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
    public static IEffect user(AbilityId ability) {
        return new SetAbility(Value.USER_ABILITY_ID, ability);
    }
    public static IEffect userActual(AbilityId ability) {
        return new SetAbility(Value.USER_ACTUAL_ABILITY_ID, ability);
    }
    public static IEffect target(AbilityId ability) {
        return new SetAbility(Value.TARGET_ABILITY_ID, ability);
    }
    public static IEffect targetActual(AbilityId ability) {
        return new SetAbility(Value.TARGET_ACTUAL_ABILITY_ID, ability);
    }
    // Value
    public static IEffect user(Value ability) {
        return new SetAbility(Value.USER_ABILITY_ID, ability);
    }
    public static IEffect userActual(Value ability) {
        return new SetAbility(Value.USER_ACTUAL_ABILITY_ID, ability);
    }
    public static IEffect target(Value ability) {
        return new SetAbility(Value.TARGET_ABILITY_ID, ability);
    }
    public static IEffect targetActual(Value ability) {
        return new SetAbility(Value.TARGET_ACTUAL_ABILITY_ID, ability);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        AbilityId ability = Value.resolve(abilityObject, AbilityId.class, outcome);
        abilityToBeSet.set(outcome, ability);

        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(outcome, 1));

        return outcomes;
    }
}
