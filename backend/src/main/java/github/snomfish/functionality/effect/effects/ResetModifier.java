package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.modifiers.ids.LoomModifierId;
import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.domain.modifiers.ids.SideModifierId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class ResetModifier implements IEffect {
	

	private List<Value<Double>> modifierValues;


	public ResetModifier() {
		this.modifierValues = new ArrayList<>();
	}


	public ResetModifier user(LoomModifierId id) {
		this.modifierValues.add(Value.USER().LOOMIAN_MODIFIER(id));
		return this;
	}
	public ResetModifier target(LoomModifierId id) {
		this.modifierValues.add(Value.TARGET().LOOMIAN_MODIFIER(id));
		return this;
	}
	public ResetModifier userSide(SideModifierId id) {
		this.modifierValues.add(Value.USER().SIDE_MODIFIER(id));
		return this;
	}
	public ResetModifier targetSide(SideModifierId id) {
		this.modifierValues.add(Value.TARGET().SIDE_MODIFIER(id));
		return this;
	}
	public ResetModifier userMove(MoveModifierId id) {
		this.modifierValues.add(Value.USER().MOVE().MOVE_MODIFIER(id));
		return this;
	}
	public ResetModifier targetMove(MoveModifierId id) {
		this.modifierValues.add(Value.TARGET().MOVE().MOVE_MODIFIER(id));
		return this;
	}


	private ResetModifier(ResetModifier copy) {
		this.modifierValues = new ArrayList<>(copy.modifierValues);
	}
	public ResetModifier deepCopy() {
		return new ResetModifier(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		for (Value<Double> modifierValue : modifierValues) {
			modifierValue.set(outcome, 1.0);
		}
		return wrap(outcome);
	}
}
