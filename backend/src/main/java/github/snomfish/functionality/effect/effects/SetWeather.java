package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class SetWeather implements IEffect {
    

    private WeatherId weatherId;
    private INumber lifespan;
    private GenericId postSetEvent;


    private SetWeather(
        WeatherId weatherId,
        INumber lifespan,
        GenericId postSetEvent
    ) {
        this.weatherId = weatherId;
        this.lifespan = lifespan;
        this.postSetEvent = postSetEvent;
    }
    private SetWeather(SetWeather copy) {
        this.weatherId = copy.weatherId;
        this.lifespan = copy.lifespan.deepCopy();
        this.postSetEvent = copy.postSetEvent;
    }


    public SetWeather deepCopy() {
        return new SetWeather(this);
    }


    public static SetWeather userSetting(WeatherId id, INumber lifespan) {
        return new SetWeather(
            id, lifespan,
            GenericId.SELF_SET_WEATHER
        );
    }
    /*public static SetWeather targetSetting(WeatherId id, INumber lifespan) {
        return new SetWeather(
            id, lifespan,
            POST_TARGET_SET_WEATHER_EVENT
        );
    }*/


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1));

        if (context.gamestate().weatherInstance().weatherId() == weatherId) return outcomes;

        outcomes = flatMap(outcomes, c -> setWeatherModule(context));
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(postSetEvent, ContextSideId.USER)); // i think this technically activates any non Side events twice as LOOMIAN_SET_WEATHER is the isolated event stored in postSetEvent
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(IsolatedId.LOOMIAN_SET_WEATHER));
        
        return outcomes;
    }


    public List<Branch<BattleContext>> setWeatherModule(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Double> lifespanBranch : lifespan.execute(context)) {
            int lifespan = lifespanBranch.value().intValue();
            double probability = lifespanBranch.probability();

            BattleContext outcome = context.deepCopy();
            outcome.gamestate().weatherInstance().setWeatherId(weatherId);
            outcome.gamestate().weatherInstance().setLifespan(lifespan);


            outcomes.add(new Branch<>(
                outcome,
                probability
            ));
        }

        return outcomes;
    }
}
