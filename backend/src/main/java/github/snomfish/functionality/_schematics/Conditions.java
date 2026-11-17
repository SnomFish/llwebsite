package github.snomfish.functionality._schematics;

import java.util.List;

import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Modulus;

public class Conditions {
    

    private Conditions() {}


    public static void checkProbability(double probability) {
        if (probability < 0 || 1 < probability) throw new RuntimeException("probability reached unacceptable value \"" + probability + "\"");
    }


    public static Double resolve(BattleContext context, List<ICondition> conditions) {
        double probability = 1;
        for (ICondition condition : conditions) {
            probability *= condition.execute(context);
            if (probability == 0) return 0.0; // returning early if the answer is no
        }
        return probability;
    }


    public static ICondition and(ICondition a, ICondition b, ICondition c) {
        return new And(a, new And(b, c));
    }
    public static ICondition or(ICondition a, ICondition b, ICondition c) {
        return new Or(a, new Or(b, c));
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
