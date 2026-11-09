package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.mechanics.MechanicRegistry;

public class TestMechanicRegistry {
    

    @Test
    void mechanicsHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "MechanicRegistry",
            MechanicId.class,
            MechanicRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "MechanicRegistry",
            MechanicId.class,
            MechanicRegistry.registry()
        );
    }
}
