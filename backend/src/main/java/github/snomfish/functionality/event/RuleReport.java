package github.snomfish.functionality.event;

import java.util.List;

import github.snomfish.domain.SideId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextUtil;
import github.snomfish.functionality.event.ruleIds.IRuleId;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;
import static github.snomfish.functionality.branch.BranchUtil.wrap;

public class RuleReport {
	

	public record Pair(
		Rule rule,
		SideId sideThatIsUser // relative to the original context
	) {}


	private SideId currentSideThatIsUser;
	private PriorityList<Pair> collection;
	
	
	public RuleReport(SideId sideId) {
		this.currentSideThatIsUser = sideId;
		this.collection = new PriorityList<>();
	}


	public void setCurrentSideThatIsUser(SideId sideId) {
		this.currentSideThatIsUser = sideId;
	}


	// adds a rule if it has the same triggering event as the one being dispatched
	public void record(List<Rule> rules, IRuleId ruleId) {
		for (Rule rule : rules) {
			if (rule.events().contains(ruleId)) {
				Pair pair = new Pair(rule, currentSideThatIsUser);
				int priority = rule.priority();
				
				collection.add(pair, priority);
			}
		}
	}


	public List<Branch<BattleContext>> execute(BattleContext context) {
		List<Branch<BattleContext>> outcomes = wrap(context);

		for (Pair pair : collection.values()) {
			outcomes = ContextUtil.setFocus(outcomes, pair.sideThatIsUser);
			outcomes = flatMap(outcomes, c -> pair.rule.execute(c));
		}

		return outcomes;
	}
}
