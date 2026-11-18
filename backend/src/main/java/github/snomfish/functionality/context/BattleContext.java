package github.snomfish.functionality.context;

import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.copy.DeepCopyable;
import github.snomfish.functionality.event.HookReport;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;

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
    

    public List<Branch<BattleContext>> dispatchRule(ConstantRuleId ruleId, ContextSideId sideId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));
        
        outcomes = ContextUtil.setFocus(outcomes, sideId);
        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().dispatchRule(context, ruleId));
        outcomes = flatMap(outcomes, context -> gamestate().mechanics().dispatchRule(context, ruleId));

        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, ruleId));

        outcomes = ContextUtil.reverseFocus(outcomes);
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, ruleId));
        
        outcomes = ContextUtil.setFocus(outcomes, userState);
        return outcomes;
    }
    public List<Branch<BattleContext>> dispatchRule(RuleForAllId ruleId, ContextSideId sideId) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(this, 1.0));

        outcomes = ContextUtil.setFocus(outcomes, sideId);
        outcomes = flatMap(outcomes, context -> gamestate().weatherInstance().dispatchRule(context, ruleId));
        outcomes = flatMap(outcomes, context -> gamestate().mechanics().dispatchRule(context, ruleId));

        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, ruleId.forUser()));
        outcomes = flatMap(outcomes, context -> user().dispatchRule(context, ruleId));

        outcomes = ContextUtil.reverseFocus(outcomes); // target state always inverts the focus
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, ruleId.forTarget()));
        outcomes = flatMap(outcomes, context -> target().dispatchRule(context, ruleId));

        outcomes = ContextUtil.setFocus(outcomes, userState); // resets the focus to the correct state
        return outcomes;
    }




    public double dispatchGate(GateForAllId gateId, ContextSideId sideId) {
        double probability = 1;

        BattleContext context = ContextUtil.setFocus(this, sideId);
        probability *= gamestate().weatherInstance().dispatchGate(context, gateId);
        probability *= gamestate().mechanics().dispatchGate(context, gateId);
        probability *= user().dispatchGate(context, gateId.forUser());
        context = ContextUtil.reverseFocus(context);
        probability *= target().dispatchGate(context, gateId.forTarget());

        // outcomes is a boolean so dont fuck with the focus of the battle context rn (context is being discarded anyway so it doesnt matter)
        // preach
        return probability;
    }



    public List<Branch<BattleContext>> dispatchHook(
        RuleForAllId eventId,
        ContextSideId sideId,
        List<Branch<BattleContext>> trueOutcomes
    ) {
        HookReport report = new HookReport(trueOutcomes);

        BattleContext context = ContextUtil.setFocus(this, sideId);
        user().dispatchHook(report, context, eventId.forUser());

        context = ContextUtil.reverseFocus(context);
        target().dispatchHook(report, context, eventId.forTarget());

        context = ContextUtil.setFocus(context, userState); // resets the focus to the correct state
        return report.execute(context);
    }


    // helpers
    public void log(String entry) {gamestate.log(entry);}
    public void printLog() {gamestate.printLog();}
}
