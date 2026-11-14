package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class Heal implements IEffect {
	

	Value<ActiveLoomian> healingTarget;
	ContextSideId sideId;
	INumber healingNumber;


	private Heal(
		Value<ActiveLoomian> healingTarget,
		ContextSideId sideId,
		INumber healingNumber
	) {
		this.healingTarget = healingTarget;
		this.sideId = sideId;
		this.healingNumber = healingNumber;
	}


	private Heal(Heal copy) {
		this.healingTarget = copy.healingTarget;
		this.sideId = copy.sideId;
		this.healingNumber = copy.healingNumber.deepCopy();
	}
	public Heal deepCopy() {
		return new Heal(this);
	}


	public static IEffect user(INumber healingNumber) {
		return new Heal(
			Value.USER().LOOMIAN(),
			ContextSideId.USER,
			healingNumber
		);
	}
	public static IEffect target(INumber healingNumber) {
		return new Heal(
			Value.TARGET().LOOMIAN(),
			ContextSideId.TARGET,
			healingNumber
		);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		double nonHealingProbability = 1;
		List<Branch<BattleContext>> outcomes = new ArrayList<>();
		List<Branch<Double>> healingBranches = healingNumber.execute(context);

		for (Branch<Double> healingBranch : healingBranches) {
			double healingValue = healingBranch.value();
			double probability = healingBranch.probability();

			ActiveLoomian loomian    = healingTarget.get(context);
			int currentHealth        = loomian.currentHealth();
			int maxHealth            = loomian.maxHealth();
			int heal				 = Math.clamp((int)healingValue, 0, maxHealth - currentHealth);

			if (heal == 0) { // skips healing if no healing would be done
				nonHealingProbability *= probability; 
				continue;
			}

			BattleContext outcome = context.deepCopy();
			currentHealth += heal;
			healingTarget.CURRENT_HEALTH().set(outcome, currentHealth);

			outcomes.add(new Branch<>(
				outcome,
				probability
			));
		}

		// one state for if healing does not occur
		if (nonHealingProbability > 0) {
			outcomes.add(new Branch<>(
				context.deepCopy(),
				nonHealingProbability
			));
		}
		
		return outcomes;
	}
}
