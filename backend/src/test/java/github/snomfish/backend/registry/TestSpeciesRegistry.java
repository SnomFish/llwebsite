package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;

public class TestSpeciesRegistry {
    

    @Test
    void speciesHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "SpeciesRegistry",
            SpeciesId.class,
            SpeciesRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "SpeciesRegistry",
            SpeciesId.class,
            SpeciesRegistry.registry()
        );
    }
}
