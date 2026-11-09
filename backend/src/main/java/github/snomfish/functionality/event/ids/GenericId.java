package github.snomfish.functionality.event.ids;

import static github.snomfish.functionality.event.ids.GenericAntiId.*;
import static github.snomfish.functionality.event.ids.IsolatedId.*;

public enum GenericId implements IGenericId {
    SELF_SWAPPED_IN(WATCHES_OPP_SWAP_IN, LOOMIAN_SWAPS_IN),

    PRE_SELF_DEAL_FORMULA_DAMAGE(PRE_OPP_DEAL_FORMULA_DAMAGE, PRE_DEAL_FORMULA_DAMAGE),
    SELF_MOVE_HIT(HIT_BY_MOVE, LOOMIAN_MOVE_HIT),
    SELF_MOVE_DEALT_DAMAGE(RECEIVED_DAMAGE_FROM_MOVE, LOOMIAN_DEALT_FORMULA_DAMAGE),

    SELF_STATS_LOWERED_BY_OPP(LOWERED_OPP_STATS, LOOMIAN_HAD_STATS_LOWERED),
    SELF_STATS_LOWERED_BY_SELF(WATCHES_OPP_LOWER_OPP_STATS, LOOMIAN_HAD_STATS_LOWERED),
    ;


    private GenericAntiId oppId;
    private IsolatedId isoId; 


    private GenericId(
        GenericAntiId oppId,
        IsolatedId isoId
    ) {
        this.oppId = oppId;
        this.isoId = isoId;
    }


    public GenericAntiId oppId() {return oppId;}
    public IsolatedId isoId() {return isoId;}
}
