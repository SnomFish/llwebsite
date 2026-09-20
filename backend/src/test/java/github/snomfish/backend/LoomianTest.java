package github.snomfish.backend;

import java.util.List;

import github.snomfish.domain.Loomian;
import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.ability.AbilityRegistry;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.item.ItemRegistry;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.nature.Nature;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.status.StatusRegistry;

import static github.snomfish.domain.nature.NatureId.*;

public class LoomianTest {
    

    public static Loomian embit() {
        SpeciesRegistry.init();
        AbilityRegistry.init();
        ItemRegistry.init();
        StatusRegistry.init();
        return new Loomian(
            SpeciesId.EMBIT,
            List.of(), // moves
            AbilityId.AWAKENING,
            ItemId.NO_ITEM,
            StatusId.NO_STATUS,
            Nature.newNature(List.of(INDIFFERENT)), // natures (I hate my current implementation)
            new Stats(0, 0, 0, 0, 0, 0, 0),
            new Stats(40, 40, 40, 40, 40, 40, 40)
        );
    }
}
