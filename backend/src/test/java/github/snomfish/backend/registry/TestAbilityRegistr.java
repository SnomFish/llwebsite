package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.ability.AbilityRegistry;

public class TestAbilityRegistr {


    @Test
    void hasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "AbilityRegistry",
            AbilityId.values(),
            AbilityRegistry.registry()
        );
    }


    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "AbilityRegistry",
            AbilityId.values(),
            AbilityRegistry.registry()
        );
    }
}
