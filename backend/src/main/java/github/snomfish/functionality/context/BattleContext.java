package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.Report;
import github.snomfish.functionality.event.eventIds.ConstantRuleId;
import github.snomfish.functionality.event.eventIds.RuleForAllId;
import github.snomfish.functionality.event.gateIds.GateForAllId;

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


    public void incrementTurnNumber() {
        this.gamestate.incrementTurnNumber();
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
    public List<Branch<BattleContext>> dispatchEvent(ConstantRuleId ruleId, ContextSideId sideId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));
        
        outcomes = ContextUtil.setFocus(outcomes, sideId);
        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().handleEvent(context, new Event(ruleId, null)));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId, null)));
        outcomes = ContextUtil.reverseFocus(outcomes);
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId, null)));
        
        outcomes = ContextUtil.setFocus(outcomes, userState);
        return outcomes;
    }
    public List<Branch<BattleContext>> dispatchEvent(RuleForAllId ruleId, ContextSideId sideId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = ContextUtil.setFocus(outcomes, sideId);
        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().handleEvent(context, new Event(ruleId, null)));

        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId.forUser(), null)));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, new Event(ruleId, null)));

        outcomes = ContextUtil.reverseFocus(outcomes); // target state always inverts the focus
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId.forTarget(), null)));
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, new Event(ruleId, null)));

        outcomes = ContextUtil.setFocus(outcomes, userState); // resets the focus to the correct state
        return outcomes;
    }



    public double dispatchGate(GateForAllId gateId, ContextSideId sideId) {
        double probability = 1;

        BattleContext context = ContextUtil.setFocus(this, sideId);
        probability = gamestate().weatherInstance().dispatchGate(context, gateId, probability);
        probability = user().dispatchGate(context, gateId.forUser(), probability);
        context = ContextUtil.reverseFocus(context);
        probability = target().dispatchGate(context, gateId.forTarget(), probability);

        // outcomes is a boolean so dont fuck with the focus of the battle context rn
        // preach
        return probability;
    }



    public List<Branch<BattleContext>> dispatchInterceptor(
        RuleForAllId eventId,
        ContextSideId sideId,
        List<Branch<BattleContext>> trueOutcomes
    ) {
        Report report = new Report(trueOutcomes);

        BattleContext context = ContextUtil.setFocus(this, sideId);
        report = user().dispatchInterceptor(report, context, new Event(eventId.forUser(), null));

        context = ContextUtil.reverseFocus(context);
        report = target().dispatchInterceptor(report, context, new Event(eventId.forTarget(), null));

        context = ContextUtil.setFocus(context, userState); // resets the focus to the correct state
        return report.execute(context);
    }


    // helpers
    public void log(String entry) {gamestate.log(entry);}
    public void printLog() {gamestate.printLog();}
}
