package github.snomfish.functionality.condition.predicates;

import github.snomfish.functionality.context.BattleContext;

public class PredicateFalse implements IPredicate {
	

	public PredicateFalse() {}


	public PredicateFalse deepCopy() {
		return new PredicateFalse();
	}


	public boolean execute(BattleContext context) {
		return false;
	}
}
