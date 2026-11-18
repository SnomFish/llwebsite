package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class RemoveHealth implements IEffect {
	

	private Value<Integer> currentHealthValue;
	private INumber number;


	private RemoveHealth(
		Value<Integer> currentHealthValue,
		INumber number
	) {
		this.currentHealthValue = currentHealthValue;
		this.number = number;
	}


	public static RemoveHealth user(INumber number) {
		return new RemoveHealth(Value.USER().CURRENT_HEALTH(), number);
	}
	public static RemoveHealth target(INumber number) {
		return new RemoveHealth(Value.TARGET().CURRENT_HEALTH(), number);
	}


	private RemoveHealth(RemoveHealth copy) {
		this.currentHealthValue = copy.currentHealthValue;
		this.number = copy.number.deepCopy();
	}
	public RemoveHealth deepCopy() {
		return new RemoveHealth(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		List<Branch<BattleContext>> outcomes = new ArrayList<>();
		List<Branch<Double>> numberBranches = number.execute(context);
		int currentHealth = currentHealthValue.get(context);

		for (Branch<Double> numberBranch : numberBranches) {
			int damage = numberBranch.value().intValue();
			double damageProbability = numberBranch.probability();
			damage = Math.clamp(damage, 1, currentHealth); // undefeined behaviour if the current health is 0

			BattleContext outcome = context.deepCopy();
			currentHealthValue.set(outcome, currentHealth - damage);
			outcomes.add(new Branch<>(outcome, damageProbability));
		}

		return outcomes;
	}
}
