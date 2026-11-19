package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ability.AbilityInstance;
import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.action.NoAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.RuleReport;
import github.snomfish.functionality.event.gateIds.IGateId;
import github.snomfish.functionality.event.ruleIds.IRuleId;

// ALL LOOMIAN PARAMETERS ARE PRIVATE BY CHOICE
public class ActiveLoomian extends Loomian {


    private List<TypeId> types;
    private AbilityInstance abilityInstance;
    private Stats stats;
    private StatChanges statChanges;
    private IAction action;
    private Mechanics mechanics; // extra functionality that is temp added to an onfield loomian such as coursing venom being in effect
    private int statusTurns;

    // eventually move to tags
    private boolean hasActedThisTurn;
    private boolean hasSwappedInThisTurn;
    

    public ActiveLoomian(
        Loomian loomian
    ) {
        super(loomian);
        this.types = new ArrayList<>(loomian.species().baseTypes());
        this.stats = actualStats().deepCopy();
        this.statChanges = new StatChanges();
        this.action = new NoAction();
        this.mechanics = new Mechanics();
        this.statusTurns = 0;

        this.hasActedThisTurn = false;
        this.hasSwappedInThisTurn = false;
    }


    public ActiveLoomian(ActiveLoomian copy) {
        super(copy);
        this.types = new ArrayList<>(copy.types);
        this.statChanges = copy.statChanges.deepCopy();
        if (copy.action != null) this.action = copy.action.deepCopy();
        this.mechanics = copy.mechanics.deepCopy();
        this.statusTurns = copy.statusTurns;

        this.hasActedThisTurn = false;
        this.hasSwappedInThisTurn = false;
    }
    public ActiveLoomian deepCopy() {
        return new ActiveLoomian(this);
    }


    // getter;
    public List<TypeId> types() {return types;}
    public TypeId primaryType() {return types.get(0);}
    public TypeId secondaryType() {return types.get(1);}
    public AbilityInstance abilityInstance() {return abilityInstance;}
    public Stats stats() {return stats;}
    public StatChanges statChanges() {return statChanges;}
    public IAction action() {return action;}
    public Mechanics mechanics() {return mechanics;}
    public int statusTurns() {return statusTurns;}

    public boolean hasActedThisTurn() {return hasActedThisTurn;}
    public boolean hasSwappedInThisTurn() {return hasSwappedInThisTurn;}


    // setter
    public void setTypes(List<TypeId> types) {this.types = types;}
    public void setPrimaryType(TypeId typeId) {this.types.set(0, typeId);}
    public void setSecondaryType(TypeId typeId) {this.types.set(1, typeId);}
    public void setAbilityInstance(AbilityInstance abilityInstance) {this.abilityInstance = abilityInstance;}
    public void setStats(Stats stats) {this.stats = stats;}
    public void setStatChanges(StatChanges statChanges) {this.statChanges = statChanges;}
    public void setAction(IAction action) {this.action = action;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;} 
    public void setStatusTurns(int statusTurns) {this.statusTurns = statusTurns;}

    public void setActedThisTurn(boolean hasActedThisTurn) {this.hasActedThisTurn = hasActedThisTurn;}
    public void setSwappedInThisTurn(boolean hasSwappedInThisTurn) {this.hasSwappedInThisTurn = hasSwappedInThisTurn;}


    public void dispatchRule(RuleReport report, IRuleId ruleId) {
        report.record(abilityInstance.ability().rules(), ruleId);
        report.record(item().rules(), ruleId);
        report.record(status().rules(), ruleId);
        mechanics.dispatchRule(report, ruleId);
    }
    public void dispatchHook(HookReport report, IRuleId hookId) {
        report.record(abilityInstance().ability().hooks(), hookId);
        report.record(item().hooks(), hookId);
        report.record(status().hooks(), hookId);
        mechanics.dispatchHook(report, hookId);
    }
    public double dispatchGate(BattleContext context, IGateId gateId) {
        double probability = 1;

        probability *= abilityInstance.handleGate(context, gateId);
        probability *= item().handleGate(context, gateId);
        probability *= status().handleGate(context, gateId);
        probability *= mechanics().dispatchGate(context, gateId);

        return probability;
    }


    public List<Branch<BattleContext>> executeAction(BattleContext context) {
        this.hasActedThisTurn = true;
        List<Branch<BattleContext>> outcomes = action.execute(context);
        // setting any values here would not register on any of the outcomee
        return outcomes;
    }


    public void incrementTurnNumber() {
        mechanics.incrementTurnNumber();
        statusTurns += 1;
    }
}
