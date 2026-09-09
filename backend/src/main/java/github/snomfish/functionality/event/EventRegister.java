package github.snomfish.functionality.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.event.EventTarget.*;

public class EventRegister {
    

    private static final Map<EventId, Event> registry = new HashMap<>();


    private EventRegister() {}


    public static List<Branch<BattleContext>> dispatch(EventId id, BattleContext context) {
        if (!registry.containsKey(id)) {
            throw new IllegalArgumentException("event registry does not contain key " + id.name());
        }

        Event event = registry.get(id); 
        return event.execute(context);
    }


    private static void register(
        EventId id, EventTarget target
    ) {
        Event event = new Event(id, target);
        registry.put(id, event);
    }


    static {
        register(BEFORE_MOVE_EVENT, ALL);
        register(USER_BEFORE_MOVE_EVENT, USER);
        register(TARGET_BEFORE_MOVE_EVENT, TARGET);
        register(DAMAGE_MODIFIER_EVENT, ALL);
        register(USER_DAMAGE_MODIFIER_EVENT, USER);
        register(TARGET_DAMAGE_MODIFIER_EVENT, TARGET);
    }
}
