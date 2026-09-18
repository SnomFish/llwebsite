package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.event.EventSideId;

// ALL LOOMIAN PARAMETERS ARE PRIVATE BY CHOICE
public class ActiveLoomian extends Loomian {


    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;
    private IAction action;
    

    public ActiveLoomian(
        Loomian loomian
    ) {
        super(loomian);
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.battleStats = loomian.actualStats().deepCopy();
        this.statChanges = new StatChanges();
        this.action = null;
    }
    public ActiveLoomian(ActiveLoomian activeLoomian) {
        super(activeLoomian.deepCopy());
        this.types = new ArrayList<>(activeLoomian.types);
        this.battleStats = activeLoomian.battleStats.deepCopy();
        this.statChanges = activeLoomian.statChanges.deepCopy();
        this.action = activeLoomian.action.deepCopy();
    }


    public ActiveLoomian deepCopy() {
        return new ActiveLoomian(this);
    }

    public List<Branch<BattleContext>> dispatchEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        outcomes = ability().handleEvent(context, event); 

        return outcomes;
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
