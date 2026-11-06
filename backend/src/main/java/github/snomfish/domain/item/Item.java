package github.snomfish.domain.item;

import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item extends Definition<ItemId> {
    
    public Item(ItemId id) {
        super(id);
    }


    // builder functions
    public static Item build(ItemId id) {
        return new Item(id);
    }
    public Item name(String name) {
        this.name = name;
        return this;
    }
    public Item rule(Rule rule) {
        this.rules.add(rule);
        return this;
    }
    public Item gate(Gate gate) {
        this.gates.add(gate);
        return this;
    }
    public Item interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }
}
