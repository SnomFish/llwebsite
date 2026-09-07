package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Side;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.branch.Branch;

public class BattleSide {


    private Side side;


    public BattleSide(
        Side side
    ) {
        this.side = side;    
    }


    // getters
    public ActiveLoomian activeLoomian() {
        return side.activeLoomian();
    }


    // has
    public boolean hasStatus(StatusId status) {
        return activeLoomian().status().id() == status;
    }


    // events
    public List<Branch<BattleContext>> dispatch() {
        throw new UnsupportedOperationException("no implementation");
    }
}
