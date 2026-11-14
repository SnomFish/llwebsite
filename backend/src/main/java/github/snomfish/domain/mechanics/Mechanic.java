package github.snomfish.domain.mechanics;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.functionality.event.Definition;

public class Mechanic extends Definition<IMechanicId, Mechanic> {
	

    private boolean hasMaxAge;


    public Mechanic() {
        this.hasMaxAge = false;
    }


    // getters
    public boolean hasMaxAge() {return hasMaxAge;}


    public Mechanic hasMaxAge(boolean hasMaxAge) {this.hasMaxAge = hasMaxAge; return this;}
}
