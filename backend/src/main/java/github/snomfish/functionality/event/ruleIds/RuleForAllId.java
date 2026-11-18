package github.snomfish.functionality.event.ruleIds;

import static github.snomfish.functionality.event.ruleIds.RuleForTargetId.*;
import static github.snomfish.functionality.event.ruleIds.RuleForUserId.*;

public enum RuleForAllId implements IRuleId {
    NULL_EVENT(), // should never be dispatched

    LOOMIAN_SWAPS_IN					(SELF_SWAPPED_IN, 						WATCHES_OPP_SWAP_IN),
	LOOMIAN_DAMAGED_BELOW_HALF			(SELF_DAMAGED_BELOW_HALF,				OPP_DAMAGED_BELOW_HALF),
    LOOMIAN_FAINTED                     (SELF_FAINTED,                          OPP_FAINTED),
    LOOMIAN_KNOCKED_OUT_BY_OTHER		(SELF_KNOCKED_OUT_OPP, 					SELF_KNOCKED_OUT_BY_OPP),

    // move
    PRE_MOVE                            (SELF_PRE_MOVE,                         OPP_PRE_MOVE),
    PRE_ENERGY_CALC                     (SELF_PRE_ENERGY_CALC,                  OPP_PRE_ENERGY_CALC),
    PRE_CHECK_ACCURACY					(SELF_PRE_ACCURACY_CHECK, 				SELF_WATCHES_OPP_PRE_ACCURACY_CHECK),
    LOOMIAN_CLUTCHES					(SELF_CLUTCHES, 						SELF_WATCHES_OPP_CLUTCH),
    LOOMIAN_POST_MOVE					(SELF_POST_MOVE, 						SELF_WATCHES_OPP_POST_MOVE),

    // formula damage
    PRE_DEAL_FORMULA_DAMAGE				(PRE_SELF_DEAL_FORMULA_DAMAGE, 			PRE_OPP_DEAL_FORMULA_DAMAGE),
    PRE_DAMAGING_LOOMIAN				(SELF_PRE_DAMAGING, 					SELF_PRE_DAMAGED),
    DEALT_FORMULA_DAMAGE				(SELF_MOVE_DEALT_DAMAGE, 				RECEIVED_DAMAGE_FROM_MOVE),
    PRE_DRAINING_HEALTH					(SELF_PRE_DRAINING_HEALTH, 				SELF_PRE_HEALTH_DRAINED),
    PRE_DRAINING_ENERGY					(SELF_PRE_DRAINING_ENERGY, 				SELF_PRE_ENERGY_DRAINED),

    // item
    LOOMIAN_CONSUMED_ITEM				(SELF_CONSUMED_ITEM, 					SELF_WATCHES_OPP_CONSUME_ITEM),
    LOOMIAN_LOST_ITEM					(SELF_LOSES_ITEM, 						SELF_WATCHES_OPP_LOSE_ITEM),

    // stat changes
    PRE_LOOMIAN_STATS_CHANGED			(SELF_PRE_STATS_CHANGED, 				OPP_PRE_STATS_CHANGED),
    LOOMIAN_STATS_LOWERED_BY_OTHER		(SELF_STATS_LOWERED_BY_OPP, 			LOWERED_OPP_STATS),
    LOOMIAN_STATS_LOWERED_BY_SELF		(SELF_STATS_LOWERED_BY_SELF, 			WATCHES_OPP_LOWER_OPP_STATS),

    // setting
    LOOMIAN_SET_AUGMENT                 (SELF_SET_AUGMENT,                      OPP_SET_AUGMENT),
    LOOMIAN_SET_BANE_OF_HASTE           (SELF_SET_BANE_OF_HASTE,                OPP_SET_BANE_OF_HASTE),
    LOOMIAN_SET_BANE_OF_WIELDING        (SELF_SET_BANE_OF_WIELDING,             OPP_SET_BANE_OF_WIELDING),
    LOOMIAN_SET_NEAR_ENCHANTMENT		(SELF_SET_NEAR_ENCHANTMENT,				OPP_SET_NEAR_ENCHANTMENT),
    LOOMIAN_SET_FAR_ENCHANTMENT			(SELF_SET_FAR_ENCHANTMENT,				OPP_SET_FAR_ENCHANTMENT),
    LOOMIAN_SET_FUNGUS_CURSE            (SELF_SET_FUNGUS_CURSE,                 OPP_SET_FUNGUS_CURSE),
    LOOMIAN_SET_SWIFTEN                 (SELF_SET_SWIFTEN,                      OPP_SET_SWIFTEN),
    LOOMIAN_SET_WEATHER					(SELF_SET_WEATHER, 						OPP_SET_WEATHER),
    ;


    private RuleForUserId ruleForUserId;
    private RuleForTargetId ruleForTargetId;


    private RuleForAllId() {
        this.ruleForUserId = RuleForUserId.NULL_EVENT;
        this.ruleForTargetId = RuleForTargetId.NULL_EVENT;
    }
    private RuleForAllId(
        RuleForUserId ruleForUserId,
        RuleForTargetId ruleForTargetId
    ) {
        this.ruleForUserId = ruleForUserId;
        this.ruleForTargetId = ruleForTargetId;
    }


    public RuleForUserId forUser() {return ruleForUserId;}
    public RuleForTargetId forTarget() {return ruleForTargetId;}
}
