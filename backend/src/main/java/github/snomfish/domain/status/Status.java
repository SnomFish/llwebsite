package github.snomfish.domain.status;

import github.snomfish.functionality.event.Definition;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;

public class Status extends Definition<StatusId, Status> {
    
    public Status(StatusId id) {
        super(id);
    }


    // builder functions
    public static Status build(StatusId id) {
        return new Status(id);
    }
}
