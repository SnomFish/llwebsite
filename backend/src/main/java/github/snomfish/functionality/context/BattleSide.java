package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Loomian;
import github.snomfish.domain.Side;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.branch.Branch;

public class BattleSide {


    private Side side;


    public BattleSide(Side side) {
        this.side = side;    
    }


    // getters
    public Loomian getActiveLoomian() {
        return side.getActiveLoomian();
    }


    // has
    public boolean hasStatus(StatusId status) {
        return getActiveLoomian().status().id() == status;
    }


    // events
    public List<Branch<BattleContext>> dispatch() {
        throw new UnsupportedOperationException("no implementation");
    }
}
