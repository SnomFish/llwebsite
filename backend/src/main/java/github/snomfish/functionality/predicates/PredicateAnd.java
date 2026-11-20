package github.snomfish.functionality.predicates;

import java.util.List;

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
}
