package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.event.EventId;

// this is the context of a loomian using a move
// this could be a record
public class BattleContext {
    
    private final Gamestate gamestate;
    private final BattleSide user;
    private final BattleSide target;


    public BattleContext(
        Gamestate gamestate, 
        Side userSide, 
        Side targetSide
    ) {
        this.gamestate = gamestate;
        this.user = new BattleSide(userSide);
        this.target = new BattleSide(targetSide);
    }


    public Gamestate gamestate() {
        return gamestate;
    }
    public BattleSide user() {
        return user;
    }
    public BattleSide target() {
        return target;
    }


    // events
    public List<Branch<BattleContext>> dispatch(EventId eventId) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public List<Branch<BattleContext>> dispatchToUser(EventId eventId) {
        throw new UnsupportedOperationException("not implemented yet");
    } 
    public List<Branch<BattleContext>> dispatchToTarget(EventId eventId) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
