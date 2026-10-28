package github.snomfish.domain.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.event.TriggerRule;

import static github.snomfish.domain.species.SpeciesId.STRICICLE;
import static github.snomfish.domain.weather.WeatherId.*;

public class WeatherRegistry {
    

    private static final Map<WeatherId, Weather> registry = new HashMap<>();


    private WeatherRegistry() {}


    public static Weather get(WeatherId id) {
        return registry.get(id);
    }


    private static void register(
        WeatherId id,
        String name,
        List<TriggerRule> rules
    ) {
        registry.put(id, new Weather(id, name, rules));
    }


    static {
        register(
            NO_WEATHER,
            "No Weather",
            List.of()
        );
        register(
            FOG,
            "Fog",
            List.of()
        );
        register(
            HEATWAVE,
            "Heatwave",
            List.of()
        );
        register(
            RAIN,
            "Rain",
            List.of()
        );
        register(
            STRONG_WINDS,
            "Strong Winds",
            List.of()
        );
        register(
            THUNDERSTORM,
            "Thunderstorm",
            List.of()
        );
    }
}
