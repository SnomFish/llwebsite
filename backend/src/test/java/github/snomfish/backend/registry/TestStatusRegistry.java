package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.status.StatusRegistry;

public class TestStatusRegistry {
    

    @Test
    void statusHasRegisteredAllEnums() {
        GeneralTestRegistry.hasRegisteredAllEnums(
            "StatusRegistry",
            StatusId.values(),
            StatusRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralTestRegistry.allEntriesAreUnique(
            "StatusRegistry",
            StatusId.values(),
            StatusRegistry.registry()
        );
    }
}
