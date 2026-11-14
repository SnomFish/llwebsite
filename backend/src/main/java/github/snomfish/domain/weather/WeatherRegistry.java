package github.snomfish.domain.weather;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.weather.WeatherId.*;

public class WeatherRegistry {
    

    private static final Map<WeatherId, Weather> registry = new HashMap<>();


    private WeatherRegistry() {}


    public static Map<WeatherId, Weather> registry() {return registry;}
    public static Weather get(WeatherId id) {return registry.get(id);}


    private static void register(Weather weather) {
		if (registry.containsKey(weather.id())) throw new RuntimeException("WeatherRegistry attempted to register duplicate entry \"" + weather.id().name() + "\"");
        registry.put(weather.id(), weather);
    }

    // ISOLATED EVENTS USE THE CURRENT BATTLE CONTEXT, AND AS DEAL_FORMULA_DAMAGE ALWAYS HAS THE USER AS THE ATTACKER, THE USER IS ALWAYS THE ATTACKER HERE
    static {
        register(Weather.build()
			.id(NO_WEATHER)
            .name("No Weather")
        );
        register(Weather.build()
			.id(DUST_STORM)
            .name("Dust Storm")
        );
        register(Weather.build()
			.id(FOG)
            .name("Fog")
            // spirit types get mold breaker on non light types
            // 2 turn moves become one turn
        );
        register(Weather.build()
			.id(HEATWAVE)
            .name("Heatwave")
            .rule(Rule.build() // increases the power of fire moves by 30%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.3)))
            )
            .rule(Rule.build() // decreases the power of water moves by 30%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.WATER))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.7)))
            )
            // moves of bug type loomians cost 10% less energy
        );
        register(Weather.build()
			.id(RAIN)
            .name("Rain")
            .rule(Rule.build() // increases the power of water moves by 30%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.3)))
            )
            .rule(Rule.build() // decreases the power of fire moves by 30%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.7)))
            )
            // plant types heal 1/16 max hp after each turn
        );
        register(Weather.build()
			.id(SNOWSTORM)
            .name("Snow Storm")
        );
        register(Weather.build()
			.id(STRONG_WINDS)
            .name("Strong Winds")
            .rule(Rule.build() // increases the power of toxic moves by 25%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
            )
            // increases the speed of air type loomians by 25%
            // nullifies trapping moves
        );
        register(Weather.build()
			.id(THUNDERSTORM)
            .name("Thunderstorm")
            .rule(Rule.build() // increases the power of toxic moves by 25%
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
            )
            // electric type moves cost 25% less energy
            // air types become grounded and can be hit neutrally by eart types
            // loomians cannot fall asleep, if they are asleep already then immediately wake them up
        );
    }
}
