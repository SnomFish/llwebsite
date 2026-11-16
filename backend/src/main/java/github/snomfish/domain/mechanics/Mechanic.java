package github.snomfish.domain.mechanics;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.event.Definition;

public class Mechanic extends Definition<IMechanicId, Mechanic> {


    private IEffect onAppliedEffect;
    private IEffect onRemoveEffect;


    public Mechanic() {
        this.onAppliedEffect = new NoEffect();
        this.onRemoveEffect = new NoEffect();
    }


    public IEffect onAppliedEffect() {return onAppliedEffect;}
    public IEffect onRemoveEffect() {return onRemoveEffect;}


    public Mechanic onAppliedEffect(IEffect onAppliedEffect) {
        this.onAppliedEffect = onAppliedEffect;
        return this;
    }
    public Mechanic onRemoveEffect(IEffect onRemoveEffect) {
        this.onRemoveEffect = onRemoveEffect;
        return this;
    }
}
