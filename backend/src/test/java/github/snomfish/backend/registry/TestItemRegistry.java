package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.item.ItemRegistry;

public class TestItemRegistry {
    

    @Test
    void itemHasRegisteredAllEnums() {
        GeneralTestRegistry.hasRegisteredAllEnums(
            "ItemRegistry",
            ItemId.values(),
            ItemRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralTestRegistry.allEntriesAreUnique(
            "ItemRegistry",
            ItemId.values(),
            ItemRegistry.registry()
        );
    }
}
