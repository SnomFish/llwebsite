package github.snomfish.backend.registry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.ability.AbilityRegistry;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.item.ItemRegistry;
import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.mechanics.MechanicRegistry;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.move.MoveRegistry;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.status.StatusRegistry;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherRegistry;

public class TestRegistry {
    

    
    private <T extends Enum<T>, R> void hasRegisteredAllEnums(
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
    
    
    private <T extends Enum<T>, R> void allEntriesAreUnique(
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


    @Test
    void abilityHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"AbilityRegistry",    AbilityId.class,    AbilityRegistry.registry());
        allEntriesAreUnique(	"AbilityRegistry",    AbilityId.class,    AbilityRegistry.registry());
    }
    @Test
    void itemHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"ItemRegistry",       ItemId.class,       ItemRegistry.registry());
        allEntriesAreUnique(	"ItemRegistry",       ItemId.class,       ItemRegistry.registry());
    }
    @Test
    void mechanicsHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"MechanicRegistry",   MechanicId.class,   MechanicRegistry.registry());
        allEntriesAreUnique(	"MechanicRegistry",   MechanicId.class,   MechanicRegistry.registry());
    }
    @Test
    void moveHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"MoveRegistry",       MoveId.class,       MoveRegistry.registry());
        allEntriesAreUnique(	"MoveRegistry",       MoveId.class,       MoveRegistry.registry());
    }
    @Test
    void speciesHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"SpeciesRegistry",    SpeciesId.class,    SpeciesRegistry.registry());
        allEntriesAreUnique(	"SpeciesRegistry",    SpeciesId.class,    SpeciesRegistry.registry());
    }
    @Test
    void statusHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"StatusRegistry",     StatusId.class,     StatusRegistry.registry());
        allEntriesAreUnique(	"StatusRegistry",     StatusId.class,     StatusRegistry.registry());
    }
    @Test
    void weatherHasRegisteredAllEnums() {
        hasRegisteredAllEnums(	"WeatherRegistry",    WeatherId.class,    WeatherRegistry.registry());
        allEntriesAreUnique(	"WeatherRegistry",    WeatherId.class,    WeatherRegistry.registry());
    }
}
