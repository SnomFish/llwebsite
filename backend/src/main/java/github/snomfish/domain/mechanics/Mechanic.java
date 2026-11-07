package github.snomfish.domain.mechanics;

import github.snomfish.functionality.event.Definition;

public class Mechanic extends Definition<MechanicId, Mechanic>{
    

    public Mechanic(MechanicId id) {
        super(id);
    }


    // builder functions
    public static Mechanic build(MechanicId id) {
        return new Mechanic(id);
    }
}
