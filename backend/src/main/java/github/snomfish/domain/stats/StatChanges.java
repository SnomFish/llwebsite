package github.snomfish.domain.stats;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

import static github.snomfish.domain.stats.StatChangesId.*;

public class StatChanges implements DeepCopyable<StatChanges> {
    

    private Map<StatChangesId, Integer> stages;
    private Map<StatChangesId, Integer> stageModifiers; // modifiers for when stages are added or removed (for anomaly)


    public StatChanges() {
        stages = new HashMap<>();
        stageModifiers = new HashMap<>();
        for (StatChangesId key : StatChangesId.values()) {
            stages.put(key, 0);
            stageModifiers.put(key, 1);
        }
    }
    public StatChanges(StatChanges copy) {
        this.stages = new HashMap<>(copy.stages);
        this.stageModifiers = new HashMap<>(copy.stageModifiers);
    }


    @Override 
    public StatChanges deepCopy() {
        return new StatChanges(this);
    }

    
    // getters
    public int stage(StatChangesId id) {
        return stages.get(id);
    }
    public int stageModifier(StatChangesId id) {
        return stageModifiers.get(id);
    }

    private double toMultiplier(int stage) {
        return (2 + (double)Math.max(stage, 0)) / (2 + (double)Math.max(-stage, 0)); 
    }
    public double multiplier(StatChangesId id) {
        if (id == CRIT_CHANCE) throw new RuntimeException("crit chance does not have a multiplier value");
        return toMultiplier(stages.get(id));
    }
    public double multiplier(StatId id) {
        return switch (id) {
            case StatId.HEALTH -> 1.0;
            case StatId.ENERGY -> 1.0;
            case StatId.MATTACK -> toMultiplier(stages.get(MATTACK));
            case StatId.MDEFENSE -> toMultiplier(stages.get(MDEFENSE));
            case StatId.RATTACK -> toMultiplier(stages.get(RATTACK));
            case StatId.RDEFENSE -> toMultiplier(stages.get(RDEFENSE));
            case StatId.SPEED -> toMultiplier(stages.get(SPEED));
        };
    }


    // setter
    // this is bad because it does not handle events
    // nevermind this shouldnt handle events, the effects should
    public void set(StatChangesId id, int value) {
        stages.put(id, value);
    }


    // adders
    public void addTo(StatChangesId id, int delta) {
        int value = stages.get(id) + delta;
        stages.put(id, value);
    }
}
