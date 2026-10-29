package github.snomfish.functionality.condition;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;

public class Conditions {
    

    private Conditions() {}


    public static ICondition clearWeather() {
        return new Equals(Value.WEATHER_ID, WeatherId.NO_WEATHER);
    }
    public static ICondition fogActive() {
        return new Equals(Value.WEATHER_ID, WeatherId.FOG);
    }
    public static ICondition heatwaveActive() {
        return new Equals(Value.WEATHER_ID, WeatherId.HEATWAVE);
    }
    public static ICondition strongWindsActive() {
        return new Equals(Value.WEATHER_ID, WeatherId.STRONG_WINDS);
    }
    public static ICondition rainActive() {
        return new Equals(Value.WEATHER_ID, WeatherId.RAIN);
    }
    public static ICondition thunderstormActive() {
        return new Equals(Value.WEATHER_ID, WeatherId.THUNDERSTORM);
    }
}
