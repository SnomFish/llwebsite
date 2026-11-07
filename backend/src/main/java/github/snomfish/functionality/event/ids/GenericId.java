package github.snomfish.functionality.event.ids;

import static github.snomfish.functionality.event.ids.GenericAntiId.*;

public enum GenericId implements IEventId {
    SELF_SWAPPED_IN(OPP_WATCHES_SELF_SWAP_IN),

    SELF_STATS_LOWERED_BY_OPP(OPP_LOWERED_SELF_STATS),
    SELF_STATS_LOWERED_BY_SELF(OPP_WATCHES_SELF_LOWER_SELF_STATS),
    ;


    private GenericAntiId oppId; 


    private GenericId(GenericAntiId oppId) {
        this.oppId = oppId;
    }


    public GenericAntiId oppId() {return oppId;}
}
