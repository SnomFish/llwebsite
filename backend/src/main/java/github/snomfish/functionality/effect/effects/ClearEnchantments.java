package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class ClearEnchantments implements IEffect {
	

	public Value<Mechanics> mechanicsValue;


	private ClearEnchantments(Value<Mechanics> mechanicsValue) {
		this.mechanicsValue = mechanicsValue;
	}


	public static ClearEnchantments fromUser() {
		return new ClearEnchantments(Value.USER().SIDE_MECHANICS());
	}
	public static ClearEnchantments fromTarget() {
		return new ClearEnchantments(Value.TARGET().SIDE_MECHANICS());
	}


	private ClearEnchantments(ClearEnchantments copy) {
		this.mechanicsValue = copy.mechanicsValue;
	}
	public ClearEnchantments deepCopy() {
		return new ClearEnchantments(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		Mechanics mechanics = mechanicsValue.get(outcome);

		mechanics.remove(SideMechanicId.NEAR_ENCHANTMENT);
		mechanics.remove(SideMechanicId.FAR_ENCHANTMENT);

		return List.of(new Branch<>(outcome, 1));
	}
}
