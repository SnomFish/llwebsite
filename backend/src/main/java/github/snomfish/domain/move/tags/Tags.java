package github.snomfish.domain.move.tags;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

public class Tags implements DeepCopyable<Tags> {
    

    private Map<TagId, Boolean> tags;


    public Tags() {
        tags = new HashMap<>();
    }
    public Tags(Tags copy) {
        this.tags = new HashMap<>(copy.tags);
    }


    @Override 
    public Tags deepCopy() {
        return new Tags(this);
    }


    public Boolean get(TagId id) {
        return tags.get(id);
    }
    public void add(TagId id) {
        tags.put(id, true);
    }
}
