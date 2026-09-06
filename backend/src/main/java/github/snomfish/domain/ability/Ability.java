package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

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

    
    public Ability(
        AbilityId id,
        String name,
        AbilityRule abilityRule
    ) {
        this.id = id;
        this.name = name;
        this.abilityRules.add(abilityRule);
    }
}
