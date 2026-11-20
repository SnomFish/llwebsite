package github.snomfish.functionality.condition.predicates;

import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface IPredicate extends DeepCopyable<IPredicate> {
	

	boolean execute(BattleContext context);
}
