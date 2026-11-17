package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherRegistry;

public class TestWeatherRegistry {


    @Test
    void weatherHasRegisteredAllEnums() {
        GeneralRegistryTests.hasRegisteredAllEnums(
            "WeatherRegistry",
            WeatherId.values(),
            WeatherRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralRegistryTests.allEntriesAreUnique(
            "WeatherRegistry",
            WeatherId.values(),
            WeatherRegistry.registry()
        );
    }
}
