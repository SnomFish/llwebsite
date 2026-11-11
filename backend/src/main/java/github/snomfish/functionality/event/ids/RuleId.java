package github.snomfish.functionality.event.ids;

public enum RuleId implements IEventId {

    // ACTIVATED DURING THE TURN

    PRE_MOVE_EVENT, // used when a move is about to be used

    ACCURACY_CHECK_EVENT,
    PRE_DAMAGE_EVENT,
    CLUTCH_EVENT,
    DEALT_DAMAGE_EVENT, // used if the move dealt damage
    USER_KNOCKED_OUT_TARGET_EVENT,
    TARGET_KNOCKED_OUT_USER_EVENT,
    POST_DAMAGE_EVENT, // used wether the move hit or missed

    ;
}
