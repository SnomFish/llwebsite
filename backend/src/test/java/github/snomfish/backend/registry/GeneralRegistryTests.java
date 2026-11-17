package github.snomfish.backend.registry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

public class GeneralRegistryTests {
    

    
    public static <T, R> void hasRegisteredAllEnums(
        String registryName,
        T[] enumValues,
        Map<T, R> registry
    ) {
        List<T> missing = Arrays.stream(enumValues)
            .filter(id -> !registry.containsKey(id))
            .toList();

        Assertions.assertTrue(
            missing.isEmpty(),
            "Missing registrations in " + registryName + ": " + missing
        );
    }
    
    
    public static <T, R> void allEntriesAreUnique(
        String registryName,
        T[] enumValues,
        Map<T, R> registry
    ) {
        Map<T, R> uniqueEntries = new java.util.HashMap<>();

        registry.forEach((id, object) -> {
            if (uniqueEntries.containsKey(id)) {
                Assertions.fail(
                    "Duplicate registration in " + registryName + ": " + id
                );
            }

            uniqueEntries.put(id, object);
        });
    }
}
