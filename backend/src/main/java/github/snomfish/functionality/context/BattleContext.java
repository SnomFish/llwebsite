package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.event.EventId;

// this is the context of a loomian using a move
// this could be a record
public class BattleContext {
    
    private final Gamestate gamestate;
    private final BattleSide user;
    private final BattleSide enemy;
    private final IAction action;


    public BattleContext(
        Gamestate gamestate, 
        Side userSide, 
        Side targetSide,
        IAction action
    ) {
        this.gamestate = gamestate;
        this.user = new BattleSide(userSide);
        this.enemy = new BattleSide(targetSide);
        this.action = action;
    }


    public Gamestate getGamestate() {
        return gamestate;
    }
    public BattleSide getUser() {
        return user;
    }
    public BattleSide getEnemy() {
        return enemy;
    }
    public IAction getAction() {
        return action;
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
