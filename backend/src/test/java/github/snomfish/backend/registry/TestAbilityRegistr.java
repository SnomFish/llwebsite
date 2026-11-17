package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.ability.AbilityRegistry;

public class TestAbilityRegistr {


    @Test
    void hasRegisteredAllEnums() {
        GeneralTestRegistry.hasRegisteredAllEnums(
            "AbilityRegistry",
            AbilityId.values(),
            AbilityRegistry.registry()
        );
    }


    @Test
    void allEntriesAreUnique() {
        GeneralTestRegistry.allEntriesAreUnique(
            "AbilityRegistry",
            AbilityId.values(),
            AbilityRegistry.registry()
        );
    }
}
