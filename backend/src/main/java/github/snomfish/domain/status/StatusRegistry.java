package github.snomfish.domain.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.move.CategoryId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.conditions.Equals;
import github.snomfish.functionality.condition.conditions.NoCondition;
import github.snomfish.functionality.effect.effects.DealDirectSelfDamage;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.event.TriggerRule;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.status.StatusId.*;
import static github.snomfish.functionality.event.EventSideId.*;

public class StatusRegistry {
    

	private static boolean initialised = false;
    private static final Map<StatusId, Status> registry = new HashMap<>();


    private StatusRegistry() {}


    public static Status get(StatusId id) {
		if (!initialised) throw new IllegalArgumentException("Statuses has not been initialised");
        return registry.get(id);
    }


    private static void register(
        StatusId id,
        String name
    ) {
        registry.put(id, new Status(id, name, new ArrayList<TriggerRule>()));
    }
    private static void register(
        StatusId id,
        String name,
        TriggerRule rule
    ) {
        registry.put(id, new Status(id, name, List.of(rule)));
    }
    private static void register(
        StatusId id,
        String name,
        List<TriggerRule> rules
    ) {
        registry.put(id, new Status(id, name, rules));
    }


    public static void init() {
		if (initialised) return;
		initialised = true;
        register(
            NO_STATUS,
            "no status",
            new TriggerRule(
                new Event(), 
                new NoCondition(),
                new NoEffect() 
            )
        );  
        register(
            POISON,
            "posion",
            new TriggerRule(
                new Event(EventId.END_TURN_STATUS_EFFECT_EVENT, USER),
                new NoCondition(),
                new DealDirectSelfDamage(new Constant(1/8))
            )
        );
        register(
            BAD_POISON,
            "poison",
            new TriggerRule(
                new Event(EventId.END_TURN_STATUS_EFFECT_EVENT, USER),
                new NoCondition(),
                new DealDirectSelfDamage(new Multiply(List.of(
                    new Constant(1/16),
                    new ValueConstant(Value.USER_STATUS_TURNS)
                )))
            )
        );
        register(
            PARALYSIS,
            "paralysis"
        );
        register(
            BURN,
            "burn",
            List.of(new TriggerRule(
                new Event(EventId.END_TURN_STATUS_EFFECT_EVENT, USER),
                new NoCondition(),
                new DealDirectSelfDamage(new Constant(1/16))
            ),
            new TriggerRule( // cutting mattack in half
                new Event(EventId.BEFORE_MOVE_EVENT, USER), 
                new Equals(Value.MOVE_CATEGORY, CategoryId.MELEE),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant (0.5)) 
            ))
        );
        register(
            FROSTBITE,
            "frostbite",
            List.of(new TriggerRule(
                new Event(EventId.END_TURN_STATUS_EFFECT_EVENT, USER),
                new NoCondition(),
                new DealDirectSelfDamage(new Constant(1/16))
            ),
            new TriggerRule( // cutting mattack in half
                new Event(EventId.BEFORE_MOVE_EVENT, USER), 
                new Equals(Value.MOVE_CATEGORY, CategoryId.RANGED),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant (0.5)) 
            ))
        );
        register(
            SLEEP,
            "sleep"
        );
    }
}
