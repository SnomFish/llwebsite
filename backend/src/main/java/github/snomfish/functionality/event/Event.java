package github.snomfish.functionality.event;

import github.snomfish.functionality.context.ContextSideId;

public record Event<T extends Enum<T>>(
    T id,
    ContextSideId sideId
) {}
