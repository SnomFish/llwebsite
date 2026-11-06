package github.snomfish.domain.status;

import java.util.List;

import github.snomfish.domain.weather.Weather;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;

public class Status extends Definition<StatusId> {
    
    public Status(StatusId id) {
        super(id);
    }


    // builder functions
    public static Status build(StatusId id) {
        return new Status(id);
    }
    public Status name(String name) {
        this.name = name;
        return this;
    }
    public Status rule(Rule rule) {
        this.rules.add(rule);
        return this;
    }
    public Status gate(Gate gate) {
        this.gates.add(gate);
        return this;
    }
    public Status interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }
}
