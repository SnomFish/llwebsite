package github.snomfish.domain;

import java.util.List;

import github.snomfish.domain.ability.AbilityInstance;
import github.snomfish.domain.item.Item;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.item.ItemRegistry;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.species.Species;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.status.Status;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.status.StatusRegistry;
import github.snomfish.functionality.Calculator;
import github.snomfish.functionality.copy.DeepCopyable;

public class Loomian implements DeepCopyable<Loomian> {
    
    private SpeciesId speciesId; // doesnt get deep copied
    private List<MoveId> moves;
    private AbilityInstance actualAbilityInstance;
    private ItemId itemId;
    private StatusId statusId;
    private Stats nature;

    private Stats actualStats;
    private int currentHealth;
    private int currentEnergy;
    private Stats tps;
    private Stats ups;

    // eventually have these bools in an enum map
    private boolean hasAbilityActivated;
    private boolean canHaveItemRemoved;


    public Loomian(
        SpeciesId speciesId,
        List<MoveId> moves,
        AbilityInstance actualAbilityInstance,
        ItemId itemId,
        StatusId statusId,
        Stats nature,
        Stats tps,
        Stats ups
    ) {
        this.speciesId = speciesId;
        this.moves = moves;
        this.actualAbilityInstance = actualAbilityInstance;
        this.itemId = itemId;
        this.statusId = statusId;
        this.nature = nature;

        this.tps = tps;
        this.ups = ups;
        this.actualStats = Calculator.getActualStats(this);
        this.currentHealth = (int) actualStats.health();
        this.currentEnergy = (int) actualStats.energy();

        this.hasAbilityActivated = false;
        this.canHaveItemRemoved = false;
    }


    protected Loomian(Loomian loomian) {
        this.speciesId = loomian.speciesId;
        this.moves = loomian.moves;
        this.actualAbilityInstance = loomian.actualAbilityInstance;
        this.itemId = loomian.itemId;
        this.statusId = loomian.statusId;
        this.nature = loomian.nature;

        this.actualStats = loomian.actualStats;
        this.currentHealth = loomian.currentHealth;
        this.currentEnergy = loomian.currentEnergy;
        this.tps = loomian.tps;
        this.ups = loomian.ups;

        this.hasAbilityActivated = loomian.hasAbilityActivated;
        canHaveItemRemoved = loomian.canHaveItemRemoved;
    }


    public Loomian deepCopy() {
        return new Loomian(this);
    }


    // getters
    public SpeciesId speciesId() {return speciesId;}
    public Species species() {return SpeciesRegistry.get(speciesId);} 
    public List<MoveId> moves() {return moves;}
    public AbilityInstance actualAbilityInstance() {return actualAbilityInstance;}
    public ItemId itemId() {return itemId;}
    public Item item() {return ItemRegistry.get(itemId);}
    public StatusId statusId() {return statusId;}
    public Status status() {return StatusRegistry.get(statusId);}
    public Stats nature() {return nature;}

    public Stats actualStats() {return actualStats;}
    public int maxHealth() {return (int) actualStats.health();}
    public int maxEnergy() {return (int) actualStats.energy();}
    public int currentHealth() {return currentHealth;}
    public int currentEnergy() {return currentEnergy;}
    public Stats tps() {return tps;}
    public Stats ups() {return ups;}

    public boolean hasAbilityActivated() {return hasAbilityActivated;}
    public boolean canHaveItemRemoved() {return canHaveItemRemoved;}


    // setter
    public void setSpeciesId(SpeciesId speciesId) {this.speciesId = speciesId;}
    public void setMoves(List<MoveId> moves) {this.moves = moves;}
    public void setActualAbilityId(AbilityInstance actualAbilityInstance) {this.actualAbilityInstance = actualAbilityInstance;}
    public void setItemId(ItemId itemId) {this.itemId = itemId;}
    public void setStatusId(StatusId statusId) {this.statusId = statusId;} // this does not reset status turns
    public void setNature(Stats nature) {this.nature = nature;}

    public void setActualStats(Stats actualStats) {this.actualStats = actualStats;}
    public void setCurrentHealth(int currentHealth) {this.currentHealth = currentHealth;}
    public void setCurrentEnergy(int currentEnergy) {this.currentEnergy = currentEnergy;}
    public void setTps(Stats tps) {this.tps = tps;}
    public void setUps(Stats ups) {this.ups = ups;}

    public void setHasAbilityActivated(boolean hasAbilityActivated) {this.hasAbilityActivated = hasAbilityActivated;}
    public void setCanHaveItemRemoved(boolean canHaveItemRemoved) {this.canHaveItemRemoved = canHaveItemRemoved;}


    // adders
    public void addToCurrentHealth(int delta) {this.currentHealth += delta;}
    public void addToCurrentEnergy(int delta) {this.currentEnergy += delta;}
}
