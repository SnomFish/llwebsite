package github.snomfish.domain.ability;

import github.snomfish.functionality.event.Definition;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability extends Definition<AbilityId, Ability> {


    private boolean isLocked;
    private boolean oncePerBattle;
    private boolean oncePerSwapIn;


    private Ability() {
        super();
        this.isLocked = false;
        this.oncePerBattle = false;
        this.oncePerSwapIn = false;
    }


    // getter
    public boolean isLocked() {return isLocked;}
    public boolean oncePerBattle() {return oncePerBattle;}
    public boolean oncePerSwapIn() {return oncePerSwapIn;}


    // builder functions
    public static Ability build() {
        return new Ability();
    }
    public Ability setLocked() {this.isLocked = true; return this;}
    public Ability setOncePerBattle() {this.oncePerBattle = true; return this;}
    public Ability setOncePerSwapIn() {this.oncePerSwapIn = true; return this;} 
}
