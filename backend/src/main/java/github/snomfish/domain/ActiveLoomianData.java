package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.copy.DeepCopyable;

public class ActiveLoomianData implements DeepCopyable<ActiveLoomianData> {
    

    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;
    private IAction action;
    

    public ActiveLoomianData(
        Loomian loomian
    ) {
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.battleStats = loomian.actualStats().deepCopy();
        this.statChanges = new StatChanges();
        this.action = null;
    }
    public ActiveLoomianData(
        List<TypeId> types,
        Stats battleStats,
        StatChanges statChanges,
        IAction action
    ) {
        this.types = types;
        this.battleStats = battleStats;
        this.statChanges = statChanges;
        this.action = action;
    }


    public ActiveLoomianData deepCopy() {
        return new ActiveLoomianData(
            new ArrayList<>(types),
            battleStats.deepCopy(),
            statChanges.deepCopy(),
            action.deepCopy()
        );
    }


    // getter;
    public List<TypeId> types() {return types;}
    public Stats battleStats() {return battleStats;}
    public StatChanges statChanges() {return statChanges;}
    public IAction action() {return action;}


    // setter
    public void setTypes(List<TypeId> types) {
        this.types = types;
    }
    public void setBattleStats(Stats battleStats) {
        this.battleStats = battleStats;
    } 
    public void setStatChanges(StatChanges statChanges) {
        this.statChanges = statChanges;
    }
    public void setAction(IAction action) {
        this.action = action;
    }
}
