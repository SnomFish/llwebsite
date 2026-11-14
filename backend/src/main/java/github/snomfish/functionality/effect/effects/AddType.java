package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class AddType implements IEffect {
	

	private Value<List<TypeId>> typesValue;
	private TypeId type;


	private AddType(
		Value<List<TypeId>> typesValue,
		TypeId type
	) {
		this.typesValue = typesValue;
		this.type = type;
	}


	private AddType(AddType copy) {
		this.type = copy.type;
	}
	public AddType deepCopy() {
		return new AddType(this);
	}


	public static IEffect toUser(TypeId type) {
		return new AddType(Value.USER().LOOMIAN().TYPES(), type);
	}
	public static IEffect toTarget(TypeId type) {
		return new AddType(Value.TARGET().LOOMIAN().TYPES(), type);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		List<Branch<BattleContext>> outcomes = new ArrayList<>();
		List<TypeId> types = typesValue.get(context);

		if (types.contains(type)) return List.of(new Branch<>(context, 1));

		types.add(type);
		BattleContext outcome = context.deepCopy();
		typesValue.set(outcome, types);
		outcomes.add(new Branch<>(outcome, 1));

		return outcomes;
	}
}
