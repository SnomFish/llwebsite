package github.snomfish.domain.move;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public abstract class IMove implements DeepCopyable<IMove> {
    

    public int executionStage() {return 0;}
    public void setExecutionStage(int executionStage) {}


    public abstract List<Branch<BattleContext>> execute(BattleContext context);
}
