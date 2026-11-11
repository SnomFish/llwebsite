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
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.domain.stats.StatChangesId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class ApplyStatChanges implements IEffect {
    

    private ContextSideId userSideI;
    private ContextSideId targetSideI;
    private GenericId statLoweredEvent; // runs after each inidividual stat change if a stat is lowered
    private Value<ActiveLoomian> activeLoomianValue; // the loomian who will have their stats changed
    private Map<StatChangesId, INumber> statChanges;


    private ApplyStatChanges(
        ContextSideId userSideI,
        ContextSideId targetSideI,
        GenericId statLoweredEvent,
        Value<ActiveLoomian> activeLoomianValue
    ) {
        this.userSideI = userSideI;
        this.targetSideI = targetSideI;
        this.statLoweredEvent = statLoweredEvent;
        this.activeLoomianValue = activeLoomianValue;
        this.statChanges = new HashMap<>();
    }


    public static ApplyStatChanges userChangingUser() {
        return new ApplyStatChanges(
            ContextSideId.USER,
            ContextSideId.USER,
            GenericId.SELF_STATS_LOWERED_BY_SELF,
            Value.USER().LOOMIAN()
        );
    }
    public static ApplyStatChanges userChangingTarget() {
        return new ApplyStatChanges(
            ContextSideId.USER,
            ContextSideId.TARGET,
            GenericId.SELF_STATS_LOWERED_BY_OPP,
            Value.TARGET().LOOMIAN()
        );
    }
    public static ApplyStatChanges targetChangingUser() {
        return new ApplyStatChanges(
            ContextSideId.TARGET,
            ContextSideId.USER,
            GenericId.SELF_STATS_LOWERED_BY_OPP,
            Value.USER().LOOMIAN()
        );
    }
    public static ApplyStatChanges targetChangingTarget() {
        return new ApplyStatChanges(
            ContextSideId.TARGET,
            ContextSideId.TARGET,
            GenericId.SELF_STATS_LOWERED_BY_SELF,
            Value.TARGET().LOOMIAN()
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
        this.activeLoomianValue = copy.activeLoomianValue;
    }
    public ApplyStatChanges deepCopy() {
        return new ApplyStatChanges(this);
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        outcomes = flatMap(outcomes, c -> c.dispatchEvent(GenericId.SELF_PRE_STATS_CHANGED, userSideI));
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
        if (deltaStage < 0) {
            outcomes = flatMap(outcomes, c -> c.dispatchEvent(statLoweredEvent, targetSideI));
        }

        return outcomes;
    }
}
