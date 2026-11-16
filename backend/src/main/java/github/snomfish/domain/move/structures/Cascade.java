package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.wrap;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class Cascade implements IMove {
	

	private List<IMove> moves;


	private Cascade() {
		this.moves = new ArrayList<>();
	}


	public static Cascade build() {
		return new Cascade();
	}
	public Cascade add(IMove moves) {
		this.moves.add(moves);
		return this;
	}


	private Cascade(Cascade copy) {
		this.moves = deepCopyList(copy.moves);
	}
	public Cascade deepCopy() {
		return new Cascade(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		//for this to execute the first condition must have been called, so the first moveEffect can be fired
		List<Branch<BattleContext>> outcomes = wrap(context);

		for (IMove move : moves) {
			for (Branch<BattleContext> outcome : outcomes) {
				Value.USER().MOVE().HAS_MOVE_FAILED().set(outcome.value(), false);
			}
			outcomes = flatMap(outcomes, move::execute);
		}

		return outcomes;
	}
}
