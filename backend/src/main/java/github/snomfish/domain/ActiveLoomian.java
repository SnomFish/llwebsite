package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ability.AbilityInstance;
import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.action.Action;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Report;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

// ALL LOOMIAN PARAMETERS ARE PRIVATE BY CHOICE
public class ActiveLoomian extends Loomian {


    private List<TypeId> types;
    private AbilityInstance abilityInstance;
    private Stats stats;
    private StatChanges statChanges;
    private Action action;
    private Mechanics mechanics; // extra functionality that is temp added to an onfield loomian such as coursing venom being in effect
    private int statusTurns;
    

    public ActiveLoomian(
        Loomian loomian
    ) {
        super(loomian);
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.stats = actualStats().deepCopy();
        this.statChanges = new StatChanges();
        this.action = null;
        this.mechanics = new Mechanics();
        this.statusTurns = 0;
    }
    public ActiveLoomian(ActiveLoomian activeLoomian) {
        super(activeLoomian);
        this.types = new ArrayList<>(activeLoomian.types);
        this.statChanges = activeLoomian.statChanges.deepCopy();
        if (activeLoomian.action != null) this.action = activeLoomian.action.deepCopy();
        this.mechanics = activeLoomian.mechanics.deepCopy();
        this.statusTurns = activeLoomian.statusTurns;
    }


    public ActiveLoomian deepCopy() {
        return new ActiveLoomian(this);
    }


    public void incrementTurnNumber() {
        mechanics.incrementTurnNumber();
        statusTurns += 1;
    }


    public List<Branch<BattleContext>> dispatchRule(BattleContext context, IRuleId event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        outcomes = flatMap(outcomes, c -> abilityInstance().handleRule(c, event));
        outcomes = flatMap(outcomes, c -> item().handleRule(c, event));
        outcomes = flatMap(outcomes, c -> status().handleRule(c, event)); // has to be afunction as I only store Ids for ability item and status
        outcomes = flatMap(outcomes, c -> mechanics.handleRule(c, event)); 

        return outcomes;
    }
    public double dispatchGate(BattleContext context, IGateId gateId, double probability) {

        probability *= abilityInstance.handleGate(context, gateId);

        return probability;
    }
    public Report dispatchInterceptor(Report report, BattleContext context, IRuleId event) {
        
        report.record(abilityInstance().ability().interceptors(), event);

        return report;
    }


    // getter;
    public List<TypeId> types() {return types;}
    public TypeId primaryType() {return types.get(0);}
    public TypeId secondaryType() {return types.get(1);}
    public AbilityInstance abilityInstance() {return abilityInstance;}
    public Stats stats() {return stats;}
    public StatChanges statChanges() {return statChanges;}
    public Action action() {return action;}
    public Mechanics mechanics() {return mechanics;}
    public int statusTurns() {return statusTurns;}


    // setter
    public void setTypes(List<TypeId> types) {this.types = types;}
    public void setPrimaryType(TypeId typeId) {this.types.set(0, typeId);}
    public void setSecondaryType(TypeId typeId) {this.types.set(1, typeId);}
    public void setAbilityInstance(AbilityInstance abilityInstance) {this.abilityInstance = abilityInstance;}
    public void setStats(Stats stats) {this.stats = stats;}
    public void setStatChanges(StatChanges statChanges) {this.statChanges = statChanges;}
    public void setAction(Action action) {this.action = action;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;} 
    public void setStatusTurns(int statusTurns) {this.statusTurns = statusTurns;}
}
