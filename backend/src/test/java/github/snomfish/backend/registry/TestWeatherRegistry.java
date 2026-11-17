package github.snomfish.backend.registry;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherRegistry;

public class TestWeatherRegistry {


    @Test
    void weatherHasRegisteredAllEnums() {
        GeneralTestRegistry.hasRegisteredAllEnums(
            "WeatherRegistry",
            WeatherId.values(),
            WeatherRegistry.registry()
        );
    }

    
    @Test
    void allEntriesAreUnique() {
        GeneralTestRegistry.allEntriesAreUnique(
            "WeatherRegistry",
            WeatherId.values(),
            WeatherRegistry.registry()
        );
    }
}
