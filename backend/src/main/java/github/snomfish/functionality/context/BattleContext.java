package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.domain.weather.WeatherInstance;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.EventId;
import github.snomfish.functionality.gate.GateEvent;
import github.snomfish.functionality.gate.GateId;

import static github.snomfish.domain.SideId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.gateFlatMap;

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
    public WeatherInstance weather() {return gamestate.weatherInstance();}


    // events
    public List<Branch<BattleContext>> dispatchEvent(EventId eventId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = flatMap(outcomes, context -> weather().handleEvent(context, new Event(eventId, null)));
        outcomes = flatMap(outcomes, context -> user().dispatchEvent(context, new Event(eventId, ContextSideId.USER)));
        outcomes = flatMap(outcomes, context -> target().dispatchEvent(context, new Event(eventId, ContextSideId.TARGET)));

        return outcomes;
    }
    public List<Branch<Boolean>> dispatchGate(GateId gateId) {
        List<Branch<Boolean>> outcomes = List.of(new Branch<>(false, 1));

        outcomes = gateFlatMap(this, outcomes, context -> user().dispatchGate(context, new GateEvent(gateId, ContextSideId.USER)));
        outcomes = gateFlatMap(this, outcomes, context -> target().dispatchGate(context, new GateEvent(gateId, ContextSideId.TARGET)));

        return outcomes;
    }


    // helpers
    public void log(String entry) {gamestate.log(entry);}
    public void printLog() {gamestate.printLog();}
}
