package github.snomfish.domain.item;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Report;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.tags.Tags;

import static github.snomfish.functionality.branch.BranchUtil.flatMap;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item {
    

    private ItemId id;
    private String name;
    private List<Rule> rules;
    private List<Gate> gates;
    private List<Interceptor> interceptors;
    private Tags<ItemTagId> tags;


    public Item() {
        this.id = ItemId.NO_ITEM;
        this.name = "no item";
        this.rules = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.interceptors = new ArrayList<>();
    }


    public ItemId id() {return id;}
    public String name() {return name;}
    public List<Rule> rules() {return rules;}
    public List<Gate> gates() {return gates;}
    public List<Interceptor> interceptors() {return interceptors;}
    public Tags<ItemTagId> tags() {return tags;}


    // builder functions
    public static Item build() {
        return new Item();
    }
    public Item id(ItemId id) {this.id = id; return this;}
    public Item name(String name) {this.name = name; return this;}
    public Item rule(Rule rule) {this.rules.add(rule); return this;}
    public Item gate(Gate gate) {this.gates.add(gate); return this;}
    public Item interceptor(Interceptor interceptor) {this.interceptors.add(interceptor); return this;}
    public Item tag(ItemTagId tag) {this.tags.add(tag); return this;}


    public List<Branch<BattleContext>> handleRule(BattleContext context, Event event) {
        List<Branch<BattleContext>> outcomes = List.of(new Branch<>(context, 1.0));
        
        for (Rule rule : rules) {
            outcomes = flatMap(outcomes, branchContext -> rule.execute(context, event));
        }

        return outcomes;
    }
    public List<Branch<Boolean>> handleGate(BattleContext context, Event event) {
        return null;
    }
    public List<Branch<BattleContext>> handleInterceptor(Report report, Event event) {
        return null;
    }
}
