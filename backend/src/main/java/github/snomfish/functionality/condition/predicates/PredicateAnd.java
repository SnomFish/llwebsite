package github.snomfish.functionality.condition.predicates;

import java.util.List;

import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class PredicateAnd implements IPredicate {
	

	private List<IPredicate> predicates;


	public PredicateAnd(IPredicate a, IPredicate b) {
		predicates.add(a);
		predicates.add(b);
	}
	public PredicateAnd(IPredicate a, IPredicate b, IPredicate c) {
		predicates.add(a);
		predicates.add(b);
		predicates.add(c);
	}
	public PredicateAnd(List<IPredicate> predicates) {
		this.predicates = predicates;
	}


	private PredicateAnd(PredicateAnd copy) {
		this.predicates = deepCopyList(copy.predicates);
	}
	public PredicateAnd deepCopy() {
		return new PredicateAnd(this);
	}


	public boolean execute(BattleContext context) {
		boolean result = true;

		for (IPredicate predicate : predicates) {
			result &= predicate.execute(context);
		}

		return result;
	}
}
