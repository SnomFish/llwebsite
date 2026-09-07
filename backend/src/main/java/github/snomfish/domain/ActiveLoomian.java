package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;

public class ActiveLoomian extends Loomian {
    

    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;
    

    public ActiveLoomian(Loomian loomian) {
        super(loomian);

        this.types = new ArrayList<>(species().baseTypes());
        this.battleStats = actualStats().deepCopy();
        this.statChanges = new StatChanges();
    }


    // getter;
    public List<TypeId> types() {return types;}
    public Stats battleStats() {return battleStats;}
    public StatChanges statChanges() {return statChanges;}


    // setter
    public void setTypes(List<TypeId> types) {this.types = types;}
    public void setBattleStats(Stats battleStats) {this.battleStats = battleStats;}
    public void setStatChanges(StatChanges statChanges) {this.statChanges = statChanges;} // probably will never be used, may need to set up a Stat changes class that extends Stats


    @Override 
    public void setSpeciesId(SpeciesId speciesId) {
        this.speciesId = speciesId;
        this.types = new ArrayList<>(species().baseTypes());
    }
}
