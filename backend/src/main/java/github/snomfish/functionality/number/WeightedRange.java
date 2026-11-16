package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;
import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class WeightedRange implements INumber {
	

	private List<INumber> numbers;
	private List<Double> probabilities;

	private WeightedRange() {
		this.numbers = new ArrayList<>();
		this.probabilities = new ArrayList<>();
	}


	public static WeightedRange build() {
		return new WeightedRange();
	}
	public WeightedRange add(INumber number, double probability) {
		this.numbers.add(number);
		this.probabilities.add(probability);
		return this;
	}


	private WeightedRange(WeightedRange copy) {
		this.numbers = deepCopyList(copy.numbers);
		this.probabilities = new ArrayList<>(copy.probabilities);
	}
	public WeightedRange deepCopy() {
		return new WeightedRange(this);
	}


	public List<Branch<Double>> execute(BattleContext context) {
		List<Branch<Double>> outcomes = new ArrayList<>();

		int n = numbers.size();
		for (int i = 0; i < n; i ++) {
			INumber number = numbers.get(i);
			double probability = probabilities.get(i);
			outcomes.addAll(applyProbability(number.execute(context), probability));
		}

		return outcomes;
	}
}
