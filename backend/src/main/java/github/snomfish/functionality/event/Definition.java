package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;

public class Definition<T extends Enum<T>> {
  
    
    protected final T id;
    protected String name;
    protected final List<Rule> rules;
    protected final List<Gate> gates;
    protected final List<Interceptor> interceptors;


    protected Definition(T id) {
        this.id = id;
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


    public List<Branch<BattleContext>> handleRule(BattleContext context, Event<RuleId> event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        
        for (Rule rule : rules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }

        return outcomes;
    }
    public List<Branch<Boolean>> handleGate(BattleContext context, Event<GateId> event) {
        return null;
    }
    public List<Branch<BattleContext>> handleInterceptor(InterceptorReport report, Event<InterceptorId> event) {
        return null;
    }
}
