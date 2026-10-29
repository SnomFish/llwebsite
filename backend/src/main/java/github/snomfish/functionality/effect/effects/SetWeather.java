package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class SetWeather implements IEffect {
    

    private WeatherId weatherId;
    private INumber lifespan;


    public SetWeather(
        WeatherId weatherId,
        INumber lifespan
    ) {
        this.weatherId = weatherId;
        this.lifespan = lifespan;
    }
    private SetWeather(SetWeather copy) {
        this.weatherId = copy.weatherId;
        this.lifespan = copy.lifespan.deepCopy();
    }


    public SetWeather deepCopy() {
        return new SetWeather(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        // exits early if the weather is the same as the one trying to be set
        if (Value.WEATHER_ID.get(context) == weatherId) return List.of(new Branch<>(context, 1));

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
