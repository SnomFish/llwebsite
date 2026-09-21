package github.snomfish.domain;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Event;

// ALL LOOMIAN PARAMETERS ARE PRIVATE BY CHOICE
public class ActiveLoomian extends Loomian {


    private List<TypeId> types;
    private Stats battleStats;
    private StatChanges statChanges;
    private IAction action;
    private Mechanics mechanics; // extra functionality that is temp added to an onfield loomina such as coursing venom being in effect
    

    public ActiveLoomian(
        Loomian loomian
    ) {
        super(loomian);
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.battleStats = loomian.actualStats().deepCopy();
        this.statChanges = new StatChanges();
        this.action = null;
        this.mechanics = new Mechanics();
    }
    public ActiveLoomian(ActiveLoomian activeLoomian) {
        super(activeLoomian);
        this.types = new ArrayList<>(activeLoomian.types);
        this.battleStats = activeLoomian.battleStats.deepCopy();
        this.statChanges = activeLoomian.statChanges.deepCopy();
        if (activeLoomian.action != null) this.action = activeLoomian.action.deepCopy();
        this.mechanics = activeLoomian.mechanics.deepCopy();
    }


    public ActiveLoomian deepCopy() {
        return new ActiveLoomian(this);
    }

    public List<Branch<BattleContext>> dispatchEvent(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        outcomes = flatMap(outcomes, c -> ability().handleEvent(c, event));
        outcomes = flatMap(outcomes, c -> item().handleEvent(c, event));
        outcomes = flatMap(outcomes, c -> status().handleEvent(c, event)); // has to be afunction as I only store Ids for ability item and status
        outcomes = flatMap(outcomes, c -> mechanics.handleEvent(c, event)); 

        return outcomes;
    }


    // getter;
    public List<TypeId> types() {return types;}
    public Stats battleStats() {return battleStats;}
    public StatChanges statChanges() {return statChanges;}
    public IAction action() {return action;}
    public Mechanics mechanics() {return mechanics;}


    // setter
    public void setTypes(List<TypeId> types) {this.types = types;}
    public void setBattleStats(Stats battleStats) {this.battleStats = battleStats;} 
    public void setStatChanges(StatChanges statChanges) {this.statChanges = statChanges;}
    public void setAction(IAction action) {this.action = action;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;} 
}
