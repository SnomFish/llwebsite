package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.SideId;
import github.snomfish.functionality._schematics.Conditions;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextUtil;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

public class HookReport {
    

    public record Pair(
		Hook hook,
		SideId sideThatIsUser // relative to the original context
	) {}


	private SideId currentSideThatIsUser;
	private PriorityList<Pair> collection;
	
	
	public HookReport(SideId sideId) {
		this.currentSideThatIsUser = sideId;
		this.collection = new PriorityList<>();
	}


	public void setCurrentSideThatIsUser(SideId sideId) {
		this.currentSideThatIsUser = sideId;
	}


	// adds a hook if it has the same triggering event as the one being dispatched
	public void record(List<Hook> hooks, IRuleId ruleId) {
		for (Hook hook : hooks) {
			if (hook.events().contains(ruleId)) {
				Pair pair = new Pair(hook, currentSideThatIsUser);
				int priority = hook.priority();
				
				collection.add(pair, priority);
			}
		}
	}




    public List<Branch<BattleContext>> execute(
        BattleContext context,
        List<Branch<BattleContext>> noHookOutcomes
    ) {
        return recursiveExecute(
            context,
            1,
            collection.values(),
            noHookOutcomes
        );
    }
    private List<Branch<BattleContext>> recursiveExecute(
        BattleContext context,
        double leftoverProbability,
        List<Pair> pairList,
        List<Branch<BattleContext>> noHookOutcomes
    ) {
        if (leftoverProbability <= 0) return new ArrayList<>(); // means that all previous interceptors have covered all possible gamestates
        if (pairList.size() == 0) return noHookOutcomes; // the failing function would go here

        Pair pair = pairList.get(0);
        pairList.remove(0);
        Hook hook = pair.hook;
        SideId sideId = pair.sideThatIsUser;
        BattleContext outcome = ContextUtil.setFocus(context, sideId);
        double probability = Conditions.resolve(outcome, hook.conditions());

        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        if (probability > 0) {
            outcomes.addAll(applyProbability(hook.effect().execute(outcome), probability * leftoverProbability));
        }
        if (probability < 1) {
            outcomes.addAll(recursiveExecute(
                context,
                (1-probability) * leftoverProbability,
                pairList,
                noHookOutcomes
            ));
        }

        return outcomes;
    }
}
