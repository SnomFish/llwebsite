package github.snomfish.functionality.event.ids;

import static github.snomfish.functionality.event.ids.GenericAntiId.*;

public enum GenericId implements IGenericId {
    SELF_SWAPPED_IN(WATCHES_OPP_SWAP_IN),

    SELF_PRE_DEAL_FORMULA_DAMAGE(WATCHES_OPP_PRE_DEAL_FORMULA_DAMAGE),
    SELF_MOVE_HIT(HIT_BY_MOVE),

    SELF_STATS_LOWERED_BY_OPP(LOWERED_OPP_STATS),
    SELF_STATS_LOWERED_BY_SELF(WATCHES_OPP_LOWER_OPP_STATS),
    ;


    private GenericAntiId oppId; 


    private GenericId(GenericAntiId oppId) {
        this.oppId = oppId;
    }


    public GenericAntiId oppId() {return oppId;}
}
