package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetAbility implements IEffect {
    

    private Value<AbilityId> abilityToBeSet;
    private Object abilityObject;


    private SetAbility(
        Value<AbilityId> abilityToBeSet,
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
        return new SetAbility(Value.USER().ABILITY_ID(), ability);
    }
    public static IEffect userActual(AbilityId ability) {
        return new SetAbility(Value.USER().ACTUAL_ABILITY_ID(), ability);
    }
    public static IEffect target(AbilityId ability) {
        return new SetAbility(Value.TARGET().ABILITY_ID(), ability);
    }
    public static IEffect targetActual(AbilityId ability) {
        return new SetAbility(Value.TARGET().ACTUAL_ABILITY_ID(), ability);
    }
    // Value
    public static IEffect user(Value<AbilityId> ability) {
        return new SetAbility(Value.USER().ABILITY_ID(), ability);
    }
    public static IEffect userActual(Value<AbilityId> ability) {
        return new SetAbility(Value.USER().ACTUAL_ABILITY_ID(), ability);
    }
    public static IEffect target(Value<AbilityId> ability) {
        return new SetAbility(Value.TARGET().ABILITY_ID(), ability);
    }
    public static IEffect targetActual(Value<AbilityId> ability) {
        return new SetAbility(Value.TARGET().ACTUAL_ABILITY_ID(), ability);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        AbilityId ability = Value.resolve(abilityObject, outcome);
        abilityToBeSet.set(outcome, ability);

        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(outcome, 1));

        return outcomes;
    }
}
