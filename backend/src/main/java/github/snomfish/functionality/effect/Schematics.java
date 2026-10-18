package github.snomfish.functionality.effect;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.effects.ApplyStatus;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

public class Schematics {


    private Schematics() {}


    // this is bad code, but I dont know how to do suppression in this engine
    // 2026 09 26: I think this is actually a great idea 
    public static IEffect secondaryEffects(IEffect effects) {
        return new ConditionalEffect(
            new And(List.of(
                new Not(new Equals(Value.USER_ABILITY_ID, AbilityId.BRUTE_FORCE))
            )),
            effects
        );
    }


    public static IEffect applyStatusToTarget(
        INumber number,
        StatusId statusId
    ) {
        return new ConditionalEffect(
            new RandomCondition(number),
            ApplyStatus.toTarget(statusId)
        );  
    }
    public static IEffect applyStatusToTarget(
        Double chance,
        StatusId statusId
    ) {
        return applyStatusToTarget(new Constant(chance), statusId);
    }
}