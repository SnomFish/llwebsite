package github.snomfish.functionality.event;

import github.snomfish.functionality.context.ContextSideId;

public record Event(
    IEventId id,
    ContextSideId sideId
) {}
