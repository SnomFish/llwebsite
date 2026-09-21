package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.move.Move;
import github.snomfish.domain.move.modifiers.Modifiers;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.domain.stats.StatId;
import github.snomfish.functionality.Constants;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.domain.move.modifiers.ModifierId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.event.EventId.*;

// the move tricky tactics does not work with this attacking and defending stat implementation, will require probably a new enum or the use of Value
public class DealFormulaDamage implements IEffect {
    // deals formula damage to the target
    
    private StatId attackingStatId;
    private StatId defendingStatId;
    private INumber basePowerINumber;
    
 
    public DealFormulaDamage(
        StatId attackingStatId,
        StatId defendingStatId,
        INumber basePowerINumber
    ) {
        this.attackingStatId = attackingStatId;
        this.defendingStatId = defendingStatId;
        this.basePowerINumber = basePowerINumber;
    }


    @Override 
    public DealFormulaDamage deepCopy() {
        return new DealFormulaDamage(
            attackingStatId, 
            defendingStatId, 
            basePowerINumber.deepCopy()
        );
    } 


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));

        context.log("executing damage effect");
        
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(PRE_DEAL_FORMULA_DAMAGE_EVENT));
        outcomes = flatMap(outcomes, c -> basePowerModule(c));
        outcomes = flatMap(outcomes, c -> critChanceModule(c));
        outcomes = flatMap(outcomes, c -> damageModule(c));
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(PRE_DAMAGE_EVENT));
        outcomes = flatMap(outcomes, c -> damageRollModule(c));
        //outcomes = flatMap(outcomes, c -> applyDamageModule(c));
        outcomes = flatMap(outcomes, c -> c.dispatchEvent(POST_DAMAGE_EVENT));
        // separate apply damage from the damage roll?

        return outcomes;
    }





    private List<Branch<BattleContext>> basePowerModule(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Double> basePowerBranch : basePowerINumber.execute(context)) {
            BattleContext outcome = context.deepCopy();
            int basePower = basePowerBranch.value().intValue();

            ActiveLoomian user = outcome.user().activeLoomian();
            Move move = user.action().move();
            move.setDamage(basePower);
            outcomes.add(new Branch<>(
                outcome,
                basePowerBranch.probability()
            ));
        }

        return outcomes;
    }


    private static final double[] CRIT_CHANCES = {1/24, 1/8, 1/2, 1};

    private List<Branch<BattleContext>> critChanceModule(BattleContext context) {
        ActiveLoomian user = context.user().activeLoomian();
        Move move = user.action().move();
        
        int critChance = user.statChanges().stage(StatChangesId.CRIT_CHANCE) + move.critChance();
        critChance = Math.clamp(critChance, 0, 3);

        BattleContext critContext1 = context.deepCopy(); // did crit
        BattleContext critContext2 = context.deepCopy(); // did not crit
        critContext1.user().activeLoomian().action().move().setHasCrit(true);
        critContext1.gamestate().log("critical hit!");
        // assumes hasCrit is always false so critContext2 is not setting it to false

        return List.of(
            new Branch<>(critContext2, CRIT_CHANCES[1-critChance]),
            new Branch<>(critContext1, CRIT_CHANCES[critChance])
        );
    }


    private List<Branch<BattleContext>> damageModule(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        ActiveLoomian user = outcome.user().activeLoomian();
        ActiveLoomian target = outcome.target().activeLoomian();
        Move move = user.action().move();


        double attackingStatModifier;
        double defendingStatModifier;
        if (move.hasCrit()) {
            attackingStatModifier = Math.max(user.statChanges().multiplier(attackingStatId), 1.0); // ensures on a crit lowered stats from attacking stat are ignored
            defendingStatModifier = Math.min(target.statChanges().multiplier(defendingStatId), 1.0); // same with stat raises on defending stat
        } else {
            attackingStatModifier = user.statChanges().multiplier(attackingStatId);
            defendingStatModifier = target.statChanges().multiplier(defendingStatId);
        }
        int attackingStat = (int)(user.battleStats().get(attackingStatId) * attackingStatModifier);
        int defendingStat = (int)(target.battleStats().get(defendingStatId) * defendingStatModifier);

        double damage = Math.floor(2 * Constants.LEVEL / 5) + 2;
        damage *= move.damage();
        damage *= attackingStat;
        damage /= defendingStat;
        damage = Math.floor(damage);
        damage /= 50;
        damage = Math.floor(damage);
        damage += 2;


        // modifiers
        Modifiers modifiers = move.modifiers();
        // types
        modifiers.mul(TYPE, move.typeChart().getModifier(move.type(), target.types()));
        // stab
        if (user.types().contains(move.type()) && !(user.abilityId() == AbilityId.AWAKENING || user.abilityId() == AbilityId.ACE)) { // stab
            modifiers.set(STAB, 1.25);
        }

        // modifier application
        // type modifier is never applied
        // crits natural 1.5 multiplier is never applied, and crit modifier should only be applied if a crit occurs
        if (move.hasCrit()) damage = Math.floor(damage * modifiers.get(CRIT) * 1.5); // crit modifier is 1.5
        damage = Math.floor(damage * modifiers.get(STAB)); 
        damage = Math.floor(damage * modifiers.get(TYPE)); 
        damage = Math.floor(damage * modifiers.get(DAMAGE)); 

        move.setDamage((int)damage);
        
        return List.of(new Branch<>(outcome, 1.0));
    }


    private List<Branch<BattleContext>> damageRollModule(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (double damageRollModifier = 0.85; damageRollModifier <= 1.0; damageRollModifier += 0.01) {

            BattleContext outcome = context.deepCopy();
            ActiveLoomian user = outcome.user().activeLoomian();
            ActiveLoomian target = outcome.target().activeLoomian();
            Move move = user.action().move();
            int damage = move.damage();

            move.setDamage((int)Math.clamp((double)damage * damageRollModifier, 1, target.currentHealth()));
            outcome.gamestate().log("move dealt " + damage + " damage");
            target.addToCurrentHealth(-damage);
            outcomes.add(new Branch<>(
                outcome,
                1/16
            ));
        }

        return outcomes;
    }
    
    
    private List<Branch<BattleContext>> applyDamageModule(BattleContext context) {
        return null;
    }
}
