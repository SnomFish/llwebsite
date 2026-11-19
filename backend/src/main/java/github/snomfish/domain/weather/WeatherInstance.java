package github.snomfish.domain.weather;

import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.RuleReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

public class WeatherInstance implements DeepCopyable<WeatherInstance> {
    

    private WeatherId id;
    private int lifespan;


    public WeatherInstance(
        WeatherId id,
        int lifespan
    ) {
        this.id = id;
        this.lifespan = lifespan;
    }
    public WeatherInstance(WeatherInstance copy) {
        this.id = copy.id;
        this.lifespan = copy.lifespan;
    }


    public WeatherInstance deepCopy() {
        return new WeatherInstance(this);
    }


    // getter
    public WeatherId weatherId() {return id;}
    public Weather weather() {return WeatherRegistry.get(id);}
    public int lifespan() {return lifespan;}


    // setter
    public void setWeatherId(WeatherId id) {this.id = id;}
    public void setLifespan(int lifespan) {this.lifespan = lifespan;}


    public void dispatchRule(RuleReport report, IRuleId ruleId) {
        report.record(weather().rules(), ruleId);
    }
    public double dispatchGate(BattleContext context, IGateId gateId) {
        return weather().handleGate(context, gateId);
    }
}
