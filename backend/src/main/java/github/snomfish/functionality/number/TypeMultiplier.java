package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class TypeMultiplier implements INumber {
	

	private Value<List<TypeId>> typesValue;
	private TypeId attackingType;


	private TypeMultiplier(
		Value<List<TypeId>> typesValue,
		TypeId attackingType
	) {
		this.typesValue = typesValue;
		this.attackingType = attackingType;
	}


	public static TypeMultiplier onUser(TypeId type) {
		return new TypeMultiplier(Value.USER().TYPES(), type);
	}
	public static TypeMultiplier onTarget(TypeId type) {
		return new TypeMultiplier(Value.TARGET().TYPES(), type);
	}


	private TypeMultiplier(TypeMultiplier copy) {
		this.typesValue = copy.typesValue;
		this.attackingType = copy.attackingType;
	}
	public TypeMultiplier deepCopy() {
		return new TypeMultiplier(this);
	}


	public List<Branch<Double>> execute(BattleContext context) {
		List<TypeId> defendingTypes = typesValue.get(context);
		TypeChart typeChart = TypeChart.get();

		return List.of(new Branch<>(typeChart.getModifier(attackingType, defendingTypes), 1));
	}
}
