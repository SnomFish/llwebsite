package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.EventId;

public class Ability implements IAbility {

    private AbilityId id;
    private String name;
    private List<AbilityRule> abilityRules = new ArrayList<>();


    public Ability(
        AbilityId id,
        String name,
        List<AbilityRule> abilityRules
    ) {
        this.id = id;
        this.name = name;
        this.abilityRules = abilityRules;
    }


    @Override
    public List<Branch<BattleContext>> handleEvent(BattleContext context, EventId event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        for (AbilityRule rule : abilityRules) {
            outcomes = BranchUtil.flatMap(outcomes, branchContext -> rule.execute(context, event));
        }
        return outcomes;
    }
}
