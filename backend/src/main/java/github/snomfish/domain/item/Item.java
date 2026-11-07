package github.snomfish.domain.item;

import github.snomfish.functionality.event.Definition;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item extends Definition<ItemId, Item> {
    
    public Item(ItemId id) {
        super(id);
    }


    // builder functions
    public static Item build(ItemId id) {
        return new Item(id);
    }
}
