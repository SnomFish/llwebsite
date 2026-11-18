package github.snomfish.domain.mechanics;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.tags.MechanicTagId;
import github.snomfish.domain.tags.Tags;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.NoEffect;
import github.snomfish.functionality.event.Definition;

public class Mechanic extends Definition<IMechanicId, Mechanic> {


    private IEffect onAppliedEffect;
    private IEffect onRemoveEffect;
    private Tags<MechanicTagId> tags;


    public Mechanic() {
        this.onAppliedEffect = new NoEffect();
        this.onRemoveEffect = new NoEffect();
        this.tags = new Tags<>();
    }


    public IEffect onAppliedEffect() {return onAppliedEffect;}
    public IEffect onRemoveEffect() {return onRemoveEffect;}
    public Tags<MechanicTagId> tags() {return tags;}


    public Mechanic onAppliedEffect(IEffect onAppliedEffect) {
        this.onAppliedEffect = onAppliedEffect;
        return this;
    }
    public Mechanic onRemoveEffect(IEffect onRemoveEffect) {
        this.onRemoveEffect = onRemoveEffect;
        return this;
    }
    public Mechanic tag(MechanicTagId tag) {
        this.tags.add(tag);
        return this;
    }
}
