package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;
import static github.snomfish.functionality.copy.DeepCopy.resolveDeepCopy;
import static github.snomfish.functionality.Value.resolve;

public class SetValue<T> implements IEffect {
	

	private Value<T> value;
	private T object;


	public SetValue(
		Value<T> value,
		T object
	) {
		this.value = value;
		this.object = object;
	}
	@SuppressWarnings("unchecked")
	public SetValue(
		Value<T> value,
		Value<T> object
	) {
		this.value = value;
		this.object = (T) object;
	}


	// TODO write a static function in copy to take in a generic and if it can be deep copied, to deep copy it
	private SetValue(SetValue<T> copy) {
		this.value = copy.value;
		this.object = resolveDeepCopy(copy.object);
	}
	public SetValue<T> deepCopy() {
		return new SetValue<>(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		value.set(outcome, resolve(object, outcome));

		return wrap(outcome);
	}
}
