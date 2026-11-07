package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.InterceptorReport;
import github.snomfish.functionality.event.ids.GateId;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.InterceptorId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.event.ids.RuleId;

import static github.snomfish.domain.SideId.*;
import static github.snomfish.functionality.branch.BranchUtil.flatMap;

// this is the context of a loomian using a move
// this could be a record
// no it cant, I like mutation
public class BattleContext implements DeepCopyable<BattleContext> {
    

    private Gamestate gamestate;
    private SideId userState;
    private SideId targetState;
    private Side user;
    private Side target;


    public BattleContext(
        Gamestate gamestate, 
        SideId userState
    ) {
        this.gamestate = gamestate;
        this.userState = userState;
        this.targetState = userState == PLAYER ? PLAYER : ENEMY;
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
    public SideId targetState() {return targetState;}
    public Side user() {return user;}
    public Side target() {return target;}
    public Side side(ContextSideId sideId) {
        return switch (sideId) {
            case USER -> user;
            case TARGET -> target;
        };
    }


    // events
    /* 
    THESE EVENTS WORK FOR EVENTS LIKE "WEATHER_STARTED" WHERE THE EVENT NEEDS TO APPLY TO BOTH PARTIES
    AND THE EVENT AFFECTS BOTH PARTIES EVENLY

    FOR A TRUELY GENERIC EVENT A NEW SYSTEM NEEDS TO BE USED
    EACH EVENT MUST HAVE A COUNTER PART, E.G: "SWAPPED_IN" AND "THE_OTHER_GUY_SWAPPED_IN"

    THE INITIAL FUNCTION CALL NEEDS TO SPECIFY WETHER THE USER OR THE TARGET GETS THE FIRST EVENT ("SWAPPED IN")
    AND THE OTHER SIDE GETS THE OTHER FUNCTION
    
    AFTER THAT THE EXTRA SIDE_CONTEXT_ID IS NOT NEEDED
    */
    public List<Branch<BattleContext>> dispatchEvent(RuleId ruleId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().handleEvent(context, new Event(ruleId, null)));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId, ContextSideId.USER)));
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId, ContextSideId.TARGET)));

        return outcomes;
    }
    public List<Branch<BattleContext>> dispatchEvent(IsolatedId ruleId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().handleEvent(context, new Event(ruleId, null)));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId, null)));
        outcomes = ContextUtil.switchFocus(outcomes, targetState);
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId, null)));
        
        outcomes = ContextUtil.switchFocus(outcomes, userState);
        return outcomes;
    }
    public List<Branch<BattleContext>> dispatchEvent(GenericId ruleId, ContextSideId sideIds) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = ContextUtil.switchFocus(outcomes, sideIds.self(this));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId, null)));
        outcomes = ContextUtil.switchFocus(outcomes, targetState); // target state always inverts the focus
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId.oppId(), null)));

        outcomes = ContextUtil.switchFocus(outcomes, userState); // resets the focus to the correct state
        return outcomes;
    }




    public List<Branch<Boolean>> dispatchGate(GateId gateId) {
        List<Branch<Boolean>> outcomes = List.of(new Branch<>(true, 1));

        outcomes = user().dispatchGate(this, new Event(gateId, ContextSideId.USER), outcomes);
        outcomes = target().dispatchGate(this, new Event(gateId, ContextSideId.TARGET), outcomes);

        return outcomes;
    }
    public List<Branch<BattleContext>> dispatchInterceptor(InterceptorId interceptorId, IEffect trueEffect) {
        InterceptorReport report = new InterceptorReport(this, trueEffect);

        report = user().dispatchInterceptor(report, new Event(interceptorId, ContextSideId.USER));
        report = user().dispatchInterceptor(report, new Event(interceptorId, ContextSideId.TARGET));

        return report.execute();
    }


    // helpers
    public void log(String entry) {gamestate.log(entry);}
    public void printLog() {gamestate.printLog();}
}
