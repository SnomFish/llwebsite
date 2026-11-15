package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetHazards implements IEffect {
	

	public enum HazardId {
		ICICLE_TRAPS,
		POISON_BARBS,
		BARBS,
		BANANA_PEEL
	}


	private HazardId hazard;


	private SetHazards(HazardId hazard) {
		this.hazard = hazard;
	}


	private SetHazards(SetHazards copy) {
		this.hazard = copy.hazard;
	}
	public SetHazards deepCopy() {
		return new SetHazards(this);
	}


	public static SetHazards icicleTraps() {
		return new SetHazards(HazardId.ICICLE_TRAPS);
	}
	public static SetHazards poisonBarbs() {
		return new SetHazards(HazardId.POISON_BARBS);
	}
	public static SetHazards barbs() {
		return new SetHazards(HazardId.BARBS);
	}
	public static SetHazards bananaPeel() {
		return new SetHazards(HazardId.BANANA_PEEL);
	}


	// ALWAYS SETS HAZARDS ON THE TARGETS SIDE OF THE FIELD
	public List<Branch<BattleContext>> execute(BattleContext context) {
		BattleContext outcome = switch (hazard) {
			case ICICLE_TRAPS -> 	setIcicleTraps(context);
			case POISON_BARBS -> 	setPoisonBarbs(context);
			case BARBS -> 			setBarbs(context);
			case BANANA_PEEL ->		setBananaPeel(context);
		};

		return List.of(new Branch<>(outcome, 1.0));
	}


	private BattleContext setIcicleTraps(BattleContext context) {
		Mechanics mechanics = Value.TARGET().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.HALF_ICICLE_TRAP)) return context;
		if (mechanics.has(SideMechanicId.ICICLE_TRAP)) return context;

		BattleContext outcome = context.deepCopy();
		ApplyMechanic.toTargetSide(SideMechanicId.ICICLE_TRAP);

		return outcome;
	}


	private BattleContext setPoisonBarbs(BattleContext context) {
		Mechanics mechanics = Value.TARGET().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.DOUBLE_POISON_BARBS)) return context;

		BattleContext outcome = context.deepCopy();
		if (mechanics.has(SideMechanicId.SINGLE_POISON_BARBS)) {
			RemoveMechanic.fromTargetSide(SideMechanicId.SINGLE_POISON_BARBS);
			ApplyMechanic.toTargetSide(SideMechanicId.DOUBLE_POISON_BARBS);
		}
		else {
			ApplyMechanic.toTargetSide(SideMechanicId.SINGLE_POISON_BARBS);
		}

		return outcome;
	}


	private BattleContext setBarbs(BattleContext context) {
		Mechanics mechanics = Value.TARGET().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.TRIPLE_BARBS)) return context;

		BattleContext outcome = context.deepCopy();
		if (mechanics.has(SideMechanicId.DOUBLE_BARBS)) {
			RemoveMechanic.fromTargetSide(SideMechanicId.DOUBLE_BARBS);
			ApplyMechanic.toTargetSide(SideMechanicId.TRIPLE_BARBS);
		}
		else if (mechanics.has(SideMechanicId.SINGLE_BARBS)) {
			RemoveMechanic.fromTargetSide(SideMechanicId.SINGLE_BARBS);
			ApplyMechanic.toTargetSide(SideMechanicId.DOUBLE_BARBS);
		}
		else {
			ApplyMechanic.toTargetSide(SideMechanicId.SINGLE_BARBS);
		}

		return outcome;
	}


	private BattleContext setBananaPeel(BattleContext context) {
		Mechanics mechanics = Value.TARGET().SIDE_MECHANICS().get(context);
		if (mechanics.has(SideMechanicId.BANANA_PEEL)) return context;

		BattleContext outcome = context.deepCopy();
		ApplyMechanic.toTargetSide(SideMechanicId.BANANA_PEEL);

		return outcome;
	}
}
