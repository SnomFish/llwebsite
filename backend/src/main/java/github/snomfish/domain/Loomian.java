package github.snomfish.domain;

import java.util.List;

import github.snomfish.domain.ability.IAbility;
import github.snomfish.domain.item.IItem;
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
    
    protected SpeciesId speciesId; // doesnt get deep copied
    private List<MoveId> moves;
    private IAbility ability;
    private IItem item;
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
        IAbility ability,
        IItem item,
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

        this.actualStats = StatCalculator.getActualStats(this);
        this.currentHealth = (int) actualStats.health();
        this.currentEnergy = (int) actualStats.energy();
        this.tps = tps;
        this.ups = ups;

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
    public IAbility ability() {return ability;}
    public IItem item() {return item;}
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
    public void setSpeciesId(SpeciesId speciesId) {this.speciesId = speciesId;} // activeLoomian overrides this to reset current types, there may be some weird behaviour between imposter and type changing moves
    public void setMoves(List<MoveId> moves) {this.moves = moves;}
    public void setAbility(IAbility ability) {this.ability = ability;}
    public void setItem(IItem item) {this.item = item;}
    public void setNature(Stats nature) {this.nature = nature;}
    public void setStatus(Status status) {this.status = status;}
    public void setAction(IAction action) {this.action = action;}

    public void setActualStats(Stats actualStats) {this.actualStats = actualStats;}
    public void setCurrentHealth(int currentHealth) {this.currentHealth = currentHealth;}
    public void setCurrentEnergy(int currentEnergy) {this.currentEnergy = currentEnergy;}
    public void setTps(Stats tps) {this.tps = tps;}
    public void usetUps(Stats ups) {this.ups = ups;}

    public void setHasAbilityActivated(boolean hasAbilityActivated) {this.hasAbilityActivated = hasAbilityActivated;} 
}
