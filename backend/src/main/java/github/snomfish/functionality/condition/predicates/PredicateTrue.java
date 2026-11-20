package github.snomfish.functionality.condition.predicates;

import github.snomfish.functionality.context.BattleContext;

public class PredicateTrue implements IPredicate {
	
	
	public PredicateTrue() {}


	public PredicateTrue deepCopy() {
		return new PredicateTrue();
	}


	public boolean execute(BattleContext context) {
		return true;
	}
}
