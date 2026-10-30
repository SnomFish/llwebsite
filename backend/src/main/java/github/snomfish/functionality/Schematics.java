package github.snomfish.functionality;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.ApplyStatus;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

public class Schematics {


    private Schematics() {}


    // this is bad code, but I dont know how to do suppression in this engine
    // 2026 09 26: I think this is actually a great idea 
    public static IEffect secondaryEffects(List<IEffect> effects) {
        return new ConditionalEffect(
            new Not(new Equals(Value.USER_ABILITY_ID, AbilityId.BRUTE_FORCE)),

            new EffectSequence(effects)
        );
    }


    public static INumber tenPercentOfMaxEnergy() {
        return new Multiply(
            new ValueConstant(Value.USER_BATTLE_ENERGY),
            new Constant(0.1)
        );
    }


    // applying status
    public static IEffect applyStatusToTarget(
        INumber number,
        StatusId statusId
    ) {
        return new ConditionalEffect(
            new RandomCondition(number),
            ApplyStatus.toTarget(statusId)
        );  
    }
    public static IEffect applyStatusToTarget(double chance, StatusId statusId) {
        return applyStatusToTarget(new Constant(chance), statusId); // just turns the double into a Constant and calls the same function again
    }
}