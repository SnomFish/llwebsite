package github.snomfish.domain.ability;

import java.util.List;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability {


    private final AbilityId id;
    private final String name;
    private final List<AbilityRule> rules;

    private final boolean oncePerBattle;
    private final boolean oncePerSwapIn;


    public Ability(
        AbilityId id,
        String name,
        List<AbilityRule> rules,
        boolean oncePerBattle,
        boolean oncePerSwapIn
    ) {
        this.id = id;
        this.name = name;
        this.rules = rules;
        this.oncePerBattle = oncePerBattle;
        this.oncePerSwapIn = oncePerSwapIn;
    }


    // getter
    public AbilityId id() {return id;}
    public String name() {return name;}
    public List<AbilityRule> rules() {return rules;}
    public boolean oncePerBattle() {return oncePerBattle;}
    public boolean oncePerSwapIn() {return oncePerSwapIn;}
}
