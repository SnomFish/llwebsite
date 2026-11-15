package github.snomfish.functionality.condition;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.context.BattleContext;

public class HasHazards implements ICondition {
	

	private Value<Mechanics> mechanicsValue;

	
	public HasHazards(
		Value<Mechanics> mechanicsValue
	) {
		this.mechanicsValue = mechanicsValue;
	}


	private HasHazards(HasHazards copy) {
		this.mechanicsValue = copy.mechanicsValue;
	}
	public HasHazards deepCopy() {
		return new HasHazards(this);
	}


	public static HasHazards user() {
		return new HasHazards(Value.USER().SIDE_MECHANICS());
	}
	public static HasHazards target() {
		return new HasHazards(Value.TARGET().SIDE_MECHANICS());
	}


	public double execute(BattleContext context) {
		boolean hasHazards = false;
		Mechanics mechanics = mechanicsValue.get(context);

		hasHazards = hasHazards || mechanics.has(SideMechanicId.ICICLE_TRAP);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.HALF_ICICLE_TRAP);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.SINGLE_POISON_BARBS);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.DOUBLE_POISON_BARBS);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.SINGLE_BARBS);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.DOUBLE_BARBS);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.TRIPLE_BARBS);
		hasHazards = hasHazards || mechanics.has(SideMechanicId.BANANA_PEEL);

		return hasHazards ? 1 : 0;
	}
}
