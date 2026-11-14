package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.*;

@SuppressWarnings("unchecked")
public abstract class Definition<A extends Enum<A>, B extends Definition<A, B>> {


    private A id;
    private String name;
    private List<Rule> rules;
    private List<Gate> gates;
    private List<Interceptor> interceptors;


    public Definition() {}


    // getters
    public A id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Interceptor> interceptors() {return interceptors;}


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
    public B gate(Gate gate) {
        this.gates.add(gate);
        return (B) this;
    }
    public B interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return (B) this;
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, IRuleId ruleId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));  
        for (Rule rule : rules) {
            outcomes = flatMap(outcomes, branchContext -> rule.execute(context, ruleId));
        }
        return outcomes;
    }
    public double handleGate(BattleContext context, IGateId gateId) {
        return 1;
    }
    public Report handleInterceptor(BattleContext context, IRuleId ruleId, Report report) {
        return null;
    }
}
