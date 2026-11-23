package github.snomfish.functionality.effect.effects;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.domain.move.structures.Cascade;
import github.snomfish.domain.move.structures.IMove;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.Multiply;

public class DoubleStrikeEffect implements IEffect {
	

	public DoubleStrikeEffect() {}


	private DoubleStrikeEffect(DoubleStrikeEffect copy) {}
	public DoubleStrikeEffect deepCopy() {
		return new DoubleStrikeEffect(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		Move oldMove = Value.USER().MOVE().get(outcome);
		Move newMove = oldMove.deepCopy();

		// second hit is the same as the first but has a quarter of the base power
		IMove firstHit = oldMove.onMove().deepCopy();
		IMove secondHit = oldMove.basePower(new Multiply(oldMove.basePower(), new Constant(0.25))).onMove().deepCopy();

		newMove.onMove(Cascade.build()
			.add(firstHit)
			.add(secondHit)
		);
		Value.USER().MOVE().set(outcome, newMove);

		return wrap(outcome);
	}
}
