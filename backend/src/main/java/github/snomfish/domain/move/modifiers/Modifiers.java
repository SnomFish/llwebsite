package github.snomfish.domain.move.modifiers;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

public class Modifiers implements DeepCopyable<Modifiers> {
    

    private Map<ModifierId, Double> modifiers;


    public Modifiers() {
        modifiers = new HashMap<>();
        for (ModifierId id : ModifierId.values()) {
            modifiers.put(id, 1.0);
        }
    }
    public Modifiers(Modifiers copy) {
        this.modifiers = new HashMap<>(copy.modifiers);
    }


    @Override 
    public Modifiers deepCopy() {
        return new Modifiers(this);
    }


    public Double get(ModifierId id) {
        return modifiers.get(id);
    }
    public void set(ModifierId id, double value) {
        modifiers.put(id, value);
    }
    public void mul(ModifierId id, double delta) {
        modifiers.put(id, modifiers.get(id) * delta);
    }
}
