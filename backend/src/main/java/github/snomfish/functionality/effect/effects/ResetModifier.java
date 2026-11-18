package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.modifiers.MoveModifierId;
import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class ResetModifier implements IEffect {
	

	private Value<Double> modifierValue;


	private ResetModifier(
		Value<Double> modifierValue
	) {
		this.modifierValue = modifierValue;
	}


	public static ResetModifier userMove(MoveModifierId id) {
		return new ResetModifier(Value.USER().MOVE().MOVE_MODIFIER(id));
	}
	public static ResetModifier targetMove(MoveModifierId id) {
		return new ResetModifier(Value.TARGET().MOVE().MOVE_MODIFIER(id));
	}
	public static ResetModifier userSide(SideModifierId id) {
		return new ResetModifier(Value.USER().SIDE_MODIFIER(id));
	}
	public static ResetModifier targetSide(SideModifierId id) {
		return new ResetModifier(Value.TARGET().SIDE_MODIFIER(id));
	}


	private ResetModifier(ResetModifier copy) {
		this.modifierValue = copy.modifierValue;
	}
	public ResetModifier deepCopy() {
		return new ResetModifier(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		modifierValue.set(outcome, 1.0);
		return wrap(outcome);
	}
}
