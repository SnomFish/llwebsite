package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class RemoveType implements IEffect {
	

	private Value<List<TypeId>> typesValue;
	private TypeId type;


	private RemoveType(
		Value<List<TypeId>> typesValue,
		TypeId type
	) {
		this.typesValue = typesValue;
		this.type = type;
	}


	private RemoveType(RemoveType copy) {
		this.type = copy.type;
	}
	public RemoveType deepCopy() {
		return new RemoveType(this);
	}


	public static IEffect fromUser(TypeId type) {
		return new RemoveType(Value.USER().LOOMIAN().TYPES(), type);
	}
	public static IEffect fromTarget(TypeId type) {
		return new RemoveType(Value.TARGET().LOOMIAN().TYPES(), type);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		List<Branch<BattleContext>> outcomes = new ArrayList<>();
		List<TypeId> types = typesValue.get(context);

		if (!types.contains(type)) return List.of(new Branch<>(context, 1));

		types.remove(type);
		BattleContext outcome = context.deepCopy();
		typesValue.set(outcome, types);
		outcomes.add(new Branch<>(outcome, 1));

		return outcomes;
	}
}
