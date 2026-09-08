package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.event.EventId;

import static github.snomfish.domain.SideId.*;

// this is the context of a loomian using a move
// this could be a record
public class BattleContext {
    
    private Gamestate gamestate;
    private SideId userState; 
    private BattleSide user;
    private BattleSide target;


    public BattleContext(
        Gamestate gamestate, 
        SideId userState
    ) {
        this.gamestate = gamestate;
        this.userState = userState;
        this.user = new BattleSide(userState == PLAYER ? gamestate.playerSide() : gamestate.enemySide());
        this.target = new BattleSide(userState == PLAYER ? gamestate.enemySide() : gamestate.playerSide());
    }


    // getters
    public Gamestate gamestate() {return gamestate;}
    public SideId userState() {return userState;}
    public BattleSide user() {return user;}
    public BattleSide target() {return target;}


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
