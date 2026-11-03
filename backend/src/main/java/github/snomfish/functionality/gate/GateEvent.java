package github.snomfish.functionality.gate;

import github.snomfish.functionality.context.ContextSideId;

public record GateEvent(
    GateId id,
    ContextSideId sideId
) {}
