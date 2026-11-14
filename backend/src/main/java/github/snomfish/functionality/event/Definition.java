package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

@SuppressWarnings("unchecked")
public abstract class Definition<A extends Enum<A>, B extends Definition<A, B>> {


    protected A id;
    protected String name;
    protected List<Rule> rules;
    protected List<Gate> gates;
    protected List<Hook> hooks;


    public Definition() {}


    // getters
    public A id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Hook> hooks() {return hooks;}


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
    public B hook(Hook hook) {
        this.hooks.add(hook);
        return (B) this;
    }


    public List<Branch<BattleContext>> handleRule(BattleContext context, IRuleId ruleId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));  
        
        for (Rule rule : rules) {  
            outcomes = flatMap(outcomes, c -> rule.execute(c, ruleId));
        }

        return outcomes;
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
