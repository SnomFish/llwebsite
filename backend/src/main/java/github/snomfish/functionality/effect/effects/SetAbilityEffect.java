package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.functionality.event.EventId.TARGET_SWITCHED_IN_EVENT;
import static github.snomfish.functionality.event.EventId.USER_SWITCHED_IN_EVENT;

public class SetAbilityEffect implements IEffect {
    

    private Object abilityObject;
    private Value abilityToSet;
    private EventId swappedInEventId;


    private SetAbilityEffect(
        Object abilityObject,
        Value abilityToSet,
        EventId swappedInEventId
    ) {
        this.abilityObject = abilityObject;
        this.abilityToSet = abilityToSet;
        this.swappedInEventId = swappedInEventId;
    }


    private SetAbilityEffect(SetAbilityEffect copy) {
        this.abilityObject = copy.abilityObject; // should either be a Value or an AbilityId
        this.abilityToSet = copy.abilityToSet;
        this.swappedInEventId = copy.swappedInEventId;
    }
    public SetAbilityEffect deepCopy() {
        return new SetAbilityEffect(this);
    }


    public static SetAbilityEffect user(Value abilityValue) {
        return new SetAbilityEffect(abilityValue,
            Value.USER_ABILITY_ID,
            USER_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect user(AbilityId abilityId) {
        return new SetAbilityEffect(abilityId,
            Value.USER_ABILITY_ID,
            USER_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect userActual(Value abilityValue) {
        return new SetAbilityEffect(abilityValue,
            Value.USER_ACTUAL_ABILITY_ID,
            USER_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect userActual(AbilityId abilityId) {
        return new SetAbilityEffect(abilityId,
            Value.USER_ACTUAL_ABILITY_ID,
            USER_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect target(Value abilityValue) {
        return new SetAbilityEffect(abilityValue,
            Value.TARGET_ABILITY_ID,
            TARGET_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect target(AbilityId abilityId) {
        return new SetAbilityEffect(abilityId,
            Value.TARGET_ABILITY_ID,
            TARGET_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect targetActual(Value abilityValue) {
        return new SetAbilityEffect(abilityValue,
            Value.TARGET_ACTUAL_ABILITY_ID,
            TARGET_SWITCHED_IN_EVENT);
    }
    public static SetAbilityEffect targetActual(AbilityId abilityId) {
        return new SetAbilityEffect(abilityId,
            Value.TARGET_ACTUAL_ABILITY_ID,
            TARGET_SWITCHED_IN_EVENT);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        AbilityId abilityId = Value.resolve(abilityObject, AbilityId.class, outcome);
        abilityToSet.set(outcome, abilityId);

        return List.of(new Branch<>(
            outcome,
            1
        ));
    }
}
