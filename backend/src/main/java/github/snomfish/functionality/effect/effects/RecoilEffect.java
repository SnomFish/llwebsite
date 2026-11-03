package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.gate.GateId.CANNOT_TAKE_RECOIL;

public class RecoilEffect implements IEffect {
    

    private INumber recoilNumber; // fraction of the damage dealt


    private RecoilEffect(INumber recoilNumber) {
        this.recoilNumber = recoilNumber;
    }


    private RecoilEffect(RecoilEffect copy) {
        this.recoilNumber = copy.recoilNumber.deepCopy();
    } 
    public RecoilEffect deepCopy() {
        return new RecoilEffect(this);
    }


    public static IEffect onUser(INumber recoilNumber) {
        return new ConditionalEffect(
            new GateCondition(CANNOT_TAKE_RECOIL),
            new RecoilEffect(recoilNumber)
        );
    }
    // on target does not exist yet as right now I dont think it needs to


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {


        if (Value.USER_ABILITY_ID.get(context) == AbilityId.BONEHEADED) List.of(new Branch<>(context, 1));

        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Double> recoilBranch : recoilNumber.execute(context)) {
            BattleContext outcome =     context.deepCopy();
            ActiveLoomian user =        outcome.user().activeLoomian();
            Move move =                 user.action().move();
            int recoil =                (int) (move.damage() * recoilBranch.value());

            recoil = Math.clamp(recoil, 1, user.currentHealth()); // clamps the recoil damage
            user.addToCurrentHealth(-recoil);

            outcomes.add(new Branch<>(
                outcome,
                recoilBranch.probability()
            ));
        }

        return outcomes;
    }
}
