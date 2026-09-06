package github.snomfish.functionality.event;

import java.util.List;
import java.util.function.BiFunction;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public enum EventTarget {
    USER((context, eventId) -> context.dispatchToUser(eventId)),
    TARGET((context, eventId) -> context.dispatchToTarget(eventId)),
    ALL((context, eventId) -> context.dispatch(eventId));


    private final BiFunction<BattleContext, EventId, List<Branch<BattleContext>>> function;


    EventTarget(BiFunction<BattleContext, EventId, List<Branch<BattleContext>>> function) {
        this.function = function;
    }


    public BiFunction<BattleContext, EventId, List<Branch<BattleContext>>> getFunction() {
        return function;
    }
}
