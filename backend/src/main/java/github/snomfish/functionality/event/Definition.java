package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

@SuppressWarnings("unchecked")
public abstract class Definition<A, B extends Definition<A, B>> {


    protected A id;
    protected String name = "definition";
    protected List<Rule> rules = new ArrayList<>();
    protected List<Hook> hooks = new ArrayList<>();
    protected List<Gate> gates = new ArrayList<>();
    protected List<Door> doors = new ArrayList<>();


    // getters
    public A id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Hook> hooks() {return hooks;}
    public List<Gate> gates() {return gates;}
    public List<Door> doors() {return doors;}


    // builder functions
    public B id(A id) {
        this.id = id;
        return (B) this;
    }
    public B name(String name) {
        this.name = name;
        return (B) this;
    }
    public B rule(Rule rule) {
        this.rules.add(rule);
        return (B) this;
    }
    public B hook(Hook hook) {
        this.hooks.add(hook);
        return (B) this;
    }
    public B gate(Gate gate) {
        this.gates.add(gate);
        return (B) this;
    }
    public B door(Door door) {
        this.doors.add(door);
        return (B) this;
    }


    public double handleGate(BattleContext context, IGateId gateId) {
        double probability = 1;

        for (Gate gate : gates) {
            probability *= gate.execute(context, gateId);
        }
        
        return probability;
    }
    public HookReport handleHook(BattleContext context, IRuleId ruleId, HookReport report) {

        report.record(hooks, ruleId);

        return report;
    }
}
