package github.snomfish.domain.mechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.effect.effects.RemoveItem;
import github.snomfish.functionality.effect.effects.RemoveMechanic;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.mechanics.MechanicId.*;
import static github.snomfish.functionality.event.EventSideId.*;
import static github.snomfish.functionality.event.EventId.*;

public class MechanicRegistry {


    private static boolean initialised = false;
    private static final Map<MechanicId, Mechanic> registry = new HashMap<>();


    private MechanicRegistry() {}


    public static Mechanic get(MechanicId id) {
		if (!initialised) throw new IllegalArgumentException("Abilities has not been initialised");
        return registry.get(id);
    }


    private static void register(
        MechanicId id,
        String name,
        TriggerRule rule
    ) {
		if (rule == null) {
			registry.put(id, new Mechanic(id, name, new ArrayList<TriggerRule>()));
			return;
		}
        registry.put(id, new Mechanic(id, name, List.of(rule)));
    }


    static {
        register(
            COMBUSTIBLE,
            "combustible",
            new TriggerRule(
                new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TypeId.FIRE),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5))
            )
        );
        register(
            COURSING_VENOM,
            "coursing venom",
            new TriggerRule(
                new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TypeId.TOXIC),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5))
            )
        );
        register(
            NOXIOUS_WEEDS, 
            "Noxious Weeds",
            new TriggerRule(
                new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TypeId.PLANT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5))
            )
        );
        register(
            PRISMATIC,
            "Prismatic",
            new TriggerRule(
                new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TypeId.LIGHT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5))
            )
        );
        register(
            TOXIC_FILTER,
            "Toxic Filter",
            new TriggerRule(
                new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TypeId.AIR),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
                    RemoveMechanic.fromUser(TOXIC_FILTER)
                ))
            )
        );
    }
}
