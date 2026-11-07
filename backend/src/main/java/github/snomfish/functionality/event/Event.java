package github.snomfish.functionality.event;

import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.event.ids.IEventId;

public record Event(
    IEventId id,
    ContextSideId sideId
) {}
