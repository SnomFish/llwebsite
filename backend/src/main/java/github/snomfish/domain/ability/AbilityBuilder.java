package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.List;

public class AbilityBuilder {
    

    private AbilityId id;
    private String name;
    private List<AbilityRule> rules;

    private boolean isLocked;
    private boolean oncePerBattle;
    private boolean oncePerSwapIn;


    private AbilityBuilder() {
        this.id = AbilityId.NO_ABILITY;
        this.name = "No Ability";
        this.rules = new ArrayList<>();
        this.isLocked = false;
        this.oncePerBattle = false;
        this.oncePerSwapIn = false;
    }


    public static AbilityBuilder newAbility() {
        return new AbilityBuilder();
    }
    public Ability build() {
        return new Ability(
            id,
            name,
            rules,
            isLocked,
            oncePerBattle,
            oncePerSwapIn
        );
    }


    public AbilityBuilder id(AbilityId id) {
        this.id = id;
        return this;
    }
    public AbilityBuilder name(String name) {
        this.name = name;
        return this;
    }
    public AbilityBuilder rule(AbilityRule rule) {
        this.rules.add(rule);
		return this;
    }
    public AbilityBuilder isLocked() {
        this.isLocked = true;
        return this;                                      
    }
    public AbilityBuilder oncePerBattle() {
        this.oncePerBattle = true;
        return this;
    }
    public AbilityBuilder oncePerSwapIn() {
        this.oncePerSwapIn = true;
        return this;
    }
}
