package github.snomfish.domain.mechanics;

import github.snomfish.functionality.event.Definition;

public class Mechanic extends Definition<MechanicId, Mechanic> {
    

    private boolean hasMaxAge;
    private int maxAge;


    public Mechanic() {
        this.hasMaxAge = false;
        this.maxAge = 0;
    }


    // getters
    public boolean hasMaxAge() {return hasMaxAge;}
    public int maxAge() {return maxAge;}


    // builder functions
    public static Mechanic build() {
        return new Mechanic();
    }
    public Mechanic hasMaxAge(boolean hasMaxAge) {this.hasMaxAge = hasMaxAge; return this;}
    public Mechanic maxAge(int maxAge) {this.maxAge = maxAge; return this;}
}
