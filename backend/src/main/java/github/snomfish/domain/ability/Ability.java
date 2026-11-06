package github.snomfish.domain.ability;

import static github.snomfish.domain.ability.AbilityId.NO_ABILITY;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability {


    private AbilityId id;
    private String name;
    private List<AbilityRule> rules;
    private List<Gate> gates;
    private List<Interceptor> interceptors;

    private boolean isLocked;
    private boolean oncePerBattle;
    private boolean oncePerSwapIn;


    private Ability(
        AbilityId id,
        String name,
        List<AbilityRule> rules,
        List<Gate> gates,
        List<Interceptor> interceptors,
        boolean isLocked,
        boolean oncePerBattle,
        boolean oncePerSwapIn
    ) {
        this.id = id;
        this.name = name;
        this.rules = rules;
        this.gates = gates;
        this.interceptors = interceptors;
        this.isLocked = isLocked;
        this.oncePerBattle = oncePerBattle;
        this.oncePerSwapIn = oncePerSwapIn;
    }


    // getter
    public AbilityId id() {return id;}
    public String name() {return name;}
    public List<AbilityRule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Interceptor> interceptors() {return interceptors;}
    public boolean isLocked() {return isLocked;}
    public boolean oncePerBattle() {return oncePerBattle;}
    public boolean oncePerSwapIn() {return oncePerSwapIn;}


    // builder functions
    public static Ability build() {
        return new Ability(
            NO_ABILITY,
            "no ability",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            false,
            false,
            false
        );
    }
    public Ability id(AbilityId id) {
        this.id = id;
        return this;
    }
    public Ability name(String name) {
        this.name = name;
        return this;
    }
    public Ability rule(AbilityRule rule) {
        this.rules.add(rule);
		return this;
    }
    public Ability gate(Gate gate) {
        this.gates.add(gate);
        return this;
    }
    public Ability interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }
    public Ability setLocked() {
        this.isLocked = true;
        return this;                                      
    }
    public Ability setOncePerBattle() {
        this.oncePerBattle = true;
        return this;
    }
    public Ability setOncePerSwapIn() {
        this.oncePerSwapIn = true;
        return this;
    } 
}
