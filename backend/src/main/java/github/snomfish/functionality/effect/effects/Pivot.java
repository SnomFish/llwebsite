package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class Pivot implements IEffect {
	

	public Pivot() {

	}


	private Pivot(Pivot copy) {}
	public Pivot deepCopy() {
		return new Pivot(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		// TODO implement
		return null;
	}
}
