package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.item.ItemRegistry;

public class TestItemRegistry {
    

    @Test
    void itemHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "ItemRegistry",
            ItemId.class,
            ItemRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "ItemRegistry",
            ItemId.class,
            ItemRegistry.registry()
        );
    }
}
