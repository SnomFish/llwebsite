package github.snomfish.domain.mechanics;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Report;
import github.snomfish.functionality.event.Rule;

public class Mechanic {
    

    private MechanicId id;
    private String name;
    private List<Rule> rules;
    private List<Gate> gates;
    private List<Interceptor> interceptors;
    private boolean hasMaxAge;
    private int maxAge;


    public Mechanic() {
        this.id = MechanicId.NO_MECHANIC;
        this.name = "No Mechanic";
        this.rules = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.interceptors = new ArrayList<>();
        this.hasMaxAge = false;
        this.maxAge = 0;
    }


    // getters
    public MechanicId id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Interceptor> interceptors() {return interceptors;}
    public boolean hasMaxAge() {return hasMaxAge;}
    public int maxAge() {return maxAge;}


    // builder functions
    public static Mechanic build() {
        return new Mechanic();
    }
    public Mechanic id(MechanicId id) {this.id = id; return this;}
    public Mechanic name(String name) {this.name = name; return this;}
    public Mechanic rule(Rule rule) {this.rules.add(rule); return this;}
    public Mechanic gate(Gate gate) {this.gates.add(gate); return this;}
    public Mechanic interceptor(Interceptor interceptor) {this.interceptors.add(interceptor); return this;}
    public Mechanic hasMaxAge(boolean hasMaxAge) {this.hasMaxAge = hasMaxAge; return this;}
    public Mechanic maxAge(int maxAge) {this.maxAge = maxAge; return this;}




    public List<Branch<BattleContext>> handleRule(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        
        for (Rule rule : rules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }

        return outcomes;
    }
    public List<Branch<Boolean>> handleGate(BattleContext context, Event event) {
        return null;
    }
    public List<Branch<BattleContext>> handleInterceptor(Report report, Event event) {
        return null;
    }
}
