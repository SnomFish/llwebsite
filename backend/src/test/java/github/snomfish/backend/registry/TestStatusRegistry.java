package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.status.StatusRegistry;

public class TestStatusRegistry {
    

    @Test
    void statusHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "StatusRegistry",
            StatusId.class,
            StatusRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "StatusRegistry",
            StatusId.class,
            StatusRegistry.registry()
        );
    }
}
