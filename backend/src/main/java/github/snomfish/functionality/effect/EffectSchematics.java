package github.snomfish.functionality.effect;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.Not;

public class EffectSchematics {


    private EffectSchematics() {}


    public static IEffect secondaryEffects(IEffect effects) {
        return new ConditionalEffect(
            new And(List.of(
                new Not(new Equals(Value.USER_ABILITY, AbilityId.BRUTE_FORCE)) // this is bad code, but I dont know how to do suppression in this engine
            )),
            effects
        );
    }
}