package github.snomfish.functionality.condition.predicates;

import java.util.List;

import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class PredicateOr implements IPredicate {
	

	private List<IPredicate> predicates;


	public PredicateOr(IPredicate a, IPredicate b) {
		predicates.add(a);
		predicates.add(b);
	}
	public PredicateOr(IPredicate a, IPredicate b, IPredicate c) {
		predicates.add(a);
		predicates.add(b);
		predicates.add(c);
	}
	public PredicateOr(List<IPredicate> predicates) {
		this.predicates = predicates;
	}


	private PredicateOr(PredicateOr copy) {
		this.predicates = deepCopyList(copy.predicates);
	}
	public PredicateOr deepCopy() {
		return new PredicateOr(this);
	}


	public boolean execute(BattleContext context) {
		boolean result = true;

		for (IPredicate predicate : predicates) {
			result |= predicate.execute(context);
		}

		return result;
	}
}
