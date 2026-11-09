package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.move.MoveRegistry;

public class TestMoveRegistry {
    

    @Test
    void moveHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "MoveRegistry",
            MoveId.class,
            MoveRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "MoveRegistry",
            MoveId.class,
            MoveRegistry.registry()
        );
    }
}
