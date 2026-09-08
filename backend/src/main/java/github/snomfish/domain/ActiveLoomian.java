package github.snomfish.domain;

import java.util.List;

import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;

// will simply hold the references for a Loomian from party and ActiveLoomianData
public class ActiveLoomian extends Loomian {
    

    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;


    public ActiveLoomian(
        Loomian loomian,
        ActiveLoomianData activeLoomianData
    ) {
        super(loomian);

        this.types = activeLoomianData.types();
        this.battleStats = activeLoomianData.battleStats();
        this.statChanges = activeLoomianData.statChanges();
    }


    


    // getters
    public List<TypeId> types() {return types;}
    public Stats battleStats() {return battleStats;}
    public StatChanges statChanges() {return statChanges;}
}
