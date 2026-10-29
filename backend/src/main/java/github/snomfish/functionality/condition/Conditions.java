package github.snomfish.functionality.condition;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;

public class Conditions {
    

    private Conditions() {}


    public static ICondition clearWeather() {
        return new Equals(Value.WEATHER_ID, WeatherId.NO_WEATHER);
    }
    public static ICondition underFog() {
        return new Equals(Value.WEATHER_ID, WeatherId.FOG);
    }
    public static ICondition underHeatwave() {
        return new Equals(Value.WEATHER_ID, WeatherId.HEATWAVE);
    }
    public static ICondition underStrongWinds() {
        return new Equals(Value.WEATHER_ID, WeatherId.STRONG_WINDS);
    }
    public static ICondition underRain() {
        return new Equals(Value.WEATHER_ID, WeatherId.RAIN);
    }
    public static ICondition underThunderstorm() {
        return new Equals(Value.WEATHER_ID, WeatherId.THUNDERSTORM);
    }
}
