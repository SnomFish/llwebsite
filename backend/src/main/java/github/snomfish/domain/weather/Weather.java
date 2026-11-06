package github.snomfish.domain.weather;

import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;

public class Weather extends Definition<WeatherId> {

    public Weather(WeatherId id) {
        super(id);
    }


    // builder functions
    public static Weather build(WeatherId id) {
        return new Weather(id);
    }
    public Weather name(String name) {
        this.name = name;
        return this;
    }
    public Weather rule(Rule rule) {
        this.rules.add(rule);
        return this;
    }
    public Weather gate(Gate gate) {
        this.gates.add(gate);
        return this;
    }
    public Weather interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }
}
