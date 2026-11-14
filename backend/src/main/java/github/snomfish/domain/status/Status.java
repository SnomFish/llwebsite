package github.snomfish.domain.status;

import github.snomfish.functionality.event.Definition;

public class Status extends Definition<StatusId, Status> {
    
    
    public Status() {
        super();
    }


    public static Status build() {
        return new Status();
    }
}
