package github.snomfish.functionality.event.ids;

public enum IsolatedId implements IEventId {
    NULL_EVENT, // should never be dispatched

    PRE_TURN,
    START_OF_TURN,
    END_OF_TURN_STATUS_EFFECTS,
    END_OF_TURN,

    LOOMIAN_SWAPS_IN,

    // formula damage
    PRE_DEAL_FORMULA_DAMAGE,
    LOOMIAN_PRE_DAMAGED,
    LOOMIAN_MOVE_HIT,
    LOOMIAN_DEALT_FORMULA_DAMAGE,
    LOOMIAN_PRE_HEATH_DRAINED,
    LOOMIAN_PRE_ENERGY_DRAINED,

    // ITEM
    LOOMIAN_CONSUMES_ITEM,
    LOOMIAN_LOSES_ITEM,

    // stat changes
    PRE_LOOMIAN_STATS_CHANGED,
    LOOMIAN_HAD_STATS_LOWERED,

    // weather
    LOOMIAN_SET_WEATHER,
    ;
}
