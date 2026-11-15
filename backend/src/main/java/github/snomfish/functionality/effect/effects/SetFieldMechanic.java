package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

public class SetFieldMechanic implements IEffect {
	

	public enum MechanicId {
		BANE_OF_HASTE,
		BANE_OF_WIELDING,
		FUNGUS_CURSE;
	}


	private MechanicId mechanic;
	private int maxAge;


	// THIS ALWAYS ASSUMES THE USER OF THE CONTEXT IS THE SETTER
	public SetFieldMechanic(MechanicId mechanic) {
		this.mechanic = mechanic;
		this.maxAge = 999;
	}


	public static SetFieldMechanic baneOfHaste() {
		return new SetFieldMechanic(MechanicId.BANE_OF_HASTE);
	}
	public static SetFieldMechanic baneOfWielding() {
		return new SetFieldMechanic(MechanicId.BANE_OF_WIELDING);
	}
	public static SetFieldMechanic fungusCurse() {
		return new SetFieldMechanic(MechanicId.FUNGUS_CURSE);
	}
	public SetFieldMechanic withMaxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}


	private SetFieldMechanic(SetFieldMechanic copy) {
		this.mechanic = copy.mechanic;
	}
	public SetFieldMechanic deepCopy() {
		return new SetFieldMechanic(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		return switch (mechanic) {
			case BANE_OF_HASTE -> 		setBaneOfHaste(context);
			case BANE_OF_WIELDING -> 	setBaneOfWielding(context);
			case FUNGUS_CURSE -> 		setFungusCurse(context);
		};
	}


	public List<Branch<BattleContext>> setBaneOfHaste(BattleContext context) {
		Mechanics mechanics = context.gamestate().mechanics();
		List<Branch<BattleContext>> outcomes;

		if (!mechanics.has(FieldMechanicId.BANE_OF_HASTE)) {
			outcomes = ApplyMechanic.toField(FieldMechanicId.BANE_OF_HASTE).withMaxAge(maxAge).execute(context);
			outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_BANE_OF_HASTE, ContextSideId.USER));

		} else { // if bane of haste is already up, remove it
			outcomes = RemoveMechanic.fromField(FieldMechanicId.BANE_OF_HASTE).execute(context);
		}

		return outcomes;
	}


	public List<Branch<BattleContext>> setBaneOfWielding(BattleContext context) {
		Mechanics mechanics = context.gamestate().mechanics();
		List<Branch<BattleContext>> outcomes;

		if (!mechanics.has(FieldMechanicId.BANE_OF_WIELDING)) {
			outcomes = ApplyMechanic.toField(FieldMechanicId.BANE_OF_WIELDING).withMaxAge(maxAge).execute(context);
			outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_BANE_OF_WIELDING, ContextSideId.USER));

		} else { // if bane of wielding is already up, remove it
			outcomes = RemoveMechanic.fromField(FieldMechanicId.BANE_OF_WIELDING).execute(context);
		}

		return outcomes;
	}


	public List<Branch<BattleContext>> setFungusCurse(BattleContext context) {
		Mechanics mechanics = context.gamestate().mechanics();
		List<Branch<BattleContext>> outcomes;

		if (!mechanics.has(FieldMechanicId.FUNGUS_CURSE)) {
			outcomes = ApplyMechanic.toField(FieldMechanicId.FUNGUS_CURSE).withMaxAge(maxAge).execute(context);
			outcomes = flatMap(outcomes, c -> c.dispatchEvent(RuleForAllId.LOOMIAN_SET_FUNGUS_CURSE, ContextSideId.USER));

		} else { // if fungus curse is already set up, remove it
			outcomes = RemoveMechanic.fromField(FieldMechanicId.FUNGUS_CURSE).execute(context);
		}

		return outcomes;
	}
}
