package github.snomfish.backend.registry;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.mechanics.MechanicRegistry;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;

public class TestMechanicRegistry {


    private static IMechanicId[] allMechanicIds() {
    return Stream.of(
            FieldMechanicId.values(),
            SideMechanicId.values(),
            LoomianMechanicId.values()
        )
        .flatMap(Arrays::stream)
        .toArray(IMechanicId[]::new);
    }
    

    @Test
    void mechanicsHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "MechanicRegistry",
            allMechanicIds(),
            MechanicRegistry.registry()
        );
    }
    

    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "MechanicRegistry",
            allMechanicIds(),
            MechanicRegistry.registry()
        );
    }
}
