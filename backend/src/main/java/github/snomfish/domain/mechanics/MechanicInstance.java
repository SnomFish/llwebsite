package github.snomfish.domain.mechanics;

import github.snomfish.functionality.copy.DeepCopyable;

public class MechanicInstance implements DeepCopyable<MechanicInstance> {
    

    private final MechanicId id;
    private int age;


    public MechanicInstance(
        MechanicId id,
        int age
    ) {
        this.id = id;
        this.age = age;
    }


    private MechanicInstance(MechanicInstance copy) {
        this.id = copy.id;
        this.age = copy.age;
    }
    public MechanicInstance deepCopy() {
        return new MechanicInstance(this);
    }


    // getters
    public MechanicId id() {return id;}
    public Mechanic mechanic() {return MechanicRegistry.get(id);}
    public int age() {return age;}


    // setters
    public void age(int age) {this.age = age;}
}
