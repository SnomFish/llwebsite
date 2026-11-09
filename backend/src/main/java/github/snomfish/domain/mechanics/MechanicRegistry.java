package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.RemoveMechanic;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.mechanics.MechanicId.*;

public class MechanicRegistry {


    private static final Map<MechanicId, Mechanic> registry = new HashMap<>();


    private MechanicRegistry() {}


    public static Map<MechanicId, Mechanic> registry() {return registry;}
    public static Mechanic get(MechanicId id) {return registry.get(id);}


    private static void register(Mechanic mechanic) {
        registry.put(mechanic.id(), mechanic);
    }


    static {
        register(Mechanic.build(COMBUSTIBLE)
            .name("combustible")
            .rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.FIRE))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
            )
        );
        register(Mechanic.build(COURSING_VENOM)
            .name("coursing venom")
            .rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.TOXIC))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
            )
        );
        register(Mechanic.build(NOXIOUS_WEEDS)
            .name("Noxious Weeds")
            .rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.PLANT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
            )
        );
        register(Mechanic.build(PRISMATIC)
            .name("Prismatic")
            .rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.LIGHT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
            )
        );
        register(Mechanic.build(TOXIC_FILTER)
            .name("Toxic Filter")
            .rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.AIR))
                .effect(new EffectSequence(
                    Effects.multiplyValue(Value.SELF().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
                    RemoveMechanic.fromUser(TOXIC_FILTER)
                ))
            )
        );
    }
}
