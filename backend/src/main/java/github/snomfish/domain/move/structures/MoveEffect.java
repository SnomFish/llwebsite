package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.gateIds.GateForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class MoveEffect implements IMove {

    
    private List<IEffect> primaries;
    private List<IEffect> secondaries;
    

    public MoveEffect() {
        this.primaries = new ArrayList<>();
        this.secondaries = new ArrayList<>();
    }


    private MoveEffect(MoveEffect copy) {
        this.primaries = copy.primaries;
        this.secondaries = copy.secondaries;
    }
    public MoveEffect deepCopy() {
        return new MoveEffect(this);
    }


    // getter
    public List<IEffect> primary() {return primaries;}
    public List<IEffect> secondary() {return secondaries;}
    
    
    // builder functions
    public static MoveEffect build() {
        return new MoveEffect();
    }
    public MoveEffect primary(IEffect effect) {
        this.primaries.add(effect);
        return this;
    }
    public MoveEffect secondary(IEffect effect) {
        this.secondaries.add(effect);
        return this;
    }


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        Value.USER().MOVE().CAN_MOVE_FAIL().set(context, true);
        for (IEffect effect : primaries) {
            outcomes = flatMap(outcomes, c -> executePrimary(c, effect));
        }
        Value.USER().MOVE().CAN_MOVE_FAIL().set(context, false); // locks the condition in, preventing secondary effects from 
        outcomes = flatMap(outcomes, c -> executeSecondaries(c));

        return outcomes;
    }


    private List<Branch<BattleContext>> executePrimary(BattleContext context, IEffect effect) {
        if (Value.USER().MOVE().HAS_MOVE_FAILED().get(context)) return wrap(context);

        return effect.execute(context);
    }


    private List<Branch<BattleContext>> executeSecondaries(BattleContext context) {
        List<Branch<BattleContext>> outcomes = wrap(context);

        if (Value.USER().MOVE().HAS_MOVE_FAILED().get(context)) return outcomes;

        double probability = context.dispatchGate(GateForAllId.CAN_USE_MOVE_SECONDARY_EFFECTS, ContextSideId.USER);
        if (probability > 0) {
            outcomes = wrap(context);
            for (IEffect effect : secondaries) {
                outcomes = flatMap(outcomes, c -> effect.execute(c));
            } 

        }
        if (probability < 1) {
            outcomes = List.of(new Branch<>(context, 1-probability));
            
        }

        return outcomes;
    }
}