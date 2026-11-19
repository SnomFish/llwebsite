package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.resolveConditions;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class Rule {
     

    private List<IRuleId> events;
    private List<ICondition> conditions;
    private List<IEffect> effects;
    private int priority;


    private Rule() {
        this.events = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.priority = 0;
    }


    public List<IRuleId> events() {return events;}
    public List<ICondition> conditions() {return conditions;}
    public List<IEffect> effects() {return effects;}
    public int priority() {return priority;}


    // builder functions
    public static Rule build() {
        return new Rule();
    }
    public Rule event(IRuleId id) {this.events.add(id); return this;}
    public Rule condition(ICondition condition) {this.conditions.add(condition); return this;}
    public Rule effect(IEffect effect) {this.effects.add(effect); return this;}
    public Rule priority(int priority) {this.priority = priority; return this;}


    public List<Branch<BattleContext>> execute(BattleContext context) {
        return resolveConditions(context, conditions, this::executeEffects);
    }


    public List<Branch<BattleContext>> executeEffects(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        for (IEffect effect : effects) {
            outcomes = flatMap(outcomes, c -> effect.execute(c));
        }

        return outcomes;
    }
}
