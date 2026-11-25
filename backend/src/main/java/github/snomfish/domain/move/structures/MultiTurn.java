package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.IMove;
import github.snomfish.domain.move.Move;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.action.Action;
import github.snomfish.functionality.action.MoveAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class MultiTurn extends IMove {

	
	// these moves should lock the user in
	private List<IMove> moves;
	private int executionStage;


	public MultiTurn() {
		this.moves = new ArrayList<>();
		this.executionStage = 0;
	}


	public static MultiTurn build() {
		return new MultiTurn();
	}
	public MultiTurn addTurn(IMove move) {
		this.moves.add(move);
		return this;
	}


	private MultiTurn(MultiTurn copy) {
		this.moves = deepCopyList(copy.moves);
		this.executionStage = copy.executionStage;

	}
	public MultiTurn deepCopy() {
		return new MultiTurn(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {

		List<Branch<BattleContext>> outcomes = wrap(context);

		// executes the current stage
		outcomes = flatMap(outcomes, this::executeMove);
		// sets the users next turn action to be this move and incriment the execution stage 
		outcomes = flatMap(outcomes, this::executeIncrementExecutionStage);

		return outcomes;
	}


	private List<Branch<BattleContext>> executeMove(BattleContext context) {
		return moves.get(executionStage).execute(context);
	}


	private List<Branch<BattleContext>> executeIncrementExecutionStage(BattleContext context) {
		int currentExecutionStage = Value.USER().MOVE().EXECUTION_STAGE().get(context);

		if (currentExecutionStage >= moves.size()) return wrap(context); // the user has reached the end of the move

		// takes the contexts current execution stage, increments it and constructs a new next turn action
		// copying the move the user has currently using (should hopefully be the multi turn move)
		currentExecutionStage++;
		Value.USER().MOVE().EXECUTION_STAGE().set(context, currentExecutionStage);
		
		Move nextTurnMove = Value.USER().MOVE().get(context);
		Action nextTurnAction = new Action();
		nextTurnAction.setMainAction(new MoveAction(nextTurnMove));
		Value.USER().NEXT_TURN_ACTION().set(context, nextTurnAction);

		return wrap(context);
	}
}
