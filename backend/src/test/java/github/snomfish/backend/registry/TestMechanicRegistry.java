package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.mechanics.MechanicRegistry;
import github.snomfish.domain.mechanics.ids.IMechanicId;

public class TestMechanicRegistry {
    

    // this is broken fuck fuck fuck fuck fuck
    @Test
    void mechanicsHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "MechanicRegistry",
            IMechanicId.class,
            MechanicRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "MechanicRegistry",
            IMechanicId.class,
            MechanicRegistry.registry()
        );
    }
}
