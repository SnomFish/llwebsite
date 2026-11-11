package github.snomfish.domain.status;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.move.CategoryId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.DealDirectSelfDamage;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.status.StatusId.*;

public class StatusRegistry {
    

    private static final Map<StatusId, Status> registry = new HashMap<>();


    private StatusRegistry() {}


    public static Map<StatusId, Status> registry() {return registry;}
    public static Status get(StatusId id) {return registry.get(id);}


    private static void register(Status status) {
		if (registry.containsKey(status.id())) throw new RuntimeException("StatusRegistry attempted to register duplicate entry \"" + status.id().name() + "\"");
        registry.put(status.id(), status);
    }


    static {
        register(Status.build(NO_STATUS)
            .name("no status")
        );  
        register(Status.build(POISON)
            .name("posion")
            .rule(Rule.build()
                .event(IsolatedId.END_OF_TURN_STATUS_EFFECTS)
                .effect(new DealDirectSelfDamage(new Multiply(new Constant(1/8), new ValueConstant(Value.OPP().HEALTH()))))
            )
        );
        register(Status.build(BAD_POISON)
            .name("poison")
            .rule(Rule.build()
                .event(IsolatedId.END_OF_TURN_STATUS_EFFECTS)
                .effect(new DealDirectSelfDamage(new Multiply(
                    new Constant(1/16),
                    new ValueConstant(Value.USER().STATUS_TURNS())
                )))
            )
        );
        register(Status.build(PARALYSIS)
            .name("paralysis")
        );
        register(Status.build(BURN)
            .name("burn")
            .rule(Rule.build()
                .event(IsolatedId.END_OF_TURN_STATUS_EFFECTS)
                .effect(new DealDirectSelfDamage(new Constant(1/16)))
            )
            .rule(Rule.build() // reduces mattack by 33%
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().CATEGORY(), CategoryId.MELEE))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().ATTACKING_STAT_MODIFIER(), new Constant (2/3)))
            )
        );
        register(Status.build(FROSTBITE)
            .name("frostbite")
            .rule(Rule.build()
                .event(IsolatedId.END_OF_TURN_STATUS_EFFECTS)
                .effect(new DealDirectSelfDamage(new Constant(1/16)))
            )
            .rule(Rule.build() // reduces rattack by 33%
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().CATEGORY(), CategoryId.RANGED))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().ATTACKING_STAT_MODIFIER(), new Constant (2/3)))
            )
        );
        register(Status.build(SLEEP)
            .name("sleep")
        );
    }
}
