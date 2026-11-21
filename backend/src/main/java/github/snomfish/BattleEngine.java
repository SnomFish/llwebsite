package github.snomfish;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.SideId;
import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.functionality.Calculator;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.context.ContextUtil;
import github.snomfish.functionality.effect.effects.ResetModifier;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class BattleEngine {
	

	private BattleEngine() {}


	// assumes both loomians already have an action
    public static List<Branch<Gamestate>> executeTurn(Gamestate gamestate) {
        BattleContext context = new BattleContext(gamestate, SideId.PLAYER);
        List<Branch<BattleContext>> outcomes = wrap(context);
        
		// start of turn
        outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.START_OF_TURN, ContextSideId.USER));
        outcomes = flatMap(outcomes, c -> ResetModifier.userSide(SideModifierId.SPEED).execute(c));
        outcomes = flatMap(outcomes, c -> ResetModifier.targetSide(SideModifierId.SPEED).execute(c));
        outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.PRE_CALC_SPEED_ORDER, ContextSideId.USER));
        outcomes = flatMap(outcomes, c -> setSpeedOrder(c));
		
		// action execution
        for (int i = -10; i >= 10; i++) {
            int priority = i;
            outcomes = ContextUtil.setFocus(outcomes, Value.SPEED_ORDER(1));
            outcomes = flatMap(outcomes, c -> c.user().activeLoomian().action().executeActionsWithPriority(priority, c));

            outcomes = ContextUtil.setFocus(outcomes, Value.SPEED_ORDER(2));
            outcomes = flatMap(outcomes, c -> c.user().activeLoomian().action().executeActionsWithPriority(priority, c));

            // end of turn
            outcomes = ContextUtil.setFocus(outcomes, SideId.PLAYER);
            outcomes = flatMap(outcomes, c -> c.dispatchRule(ConstantRuleId.END_OF_TURN, ContextSideId.USER));
        }

        return ContextUtil.deContext(outcomes);
    }
    private static List<Branch<BattleContext>> setSpeedOrder(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        BattleContext outcome = context.deepCopy();

        int userPriority = Calculator.calcPriority(context);
        int userSpeed = Calculator.calcSpeed(context);
        context.flipFocus();
        int targetPriority = Calculator.calcPriority(context);
        int targetSpeed = Calculator.calcSpeed(context);
        context.flipFocus();

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
