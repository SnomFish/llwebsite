package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.copy.DeepCopyable;

public class ActiveLoomianData implements DeepCopyable<ActiveLoomianData> {
    

    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;
    

    public ActiveLoomianData(
        Loomian loomian
    ) {
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.battleStats = loomian.actualStats().deepCopy();
        this.statChanges = new StatChanges();
    }
    public ActiveLoomianData(
        List<TypeId> types,
        Stats battleStats,
        StatChanges statChanges
    ) {
        this.types = types;
        this.battleStats = battleStats;
        this.statChanges = statChanges;
    }


    public ActiveLoomianData deepCopy() {
        return new ActiveLoomianData(
            new ArrayList<>(types),
            battleStats.deepCopy(),
            statChanges.deepCopy()
        );
    }


    // getter;
    public List<TypeId> types() {return types;}
    public Stats battleStats() {return battleStats;}
    public StatChanges statChanges() {return statChanges;}


    public Builder builder() {
        return new Builder(this);
    }
    public class Builder {

        private ActiveLoomianData copy;

        public Builder(ActiveLoomianData original) {
            copy = original.deepCopy();
        }

        public Builder types(List<TypeId> types) {
            copy.types = types;
            return this;
        }
        public Builder battleStats(Stats BattleStats) {
            copy.battleStats = battleStats;
            return this;
        }
        public Builder statCanges(StatChanges statChanges) {
            copy.statChanges = statChanges;
            return this;
        }

        public ActiveLoomianData build() {
            return copy;
        }
    } 
}
