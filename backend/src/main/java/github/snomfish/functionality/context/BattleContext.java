package github.snomfish.functionality.context;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.event.EventSideId;

import static github.snomfish.domain.SideId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

// this is the context of a loomian using a move
// this could be a record // no it cant, I like mutation
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
    public List<Branch<BattleContext>> dispatchEvent(EventId eventId) { // I could crush this down to one line
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = flatMap(outcomes, context -> user().dispatchEvent(context, new Event(eventId, EventSideId.USER)));
        outcomes = flatMap(outcomes, context -> target().dispatchEvent(context, new Event(eventId, EventSideId.TARGET)));

        return outcomes;
    }


    // helpers
    public void log(String entry) {gamestate.log(entry);}
    public void printLog() {gamestate.printLog();}
}
