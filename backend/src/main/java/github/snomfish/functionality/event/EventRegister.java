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
        register(DAMAGE_MULTIPLIER, ALL);
        register(USER_DAMAGE_MULTIPLIER, USER);
        register(TARGET_DAMAGE_MULTIPLIER, TARGET);
    }
}
