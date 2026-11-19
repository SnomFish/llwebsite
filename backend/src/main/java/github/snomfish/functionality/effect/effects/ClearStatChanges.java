package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class ClearStatChanges implements IEffect {
	

	private enum Mode {
		DROPS,
		CHANGES,
		BOOSTS,;
	}


	private Mode mode;
	private Value<ActiveLoomian> targetValue;


	private ClearStatChanges(
		Mode mode,
		Value<ActiveLoomian> targetValue
	) {
		this.mode = mode;
		this.targetValue = targetValue;
	}


	public static ClearStatChanges userDrops() {
		return new ClearStatChanges(Mode.DROPS, Value.USER().LOOMIAN());
	}
	public static ClearStatChanges userBoosts() {
		return new ClearStatChanges(Mode.BOOSTS, Value.USER().LOOMIAN());
	}
	public static ClearStatChanges userChanges() {
		return new ClearStatChanges(Mode.CHANGES, Value.USER().LOOMIAN());
	}
	public static ClearStatChanges targetDrops() {
		return new ClearStatChanges(Mode.DROPS, Value.TARGET().LOOMIAN());
	}
	public static ClearStatChanges targetBoosts() {
		return new ClearStatChanges(Mode.BOOSTS, Value.TARGET().LOOMIAN());
	}
	public static ClearStatChanges targetChanges() {
		return new ClearStatChanges(Mode.CHANGES, Value.TARGET().LOOMIAN());
	}


	private ClearStatChanges(ClearStatChanges copy) {
		this.targetValue = copy.targetValue;
	}
	public ClearStatChanges deepCopy() {
		return new ClearStatChanges(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		return switch(mode) {
			case DROPS -> clearStatDrops(context);
			case BOOSTS -> clearStatBoosts(context);
			case CHANGES -> clearStatChanges(context);
		};
	}


	private List<Branch<BattleContext>> clearStatDrops(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		ActiveLoomian loomian = targetValue.get(outcome);

		for (StatChangesId statChangeId : StatChangesId.values()) {
			if (loomian.statChanges().stage(statChangeId) < 0) {
				loomian.statChanges().set(statChangeId, 0);
			}
		}
		
		return wrap(outcome);
	}
	private List<Branch<BattleContext>> clearStatBoosts(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		ActiveLoomian loomian = targetValue.get(outcome);

		for (StatChangesId statChangeId : StatChangesId.values()) {
			if (loomian.statChanges().stage(statChangeId) > 0) {
				loomian.statChanges().set(statChangeId, 0);
			}
		}
		
		return wrap(outcome);
	}
	private List<Branch<BattleContext>> clearStatChanges(BattleContext context) {
		BattleContext outcome = context.deepCopy();
		ActiveLoomian loomian = targetValue.get(outcome);

		for (StatChangesId statChangeId : StatChangesId.values()) {
			loomian.statChanges().set(statChangeId, 0);
		}
		
		return wrap(outcome);
	}
}
