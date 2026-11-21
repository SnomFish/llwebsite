package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality._schematics.Predicates;
import github.snomfish.functionality.condition.predicates.IPredicate;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.gateIds.IGateId;

public class Door {
	

	private List<IGateId> events;
	private List<IPredicate> predicates;


	public Door() {
		this.events = new ArrayList<>();
		this.predicates = new ArrayList<>();
	}


	public List<IGateId> events() {return events;}
	public List<IPredicate> predicates() {return predicates;}


	// builder functions
	public static Door build() {
		return new Door();
	}
	public Door event(IGateId event) {this.events.add(event); return this;}
	public Door predicate(IPredicate predicate) {this.predicates.add(predicate); return this;}


	public boolean execute(BattleContext context, IGateId gateId) {

        if (!events.contains(gateId)) return false;

        return Predicates.resolve(context, predicates);
    }
}
