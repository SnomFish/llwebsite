package github.snomfish.domain.ability;

import github.snomfish.domain.tags.AbilityTagId;
import github.snomfish.domain.tags.Tags;
import github.snomfish.functionality.event.Definition;

// ability is only ever called from the registry, therefore it should remain constant
// same with item
public class Ability extends Definition<AbilityId, Ability> {


    private boolean isLocked;
    private boolean oncePerBattle;
    private boolean oncePerSwapIn;
    private Tags<AbilityTagId> tags;


    private Ability() {
        super();
        this.isLocked = false;
        this.oncePerBattle = false;
        this.oncePerSwapIn = false;
        this.tags = new Tags<>();
    }


    // getter
    public boolean isLocked() {return isLocked;}
    public boolean oncePerBattle() {return oncePerBattle;}
    public boolean oncePerSwapIn() {return oncePerSwapIn;}
    public Tags<AbilityTagId> tags() {return tags;}


    // builder functions
    public static Ability build() {
        return new Ability();
    }
    public Ability setLocked() {this.isLocked = true; return this;}
    public Ability setOncePerBattle() {this.oncePerBattle = true; return this;}
    public Ability setOncePerSwapIn() {this.oncePerSwapIn = true; return this;}
    public Ability tag(AbilityTagId tag) {this.tags.add(tag); return this;} 
}
