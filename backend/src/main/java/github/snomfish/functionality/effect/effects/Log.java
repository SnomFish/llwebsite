package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class Log implements IEffect {
    

    private String logMessage;


    public Log() {}


    public Log deepCopy() {
        return new Log();
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();

        outcome.gamestate().log(logMessage);

        return List.of(new Branch<>(outcome, 1));
    }
}
