package github.snomfish.domain.weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.type.TypeId.*;
import static github.snomfish.domain.weather.WeatherId.*;
import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.event.EventSideId.*;

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
            List.of(
                // spirit types get mold breaker on non light types
                // 2 turn moves become one turn
            )
        );
        register(
            HEATWAVE,
            "Heatwave",
            List.of(
                new TriggerRule( // increases the power of fire moves by 30%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, FIRE),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.3))
                    )
                ),
                new TriggerRule( // decreases the power of water moves by 30%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, WATER),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.7))
                    )
                )
                // moves of bug type loomians cost 10% less energy
            )
        );
        register(
            RAIN,
            "Rain",
            List.of(
                new TriggerRule( // increases the power of water moves by 30%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, WATER),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.3))
                    )
                ),
                new TriggerRule( // decreases the power of fire moves by 30%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, FIRE),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.7))
                    )
                )
                // plant types heal 1/16 max hp after each turn
            )
        );
        register(
            STRONG_WINDS,
            "Strong Winds",
            List.of(
                new TriggerRule( // increases the power of toxic moves by 25%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, TOXIC),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25))
                    )
                )
                // increases the speed of air type loomians by 25%
                // nullifies trapping moves
            )
        );
        register(
            THUNDERSTORM,
            "Thunderstorm",
            List.of(
                new TriggerRule( // increases the power of toxic moves by 25%
                    new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
                    new ConditionalEffect(
                        new Equals(Value.MOVE_TYPE, ELECTRIC),
                        new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25))
                    )
                )
                // electric type moves cost 25% less energy
                // air types become grounded and can be hit neutrally by eart types
                // loomians cannot fall asleep, if they are asleep already then immediately wake them up
            )
        );
    }
}
