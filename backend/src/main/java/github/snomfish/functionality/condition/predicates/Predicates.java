package github.snomfish.functionality.condition.predicates;

import java.util.List;

import github.snomfish.functionality.context.BattleContext;

public class Predicates {
	

	private Predicates() {}


	// returns true if all the conditions are true
	public static boolean resolve(BattleContext context, List<IPredicate> predicates) {
		for (IPredicate predicate : predicates) {
			if (!predicate.execute(context)) return false;
		}
		return true;
	}
}
