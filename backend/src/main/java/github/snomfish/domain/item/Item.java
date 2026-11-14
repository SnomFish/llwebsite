package github.snomfish.domain.item;

import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.tags.Tags;

// item is only ever called from the registry, therefore it should remain constant
// same with ability
public class Item extends Definition<ItemId, Item> {


    private Tags<ItemTagId> tags;


    public Item() {}


    // getters
    public Tags<ItemTagId> tags() {return tags;}


    // builder functions
    public static Item build() {
        return new Item();
    }
    public Item tag(ItemTagId tagId) {this.tags.add(tagId); return this;}
}
