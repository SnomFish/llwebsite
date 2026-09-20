package github.snomfish.domain.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.event.TriggerRule;

import static github.snomfish.domain.status.StatusId.*;

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
        String name,
        TriggerRule rule
    ) {
		if (rule == null) {
			registry.put(id, new Status(id, name, new ArrayList<TriggerRule>()));
			return;
		}
        registry.put(id, new Status(id, name, List.of(rule)));
    }


    public static void init() {
		if (initialised) return;
		initialised = true;
        register(
            NO_STATUS,
            "no status",
            null
        );  
        register(
            POISON,
            "posion",
            null
        );
        register(
            BAD_POISON,
            "poison",
            null
        );
        register(
            PARALYSIS,
            "paralysis",
            null
        );
        register(
            BURN,
            "burn",
            null
        );
        register(
            FROSTBITE,
            "frostbite",
            null
        );
        register(
            SLEEP,
            "sleep",
            null
        );
    }
}
