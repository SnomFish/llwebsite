package github.snomfish.functionality.condition.predicates;

import github.snomfish.functionality.context.BattleContext;

public class PredicateNot implements IPredicate {
	

	public IPredicate predicate;


	public PredicateNot (IPredicate predicate) {
		this.predicate = predicate;
	}


	private PredicateNot(PredicateNot copy) {
		this.predicate = copy.predicate.deepCopy();
	}
	public PredicateNot deepCopy() {
		return new PredicateNot(this);
	}


	public boolean execute(BattleContext context) {
		return !predicate.execute(context);
	}
}
