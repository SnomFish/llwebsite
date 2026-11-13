package github.snomfish.functionality.event.eventIds;

public enum RuleForUserId implements IRuleForSideId {
    NULL_EVENT, // should never be dispatched

    SELF_SWAPPED_IN,
    SELF_KNOCKED_OUT_OPP,

    // move
    SELF_PRE_MOVE,
    SELF_PRE_ACCURACY_CHECK,
    SELF_CLUTCHES,
    SELF_POST_MOVE,

    // formula damage
    PRE_SELF_DEAL_FORMULA_DAMAGE,
    SELF_PRE_DAMAGING,
    SELF_MOVE_HIT,
    SELF_MOVE_DEALT_DAMAGE,
    SELF_PRE_DRAINING_HEALTH,
    SELF_PRE_DRAINING_ENERGY,

    // item
    SELF_CONSUMED_ITEM,
    SELF_LOSES_ITEM,

    // stat changes
    SELF_PRE_STATS_CHANGED,
    SELF_STATS_LOWERED_BY_OPP,
    SELF_STATS_LOWERED_BY_SELF,

    // weather
    SELF_SET_WEATHER,
    ;
}
