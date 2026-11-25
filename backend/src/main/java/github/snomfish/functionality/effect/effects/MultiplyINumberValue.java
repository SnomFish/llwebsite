package github.snomfish.functionality.effect.effects;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;

public class MultiplyINumberValue implements IEffect {
	

	private Value<INumber> numberValue;
	private INumber multiplier;


	public MultiplyINumberValue(
		Value<INumber> numberValue,
		INumber multiplier
	) {
		this.numberValue = numberValue;
		this.multiplier = multiplier;
	}
	public MultiplyINumberValue(
		Value<INumber> numberValue,
		double multiplier
	) {
		this.numberValue = numberValue;
		this.multiplier = new Constant(multiplier);
	}


	private MultiplyINumberValue(MultiplyINumberValue copy) {
		this.numberValue = copy.numberValue;
		this.multiplier = copy.multiplier.deepCopy();
	}
	public MultiplyINumberValue deepCopy() {
		return new MultiplyINumberValue(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();

		INumber value = numberValue.get(outcome);
		numberValue.set(outcome, new Multiply(value, multiplier));

		return wrap(outcome);
	}
}
