package github.snomfish.functionality.tags;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

public class Tags<T extends Enum<T>> implements DeepCopyable<Tags<T>> {
    

    private Map<T, Boolean> tags;


    public Tags() {
        tags = new HashMap<>();
    }


    public Tags(Tags<T> copy) {
        this.tags = new HashMap<>(copy.tags);
    }
    @Override 
    public Tags<T> deepCopy() {
        return new Tags<>(this);
    }


    public boolean has(T id) {
        return tags.containsKey(id);
    }
    public Boolean get(T id) {
        if (!tags.containsKey(id)) tags.put(id, false);
        return tags.get(id);
    }
    public void set(T id, Boolean value) {
        tags.put(id, value);
    }
    public void add(T id) {
        tags.put(id, true);
    }
}
