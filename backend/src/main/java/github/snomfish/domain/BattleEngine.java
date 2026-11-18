package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.context.ContextUtil;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class BattleEngine {
	

	private BattleEngine() {}


	// assumes both loomians already have an action
    public static List<Branch<BattleContext>> executeTurn(Gamestate gamestate) {
        BattleContext context = new BattleContext(gamestate, SideId.PLAYER);
        List<Branch<BattleContext>> outcomes = wrap(context);
        
        outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.START_OF_TURN, ContextSideId.USER));
        outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.PRE_CALC_SPEED_ORDER, ContextSideId.USER));
        outcomes = flatMap(outcomes, c -> setSpeedOrder(c));
		
		outcomes = ContextUtil.setFocus(outcomes, Value.SPEED_ORDER(1));
		outcomes = flatMap(outcomes, c -> c.user().activeLoomian().executeAction(c));

		outcomes = ContextUtil.setFocus(outcomes, Value.SPEED_ORDER(2));
		outcomes = flatMap(outcomes, c -> c.user().activeLoomian().executeAction(c));

		outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.END_OF_TURN_STATUS_EFFECTS, ContextSideId.USER));
		outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.END_OF_TURN, ContextSideId.USER));

        return null;
    }
    private static List<Branch<BattleContext>> setSpeedOrder(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        BattleContext outcome = context.deepCopy();

		Side user = outcome.user();
		Side target = outcome.target();

        int userPriority = user.activeLoomian().action().priority();
        int targetPriority = target.activeLoomian().action().priority();

        int userSpeed = (int) (user.activeLoomian().actualStats().speed() * 
            user.activeLoomian().statChanges().multiplier(StatChangesId.SPEED) * 
            user.modifiers().get(SideModifierId.SPEED));
        int targetSpeed = (int) (target.activeLoomian().actualStats().speed() * 
            target.activeLoomian().statChanges().multiplier(StatChangesId.SPEED) * 
            target.modifiers().get(SideModifierId.SPEED));
                            
		boolean equalPriority = userPriority == targetPriority;

        if ((userPriority > targetPriority) || (equalPriority && userSpeed > targetSpeed)) {
			// player is faster
			outcome.gamestate().setSpeedOrder(List.of(SideId.PLAYER, SideId.ENEMY));
			outcomes.add(new Branch<>(outcome, 1));

        } else if ((userPriority < targetPriority) || (equalPriority && userSpeed < targetSpeed)) {
			// enemy is faster
			outcome.gamestate().setSpeedOrder(List.of(SideId.ENEMY, SideId.PLAYER));
			outcomes.add(new Branch<>(outcome, 1));

        } else {
			// speed tie
			outcome.gamestate().setSpeedOrder(List.of(SideId.PLAYER, SideId.ENEMY));
			outcomes.add(new Branch<>(outcome, 0.5));
			outcome = context.deepCopy();
			outcome.gamestate().setSpeedOrder(List.of(SideId.ENEMY, SideId.PLAYER));
			outcomes.add(new Branch<>(outcome, 0.5));

        }
        
        return outcomes;
    }
}
