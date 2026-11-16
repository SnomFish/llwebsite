package github.snomfish.domain.move.structures;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;
import static github.snomfish.functionality.branch.BranchUtil.resolveConditions;

public class SingleHit implements IMove {
	

	private List<ICondition> conditions;
	private MoveEffect moveEffect;


	private SingleHit() {
		this.conditions = new ArrayList<>();
		this.moveEffect = new MoveEffect();
	}


	// getters
	// cba


	// builder functions
    public static SingleHit build() {
        return new SingleHit();
    }
    public SingleHit condition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }
    public SingleHit primary(IEffect effect) {
        this.moveEffect.primaries().add(effect);
        return this;
    }
    public SingleHit secondary(IEffect effect) {
        this.moveEffect.secondaries().add(effect);
        return this;
    }


	private SingleHit(SingleHit copy) {
		this.conditions = deepCopyList(conditions);
		this.moveEffect = moveEffect.deepCopy();
	}
	public SingleHit deepCopy() {
		return new SingleHit(this);
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		return resolveConditions(context, conditions, moveEffect::execute);
	}
}
