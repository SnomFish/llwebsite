package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.ids.IGenericId;
import github.snomfish.functionality.event.ids.IsolatedId;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

public class Rule {
     

    private List<Event> events;
    private ICondition condition;
    private IEffect effect;


    private Rule() {
        this.events = new ArrayList<>();
        this.condition = new NoCondition();
        this.effect = new NoEffect();
    }


    public List<Branch<BattleContext>> execute(BattleContext context, Event event) {
        
        if (!events.contains(event)) {
            return List.of(new Branch<>(context, 1.0));
        }

        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        double probability = condition.execute(context);

        if (probability > 0) {
            outcomes.addAll(applyProbability(effect.execute(context), probability));
        }
        if (probability < 1) {
            outcomes.addAll(applyProbability(effect.execute(context), 1-probability));
        }
        
        return effect.execute(context);
    }


    // builder functions
    public static Rule build() {
        return new Rule();
    }
    public Rule event(IsolatedId ruleId) {
        this.events.add(new Event(ruleId, null));
        return this;
    }
    public Rule event(IGenericId genericId) {
        this.events.add(new Event(genericId, null));
        return this;
    }
    public Rule condition(ICondition condition) {
        this.condition = condition;
        return this;
    }
    public Rule effect(IEffect effect) {
        this.effect = effect;
        return this;
    }
}
