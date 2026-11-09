package github.snomfish.backend.registry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

public class GeneralRegistryTests {
    

    
    public static <T extends Enum<T>, R> void hasRegisteredAllEnums(
        String registryName,
        Class<T> enumClass,
        Map<T, R> registry
    ) {
        List<T> missing = Arrays.stream(enumClass.getEnumConstants())
            .filter(id -> !registry.containsKey(id))
            .toList();

        Assertions.assertTrue(
            missing.isEmpty(),
            "Missing registrations in " + registryName + ": " + missing
        );
    }
    
    
    public static <T extends Enum<T>, R> void allEntriesAreUnique(
        String registryName,
        Class<T> enumClass,
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
