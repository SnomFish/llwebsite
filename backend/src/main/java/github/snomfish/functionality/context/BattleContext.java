package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.SideId.*;

// this is the context of a loomian using a move
// this could be a record
public class BattleContext implements DeepCopyable<BattleContext> {
    
    private Gamestate gamestate;
    private SideId userState; 
    private Side user;
    private Side target;


    public BattleContext(
        Gamestate gamestate, 
        SideId userState
    ) {
        this.gamestate = gamestate;
        this.userState = userState;
        this.user = userState == PLAYER ? gamestate.playerSide() : gamestate.enemySide();
        this.target = userState == PLAYER ? gamestate.enemySide() : gamestate.playerSide();
    }


    @Override 
    public BattleContext deepCopy() {
        return new BattleContext(
            gamestate.deepCopy(), 
            userState
        );
    }


    // getters
    public Gamestate gamestate() {return gamestate;}
    public SideId userState() {return userState;}
    public Side user() {return user;}
    public Side target() {return target;}


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
