package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ClearHazards implements IEffect {
	

	public Value<Mechanics> mechanicsValue;


	private ClearHazards(Value<Mechanics> mechanicsValue) {
		this.mechanicsValue = mechanicsValue;
	}


	public static ClearHazards fromUser() {
		return new ClearHazards(Value.USER().SIDE_MECHANICS());
	}
	public static ClearHazards fromTarget() {
		return new ClearHazards(Value.TARGET().SIDE_MECHANICS());
	}


	private ClearHazards(ClearHazards copy) {
		this.mechanicsValue = copy.mechanicsValue;
	}
	public ClearHazards deepCopy() {
		return new ClearHazards(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		Mechanics mechanics = mechanicsValue.get(outcome);

		mechanics.remove(SideMechanicId.ICICLE_TRAP);
		mechanics.remove(SideMechanicId.HALF_ICICLE_TRAP);
		mechanics.remove(SideMechanicId.SINGLE_POISON_BARBS);
		mechanics.remove(SideMechanicId.DOUBLE_POISON_BARBS);
		mechanics.remove(SideMechanicId.SINGLE_BARBS);
		mechanics.remove(SideMechanicId.DOUBLE_BARBS);
		mechanics.remove(SideMechanicId.TRIPLE_BARBS);
		mechanics.remove(SideMechanicId.BANANA_PEEL);

		return List.of(new Branch<>(outcome, 1));
	}
}
