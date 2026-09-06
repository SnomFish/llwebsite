package github.snomfish.domain.status;

import github.snomfish.functionality.copy.DeepCopyable;

public class Status implements DeepCopyable<Status> {
    

    private StatusId id;


    public Status(StatusId id) {
        this.id = id;
    }


    @Override 
    public Status deepCopy() {
        return new Status(id);
    }


    // getters
    public StatusId id() {return id;}
}
