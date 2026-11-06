package github.snomfish.domain.mechanics;

import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;

public class Mechanic extends Definition<MechanicId>{
    

    public Mechanic(MechanicId id) {
        super(id);
    }


    // builder functions
    public static Mechanic build(MechanicId id) {
        return new Mechanic(id);
    }
    public Mechanic name(String name) {
        this.name = name;
        return this;
    }
    public Mechanic rule(Rule rule) {
        this.rules.add(rule);
        return this;
    }
    public Mechanic gate(Gate gate) {
        this.gates.add(gate);
        return this;
    }
    public Mechanic interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }
}
