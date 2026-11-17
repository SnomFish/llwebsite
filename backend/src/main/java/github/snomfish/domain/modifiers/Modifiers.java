package github.snomfish.domain.modifiers;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

public class Modifiers<T extends IModifierId> implements DeepCopyable<Modifiers<T>> {
    

    private Map<T, Double> modifiers;


    public Modifiers() {
        modifiers = new HashMap<>();
    }


    private Modifiers(Modifiers<T> copy) {
        this.modifiers = new HashMap<>(copy.modifiers);
    }
    public Modifiers<T> deepCopy() {
        return new Modifiers<>(this);
    }


    public Double get(T id) {
        if (!modifiers.containsKey(id)) modifiers.put(id, 1.0);
        return modifiers.get(id);
    }
    public void set(T id, double value) {
        modifiers.put(id, value);
    }
    public void mul(T id, double delta) {
        if (!modifiers.containsKey(id)) modifiers.put(id, 1.0);
        modifiers.put(id, modifiers.get(id) * delta);
    }
}
