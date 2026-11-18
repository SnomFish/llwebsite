package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class RemoveEnergy implements IEffect {
	

	private Value<Integer> currentEnergyValue;
	private INumber number;


	private RemoveEnergy(
		Value<Integer> currentEnergyValue,
		INumber number
	) {
		this.currentEnergyValue = currentEnergyValue;
		this.number = number;
	}


	public static RemoveEnergy user(INumber number) {
		return new RemoveEnergy(Value.USER().CURRENT_ENERGY(), number);
	}
	public static RemoveEnergy target(INumber number) {
		return new RemoveEnergy(Value.TARGET().CURRENT_ENERGY(), number);
	}


	private RemoveEnergy(RemoveEnergy copy) {
		this.currentEnergyValue = copy.currentEnergyValue;
		this.number = copy.number.deepCopy();
	}
	public RemoveEnergy deepCopy() {
		return new RemoveEnergy(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		List<Branch<BattleContext>> outcomes = new ArrayList<>();
		List<Branch<Double>> numberBranches = number.execute(context);
		int currentEnergy = currentEnergyValue.get(context);

		for (Branch<Double> numberBranch : numberBranches) {
			int damage = numberBranch.value().intValue();
			double damageProbability = numberBranch.probability();
			damage = Math.clamp(damage, 1, currentEnergy); // undefeined behaviour if the current energy is 0

			BattleContext outcome = context.deepCopy();
			currentEnergyValue.set(outcome, currentEnergy - damage);
			outcomes.add(new Branch<>(outcome, damageProbability));
		}

		return outcomes;
	}
}
