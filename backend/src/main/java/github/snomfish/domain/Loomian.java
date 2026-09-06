package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ability.IAbility;
import github.snomfish.domain.item.IItem;
import github.snomfish.domain.move.IMove;
import github.snomfish.domain.species.Species;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.species.SpeciesRegistry;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.status.Status;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.StatCalculator;
import github.snomfish.functionality.copy.DeepCopyable;

public class Loomian implements DeepCopyable<Loomian> {
    
    private final SpeciesId speciesId; // doesnt get deep copied
    private final List<TypeId> types;
    //private final List<IMove> moves;
    private final IAbility ability;
    private final IItem item;
    private final Stats nature;

    private final Stats actualStats;
    private final Stats battleStats;
    private final int currentHealth;
    private final int currentEnergy;
    private final Stats tps;
    private final Stats ups;

    private final Status status;

    // eventually have these bools in an enum map
    private final boolean hasAbilityActivated;


    public Loomian(
        SpeciesId speciesId,
        //List<IMove> moves,
        IAbility ability,
        IItem item,
        Stats nature,
        Stats tps,
        Stats ups,
        Status status
    ) {
        this.speciesId = speciesId;
        this.types = new ArrayList<>(species().baseTypes()); // List<TypeId>
        //this.moves = moves;
        this.ability = ability;
        this.item = item;
        this.nature = nature;

        this.actualStats = StatCalculator.getActualStats(this);
        this.battleStats = actualStats.deepCopy();
        this.currentHealth = (int) actualStats.getHealth();
        this.currentEnergy = (int) actualStats.getEnergy();
        this.tps = tps;
        this.ups = ups;

        this.status = status;

        this.hasAbilityActivated = false;
    }


    public Loomian deepCopy() {
        return new Loomian(
            speciesId,
            ability,
            item,
            nature.deepCopy(),
            tps.deepCopy(),
            ups.deepCopy(),
            status.deepCopy()
        );
    }


    // getters
    public SpeciesId speciesId() {return speciesId;}
    public Species species() {return SpeciesRegistry.get(speciesId);} 
    public List<TypeId> types() {return types;}
    public IAbility ability() {return ability;}
    public IItem item() {return item;}
    public Stats nature() {return nature;}

    public Stats actualStats() {return actualStats;}
    public Stats battleStats() {return battleStats;}
    public int currentHealth() {return currentHealth;}
    public int currentEnergy() {return currentEnergy;}
    public Stats tps() {return tps;}
    public Stats ups() {return ups;}

    public Status status() {return status;}

    public boolean hasAbilityActivated() {return hasAbilityActivated;}

}
