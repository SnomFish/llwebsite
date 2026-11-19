package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriorityList<T> {
	

	private Map<Integer, List<T>> list;


	public PriorityList() {}


	public void add(T value, int priority) {
		if (!list.containsKey(priority)) list.put(priority, new ArrayList<>());
		list.get(priority).add(value);
	}


	// returns all the values, highest priority first
	// values with hte same priority are ordered by when they were added (default list<>) 
	public List<T> values() {
		return list.entrySet().stream()
            .sorted(Map.Entry.<Integer, List<T>>comparingByKey().reversed())
            .flatMap(entry -> entry.getValue().stream())
            .toList();
	}
}
