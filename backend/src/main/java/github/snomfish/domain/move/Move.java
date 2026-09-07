package github.snomfish.domain.move;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.move.categories.MoveCategory;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.number.INumber;

public class Move implements IMove {
    

    private MoveId id;
    private IMove move;
    private ICondition condition;
    private MoveCategory category;
    private INumber priority;


    public Move(
        MoveId id,
        IMove move,
        MoveCategory category,
        INumber priority
    ) {
        this.id = id;
        this.move = move;
        this.category = category;
        this.priority = priority;
    }


    // getter
    public MoveId id() {return id;}
    public IMove move() {return move;}
    public MoveCategory category() {return category;}
    public INumber priority() {return priority;}


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<Boolean>> conditionBranches = condition.execute(context);
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<Boolean> branch : conditionBranches) {
            if (branch.value() == true) {
                // move does happen
                // gets the outcome of move and multiplies all the branches probability by the conditional probability
                outcomes.addAll(BranchUtil.mulProb(move.execute(context), branch.probability()));
            } else {
                // the move does not happen
                outcomes.add(new Branch<>(
                    context,
                    branch.probability()
                ));
            }
        }

        return outcomes;
    }
}
