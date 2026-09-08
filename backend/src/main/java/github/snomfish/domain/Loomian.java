package github.snomfish.domain;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.species.Species;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.status.Status;
import github.snomfish.functionality.StatCalculator;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.copy.DeepCopyable;

public class Loomian implements DeepCopyable<Loomian> {
    
    private SpeciesId speciesId; // doesnt get deep copied
    private List<MoveId> moves;
    private AbilityId ability;
    private ItemId item;
    private Stats nature;
    private IAction action;

    private Stats actualStats;
    private int currentHealth;
    private int currentEnergy;
    private Stats tps;
    private Stats ups;

    private Status status;

    // eventually have these bools in an enum map
    private boolean hasAbilityActivated;


    public Loomian(
        SpeciesId speciesId,
        List<MoveId> moves,
        AbilityId ability,
        ItemId item,
        Stats nature,
        Stats tps,
        Stats ups,
        Status status
    ) {
        this.speciesId = speciesId;
        this.moves = moves;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
        this.status = status;
        this.action = null;

        this.tps = tps;
        this.ups = ups;
        this.actualStats = StatCalculator.getActualStats(this);
        this.currentHealth = (int) actualStats.health();
        this.currentEnergy = (int) actualStats.energy();

        this.hasAbilityActivated = false;
    }


    protected Loomian(Loomian loomian) {
        this.speciesId = loomian.speciesId;
        this.moves = loomian.moves();
        this.ability = loomian.ability();
        this.item = loomian.item();
        this.nature = loomian.nature();
        this.status = loomian.status();
        this.action = loomian.action();

        this.actualStats = loomian.actualStats();
        this.currentHealth = loomian.currentHealth();
        this.currentEnergy = loomian.currentEnergy();
        this.tps = loomian.tps;
        this.ups = loomian.ups;

        this.hasAbilityActivated = loomian.hasAbilityActivated;
    }


    public Loomian deepCopy() {
        return new Loomian(this);
    }


    // getters
    public SpeciesId speciesId() {return speciesId;}
    public Species species() {return SpeciesRegistry.get(speciesId);} 
    public List<MoveId> moves() {return moves;}
    public AbilityId ability() {return ability;}
    public ItemId item() {return item;}
    public Stats nature() {return nature;}
    public Status status() {return status;}
    public IAction action() {return action;}

    public Stats actualStats() {return actualStats;}
    public int currentHealth() {return currentHealth;}
    public int currentEnergy() {return currentEnergy;}
    public Stats tps() {return tps;}
    public Stats ups() {return ups;}

    public boolean hasAbilityActivated() {return hasAbilityActivated;}


    // setter
    public Builder builder() {
        return new Builder(this);
    }
    public class Builder {

        private final Loomian copy;

        public Builder(Loomian original) {
            copy = original.deepCopy();
        }

        public Builder speciesId(SpeciesId speciesId) {
            copy.speciesId = speciesId;
            return this;
        }
        public Builder moves(List<MoveId> moves) {
            copy.moves = moves;
            return this;
        }
        public Builder ability(AbilityId ability) {
            copy.ability = ability;
            return this;
        }
        public Builder item(ItemId item) {
            copy.item = item;
            return this;
        }
        public Builder nature(Stats nature) {
            copy.nature = nature;
            return this;
        }
        public Builder action(IAction action) {
            copy.action = action;
            return this;
        }
        public Builder actualStats(Stats actualStats) {
            copy.actualStats = actualStats;
            return this;
        }
        public Builder currentHealth(int currentHealth) {
            copy.currentHealth = currentHealth;
            return this;
        }
        public Builder currentEnergy(int currentEnergy) {
            copy.currentEnergy = currentEnergy;
            return this;
        }
        public Builder tps(Stats tps) {
            copy.tps = tps;
            return this;
        }
        public Builder ups(Stats ups) {
            copy.ups = ups;
            return this;
        }
        public Builder status(Status status) {
            copy.status = status;
            return this;
        }
        public Builder hasAbilityActivated(boolean hasAbilityActivated) {
            copy.hasAbilityActivated = hasAbilityActivated;
            return this;
        }

        public Loomian build() {
            return copy;
        }
    }
}
