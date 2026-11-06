package github.snomfish.functionality.condition;

import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Modulus;

public class Conditions {
    

    private Conditions() {}


    public static ICondition and(ICondition a, ICondition b, ICondition c) {
        return new And(a, new And(b, c));
    }


    public static ICondition isEven(INumber number) {
        return new Equals<>(new Modulus(number, 2), new Constant(1));
    }
    public static ICondition isOdd(INumber number) {
        return new Equals<>(new Modulus(number, 2), new Constant(1));
    } 


    public static ICondition clearWeather() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.NO_WEATHER);
    }
    public static ICondition fogActive() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.FOG);
    }
    public static ICondition heatwaveActive() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE);
    }
    public static ICondition strongWindsActive() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS);
    }
    public static ICondition rainActive() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN);
    }
    public static ICondition thunderstormActive() {
        return new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM);
    }


    public static ICondition userHasStatusAilment() {
        return new Not(new Equals<>(Value.USER().STATUS_ID(), StatusId.NO_STATUS));
    }
    public static ICondition targetHasStatusAilment() {
        return new Not(new Equals<>(Value.TARGET().STATUS_ID(), StatusId.NO_STATUS));
    }


    public static ICondition userIsAtFullHealth() {
        return new Equals<>(Value.USER().CURRENT_HEALTH(), Value.USER().HEALTH());
    }
    public static ICondition targetIsAtFullHealth() {
        return new Equals<>(Value.TARGET().CURRENT_HEALTH(), Value.TARGET().HEALTH());
    }
}
