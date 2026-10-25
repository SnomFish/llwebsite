package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.domain.stats.StatChangesId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class ApplyStatChanges implements IEffect {
    

    private EventId preEvent; // runs once before the stat changes are applied
    private EventId loweredStatEvent; // runs after each inidividual stat change if a stat is lowered
    private Value activeLoomianValue; // the loomian who will have their stats changed
    private Map<StatChangesId, INumber> statChanges;


    private ApplyStatChanges(
        EventId preEvent,
        EventId loweredStatEvent,
        Value activeLoomianValue
    ) {
        this.preEvent = preEvent;
        this.loweredStatEvent = loweredStatEvent;
        this.activeLoomianValue = activeLoomianValue;
        this.statChanges = new HashMap<>();
    }


    public static ApplyStatChanges userChangingUser() {
        return new ApplyStatChanges(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT, 
            null,
            Value.USER
        );
    }
    public static ApplyStatChanges userChangingTarget() {
        return new ApplyStatChanges(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT,  
            EventId.USER_LOWERED_TARGET_STATS_EVENT,
            Value.TARGET
        );
    }
    public static ApplyStatChanges targetChangingUser() {
        return new ApplyStatChanges(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT,  
            EventId.TARGET_LOWERED_USER_STATS_EVENT,
            Value.USER
        );
    }
    public static ApplyStatChanges targetChangingTarget() {
        return new ApplyStatChanges(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT, 
            null, 
            Value.TARGET
        );
    }


    public ApplyStatChanges mattack(INumber mattack) {
        statChanges.put(MATTACK, mattack);
        return this;
    }
    public ApplyStatChanges mdefense(INumber mdefense) {
        statChanges.put(MDEFENSE, mdefense);
        return this;
    }
    public ApplyStatChanges rattack(INumber rattack) {
        statChanges.put(RATTACK, rattack);
        return this;
    }
    public ApplyStatChanges rdefense(INumber rdefense) {
        statChanges.put(RDEFENSE, rdefense);
        return this;
    }
    public ApplyStatChanges speed(INumber speed) {
        statChanges.put(SPEED, speed);
        return this;
    }
    public ApplyStatChanges accuracy(INumber accuracy) {
        statChanges.put(ACCURACY, accuracy);
        return this;
    }
    public ApplyStatChanges evasion(INumber evasion) {
        statChanges.put(EVASION, evasion);
        return this;
    }
    public ApplyStatChanges critChance(INumber critChance) {
        statChanges.put(CRIT_CHANCE, critChance);
        return this;
    }


    private ApplyStatChanges(ApplyStatChanges copy) { // these are enums
        this.preEvent = copy.preEvent;
        this.loweredStatEvent = copy.loweredStatEvent;
        this.activeLoomianValue = copy.activeLoomianValue;
    }
    public ApplyStatChanges deepCopy() {
        return new ApplyStatChanges(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(preEvent));
        for (StatChangesId key : StatChangesId.values()) {
            outcomes = flatMap(outcomes, c -> applyStatChangeModule(context, key));
        }

        return outcomes;
    }


    private List<Branch<BattleContext>> applyStatChangeModule(
        BattleContext context,
        StatChangesId key
    ) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        
        INumber deltaStageNumber = statChanges.get(key);
        
        for (Branch<Double> deltaStageBranch : deltaStageNumber.execute(context)) {
            if (deltaStageBranch.value() == 0) continue;
            outcomes = flatMap(outcomes, c -> applyStatChangeNumberModule(context, key, deltaStageBranch));
        }

        return outcomes;
    }


    private List<Branch<BattleContext>> applyStatChangeNumberModule(
        BattleContext context,
        StatChangesId key,
        Branch<Double> deltaStageBranch
    ) { 
        BattleContext outcome = context.deepCopy();
        ActiveLoomian loomian = (ActiveLoomian)activeLoomianValue.get(outcome);
        int currentStage = loomian.statChanges().stage(key);
        int deltaStage = deltaStageBranch.value().intValue() * loomian.statChanges().stageModifier(key);
        deltaStage = Math.clamp(deltaStage, 6-currentStage, -6+currentStage); // preparing the delta

        // applies the stat change
        loomian.statChanges().addTo(key, deltaStage);
    
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(outcome, deltaStageBranch.probability()));

        // if a stat was lowered then trigger this
        if (deltaStage < 0 && loweredStatEvent != null) {
            outcomes = flatMap(outcomes, c -> c.dispatchEvent(loweredStatEvent));
        }

        return outcomes;
    }
}
