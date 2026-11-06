package github.snomfish.functionality.context;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.Gamestate;
import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;

public class ContextUtil {
    

    private ContextUtil() {}


    // takes a set of outcomes and sets the user and target states to new values (so user -> target and vice versa)
    // this is used for generic events
    public static List<Branch<BattleContext>> switchFocus(List<Branch<BattleContext>> contextBranches, SideId userState) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();

        for (Branch<BattleContext> contextBranch : contextBranches) {
            BattleContext context = contextBranch.value();
            Gamestate gamestate = context.gamestate();
            Double probability = contextBranch.probability();

            outcomes.add(new Branch<>(
                new BattleContext(gamestate, userState),
                probability
            ));
        }

        return outcomes;
    }
}
