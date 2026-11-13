package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;

public class Definition<T extends Enum<T>, D extends Definition<T, D>> {
  
    
    protected T id;
    protected String name;
    protected final List<Rule> rules;
    protected final List<Gate> gates;
    protected final List<Interceptor> interceptors;


    protected Definition() {
        this.id = null;
        this.name = "no definition";
        this.rules = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.interceptors = new ArrayList<>();
    }


    // getter
    public T id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Interceptor> interceptors() {return interceptors;}


    // builder functions
    public static <T extends Enum<T>, D extends Definition<T, D>> Definition<T, D> build() {
        return new Definition<>();
    }
    @SuppressWarnings("unchecked")
    public D id(T id) {
        this.id = id;
        return (D) this;
    }
    @SuppressWarnings("unchecked")
    public D name(String name) {
        this.name = name;
        return (D) this;
    }
    @SuppressWarnings("unchecked")
    public D rule(Rule rule) {
        this.rules.add(rule);
        return (D) this;
    }
    @SuppressWarnings("unchecked")
    public D gate(Gate gate) {
        this.gates.add(gate);
        return (D) this;
    }
    @SuppressWarnings("unchecked")
    public D interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return (D) this;
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        
        for (Rule rule : rules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }

        return outcomes;
    }
    public double handleGate(BattleContext context, IGateId gateId) {
        double probability = 1;

        for (Gate gate : gates) {
            probability *= gate.condition().execute(context);
        }

        return probability;
    }
    public Report handleInterceptor(Report report, Event event) {
        // FUCK
        return report;
    }
}
