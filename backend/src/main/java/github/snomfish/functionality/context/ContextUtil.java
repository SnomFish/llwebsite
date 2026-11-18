package github.snomfish.functionality.context;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;

public class ContextUtil {
    

    private ContextUtil() {}


    // takes a set of outcomes and sets the user and target states to new values (so user -> target and vice versa)
    // this is used for generic events
    public static List<Branch<BattleContext>> setFocus(List<Branch<BattleContext>> contextBranches, SideId theSideThatWillBecomeUser) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranches) {
            BattleContext context = contextBranch.value();
            Gamestate gamestate = context.gamestate();
            Double probability = contextBranch.probability();

            outcomes.add(new Branch<>(
                new BattleContext(gamestate, theSideThatWillBecomeUser),
                probability
            ));
        }

        return outcomes;
    }


    public static List<Branch<BattleContext>> setFocus(List<Branch<BattleContext>> contextBranches, ContextSideId theSideThatWillBecomeUser) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranches) {
            BattleContext context = contextBranch.value();
            Gamestate gamestate = context.gamestate();
            Double probability = contextBranch.probability();

            outcomes.add(new Branch<>(
                new BattleContext(gamestate, theSideThatWillBecomeUser.self(context)),
                probability
            ));
        }

        return outcomes;
    } 


    public static List<Branch<BattleContext>> setFocus(List<Branch<BattleContext>> contextBranches, Value<SideId> theSideThatWillBecomeUserValue) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranches) {
            BattleContext context = contextBranch.value();
            Gamestate gamestate = context.gamestate();
            Double probability = contextBranch.probability();
            SideId theSideThatWillBecomeUser = theSideThatWillBecomeUserValue.get(context);

            outcomes.add(new Branch<>(
                new BattleContext(gamestate, theSideThatWillBecomeUser),
                probability
            ));
        }

        return outcomes;
    } 


    public static BattleContext setFocus(BattleContext context, SideId sideId) {
        return new BattleContext(context.gamestate(), sideId);
    }


    public static BattleContext setFocus(BattleContext context, ContextSideId theSideThatWillBecomeUser) {
        return new BattleContext(context.gamestate(), theSideThatWillBecomeUser.self(context));
    }


    public static List<Branch<BattleContext>> reverseFocus(List<Branch<BattleContext>> contextBranches) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranches) {
            BattleContext context = contextBranch.value();
            Double probability =    contextBranch.probability();

            Gamestate gamestate = context.gamestate();

            outcomes.add(new Branch<>(
                new BattleContext(gamestate, context.targetState()),
                probability
            ));
        }

        return outcomes;
    }


    public static BattleContext reverseFocus(BattleContext context) {
        return new BattleContext(context.gamestate(), context.targetState());
    }


    
    
    public static List<Branch<Gamestate>> deContext(List<Branch<BattleContext>> contextBranchs) {
        List<Branch<Gamestate>> gamestates = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranchs) {
            BattleContext context = contextBranch.value();
            double contextProbability = contextBranch.probability();

            gamestates.add(new Branch<>(
                context.gamestate(),
                contextProbability
            ));
        }

        return gamestates;
    }
}
