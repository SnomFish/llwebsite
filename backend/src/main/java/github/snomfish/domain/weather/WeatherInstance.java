package github.snomfish.domain.weather;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.RuleId;

public class WeatherInstance implements DeepCopyable<WeatherInstance> {
    

    private WeatherId id;
    private int lifespan;
    private int lifespanModifier;


    public WeatherInstance(
        WeatherId id,
        int lifespan,
        int lifespanModifier
    ) {
        this.id = id;
        this.lifespan = lifespan;
        this.lifespanModifier = lifespanModifier;
    }
    public WeatherInstance(WeatherInstance copy) {
        this.id = copy.id;
        this.lifespan = copy.lifespan;
        this.lifespanModifier = copy.lifespanModifier;
    }


    public WeatherInstance deepCopy() {
        return new WeatherInstance(this);
    }


    // getter
    public WeatherId weatherId() {return id;}
    public Weather weather() {return WeatherRegistry.get(id);}
    public int lifespan() {return lifespan;}
    public int lifespanModifier() {return lifespanModifier;}


    // setter
    public void setWeatherId(WeatherId id) {this.id = id;}
    public void setLifespan(int lifespan) {this.lifespan = lifespan;}
    public void setLifespanModifier(int lifespanModifier) {this.lifespanModifier = lifespanModifier;}


    public List<Branch<BattleContext>> handleEvent(BattleContext context, Event<RuleId> event) {
        return weather().handleRule(context, event);
    }
}
