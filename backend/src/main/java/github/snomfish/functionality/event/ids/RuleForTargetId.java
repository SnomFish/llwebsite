package github.snomfish.functionality.event.ids;

public enum RuleForTargetId implements IRuleForSideId {
    NULL_EVENT, // should never be dispatched

    WATCHES_OPP_SWAP_IN,
    SELF_KNOCKED_OUT_BY_OPP,

    // move
    SELF_WATCHES_OPP_PRE_MOVE,
    SELF_WATCHES_OPP_PRE_ACCURACY_CHECK,
    SELF_WATCHES_OPP_CLUTCH,
    SELF_WATCHES_OPP_POST_MOVE,

    // formula damage
    PRE_OPP_DEAL_FORMULA_DAMAGE,
    SELF_PRE_DAMAGED,
    HIT_BY_MOVE,
    RECEIVED_DAMAGE_FROM_MOVE,
    SELF_PRE_HEALTH_DRAINED,
    SELF_PRE_ENERGY_DRAINED,

    // item
    SELF_WATCHES_OPP_CONSUME_ITEM,
    SELF_WATCHES_OPP_LOSE_ITEM,

    // stat changes
    OPP_PRE_STATS_CHANGED,
    LOWERED_OPP_STATS,
    WATCHES_OPP_LOWER_OPP_STATS,

    // weather
    OPP_SET_WEATHER,
    ;
}
