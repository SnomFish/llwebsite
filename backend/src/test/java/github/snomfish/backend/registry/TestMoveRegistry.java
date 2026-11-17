package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.move.MoveRegistry;

public class TestMoveRegistry {
    

    @Test
    void moveHasRegisteredAllEnums() {
        GeneralTestRegistry.hasRegisteredAllEnums(
            "MoveRegistry",
            MoveId.values(),
            MoveRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralTestRegistry.allEntriesAreUnique(
            "MoveRegistry",
            MoveId.values(),
            MoveRegistry.registry()
        );
    }
}
