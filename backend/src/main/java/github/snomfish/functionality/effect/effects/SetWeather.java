package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.event.EventId.*;

public class SetWeather implements IEffect {
    

    private WeatherId weatherId;
    private INumber lifespan;
    private EventId beforeSetEvent;
    private EventId setEvent;


    private SetWeather(
        WeatherId weatherId,
        INumber lifespan,
        EventId beforeSetEvent,
        EventId setEvent
    ) {
        this.weatherId = weatherId;
        this.lifespan = lifespan;
        this.beforeSetEvent = beforeSetEvent;
        this.setEvent = setEvent;
    }
    private SetWeather(SetWeather copy) {
        this.weatherId = copy.weatherId;
        this.lifespan = copy.lifespan.deepCopy();
        this.beforeSetEvent = copy.beforeSetEvent;
        this.setEvent = copy.setEvent;
    }


    public SetWeather deepCopy() {
        return new SetWeather(this);
    }


    public static SetWeather userSetting(WeatherId id, INumber lifespan) {
        return new SetWeather(
            id, lifespan,
            USER_BEFORE_WEATHER_SET_EVENT,
            USER_WEATHER_SET_EVENT
        );
    }
    public static SetWeather targetSetting(WeatherId id, INumber lifespan) {
        return new SetWeather(
            id, lifespan,
            TARGET_BEFORE_WEATHER_SET_EVENT,
            TARGET_WEATHER_SET_EVENT
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));

        if (context.gamestate().weatherId() == weatherId) return outcomes;

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(beforeSetEvent));
        outcomes = flatMap(outcomes, c -> setWeatherModule(context));
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(setEvent));
        
        return outcomes;
    }


    public List<Branch<BattleContext>> setWeatherModule(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Double> lifespanBranch : lifespan.execute(context)) {
            int lifespan = lifespanBranch.value().intValue();
            double probability = lifespanBranch.probability();

            BattleContext outcome = context.deepCopy();
            outcome.gamestate().setWeatherId(weatherId);
            outcome.gamestate().setWeatherLifespan(lifespan);


            outcomes.add(new Branch<>(
                outcome,
                probability
            ));
        }

        return outcomes;
    }
}
