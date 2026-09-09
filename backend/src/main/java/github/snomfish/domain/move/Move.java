package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.functionality.branch.BranchUtil.*;

public class Move implements DeepCopyable<Move> {
    

    private MoveId id;
    private ICondition condition;
    private TypeId type;
    private CategoryId category;
    private INumber priority;

    private IMove beforeHit;
    private IMove onHit;
    private IMove afterHit;
    private IMove onMiss;


    public Move(
        MoveId id,
        ICondition condition,
        TypeId type,
        CategoryId category,
        INumber priority,
        IMove beforeHit,
        IMove onHit,
        IMove afterHit,
        IMove onMiss
    ) {
        this.id = id;
        this.condition = condition;
        this.type = type;
        this.category = category;
        this.priority = priority;

        this.beforeHit = beforeHit;
        this.onHit = onHit;
        this.afterHit = afterHit;
        this.onMiss = onMiss;
    }


    @Override 
    public Move deepCopy() {
        return new Move(
            id,
            condition.deepCopy(),
            type,
            category,
            priority.deepCopy(),

            beforeHit.deepCopy(),
            onHit.deepCopy(),
            afterHit.deepCopy(),
            onMiss.deepCopy()
        );
    }


    // getter
    public MoveId id() {return id;}
    public ICondition condition() {return condition;}
    public TypeId type() {return type;}
    public CategoryId category() {return category;}
    public INumber priority() {return priority;}
    public IMove beforeHit() {return beforeHit;}
    public IMove onHit() {return onHit;}
    public IMove afterHit() {return afterHit;}
    public IMove onMiss() {return onMiss;}


    // setter
    public void setMoveId(MoveId moveId) {this.id = moveId;}
    public void setType(TypeId type) {this.type = type;}
    public void setCategory(CategoryId category) {this.category = category;}


    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> beforeHitOutcomes = beforeHit.execute(context);
        List<Branch<BattleContext>> moveOutcomes = new ArrayList<>();

        for (Branch<Boolean> conditionBranch : condition.execute(context)) {
            if (conditionBranch.value()) {
                moveOutcomes = applyProbability(
                    flatMap(beforeHitOutcomes, c -> onHit.execute(c)), conditionBranch);
            } else {
                moveOutcomes = applyProbability(
                    flatMap(beforeHitOutcomes, c -> onMiss.execute(c)), conditionBranch);
            }
        }

        return flatMap(moveOutcomes, c -> afterHit.execute(c));
    }
}
