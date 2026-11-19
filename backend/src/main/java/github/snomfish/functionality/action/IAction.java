package github.snomfish.functionality.action;

import java.util.List;

import github.snomfish.domain.move.Move;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.copy.DeepCopyable;

public interface IAction extends DeepCopyable<IAction> {
    
    ActionId id();
    int priority();
    Move move();
    int switchTarget();
    boolean willSoulBurst();

    void setPriority(int priority);
    void setMove(Move move);
    void setSwitchTarget(int switchTarget);
    void setWillSoulBurst(boolean willSouldBurst);

    boolean isSelectable(BattleContext context);

    List<Branch<BattleContext>> execute(BattleContext context);
}
