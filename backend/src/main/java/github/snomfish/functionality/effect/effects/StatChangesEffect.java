package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.stats.StatChanges;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.stats.StatChangesId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class StatChangesEffect implements IEffect {
    
    
    private EventId preEffectEvent;
    private EventId postEffectEvent;
    private Value activeLoomianValue;
    
    private Map<StatChangesId, Integer> statChanges;


    public StatChangesEffect(
        EventId preEffectEvent, 
        EventId postEffectEvent,
        Value activeLoomianValue,
        int mattack, int mdefense, int rattack, int rdefense, int speed, int accuracy, int evasion, int critChance
    ) {
        this.preEffectEvent = preEffectEvent;
        this.postEffectEvent = postEffectEvent;
        this.activeLoomianValue = activeLoomianValue; // the value pointing at the loomian getting the stat changes
        this.statChanges = new HashMap<>();
        statChanges.put(MATTACK, mattack);
        statChanges.put(MDEFENSE, mdefense);
        statChanges.put(RATTACK, rattack);
        statChanges.put(RDEFENSE, rdefense);
        statChanges.put(SPEED, speed);
        statChanges.put(ACCURACY, accuracy);
        statChanges.put(EVASION, evasion);
        statChanges.put(CRIT_CHANCE, critChance);
    }


    public static StatChangesEffect userChangingUser(
        int mattack, int mdefense, int rattack, int rdefense, int speed, int accuracy, int evasion, int critChance
    ) {
        return new StatChangesEffect(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT, 
            null,
            Value.USER,
            mattack, mdefense, rattack, rdefense, speed, accuracy, evasion, critChance
        );
    }
    public static StatChangesEffect userChangingTarget(
        int mattack, int mdefense, int rattack, int rdefense, int speed, int accuracy, int evasion, int critChance
    ) {
        return new StatChangesEffect(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT,  
            EventId.USER_LOWERED_TARGET_STATS_EVENT,
            Value.TARGET,
            mattack, mdefense, rattack, rdefense, speed, accuracy, evasion, critChance
        );
    }
    public static StatChangesEffect targetChangingUser(
        int mattack, int mdefense, int rattack, int rdefense, int speed, int accuracy, int evasion, int critChance
    ) {
        return new StatChangesEffect(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT,  
            EventId.TARGET_LOWERED_USER_STATS_EVENT,
            Value.USER,
            mattack, mdefense, rattack, rdefense, speed, accuracy, evasion, critChance
        );
    }
    public static StatChangesEffect targetChangingTarget(
        int mattack, int mdefense, int rattack, int rdefense, int speed, int accuracy, int evasion, int critChance
    ) {
        return new StatChangesEffect(
            EventId.PRE_STAT_CHANGES_EFFECT_EVENT, 
            null, 
            Value.TARGET,
            mattack, mdefense, rattack, rdefense, speed, accuracy, evasion, critChance
        );
    }


    public StatChangesEffect(StatChangesEffect copy) {
        this.preEffectEvent = copy.preEffectEvent;
        this.postEffectEvent = copy.postEffectEvent;
        this.activeLoomianValue = copy.activeLoomianValue;
        this.statChanges = new HashMap<>(copy.statChanges);
    }


    @Override 
    public StatChangesEffect deepCopy() {
        return new StatChangesEffect(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(preEffectEvent)); // things like anomaly
        for (StatChangesId key : StatChangesId.values()) { // remove crit chance probably (i think)
            outcomes = flatMap(outcomes, c -> applyStatChangeModule(context, key));
        }

        return outcomes;        
    }


    private List<Branch<BattleContext>> applyStatChangeModule(BattleContext context, StatChangesId key) {
        BattleContext outcome = context.deepCopy();
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(outcome, 1.0));

        ActiveLoomian loomian = (ActiveLoomian)activeLoomianValue.get(context);

        int currentStage = loomian.statChanges().stage(key);
        int deltaStage = statChanges.get(key) * loomian.statChanges().stageModifier(key);
        deltaStage = Math.clamp(deltaStage, 6-currentStage, -6+currentStage);

        loomian.statChanges().addTo(key, deltaStage);
        
        if (deltaStage < 0 && postEffectEvent != null) {
            outcomes = flatMap(outcomes, c -> c.dispatchEvent(postEffectEvent));
        }

        return outcomes;
    }
}
