package github.snomfish.functionality.interceptor;

import github.snomfish.functionality.context.ContextSideId;

public record InterceptorEvent(
    InterceptorId id,
    ContextSideId sideId
) {}
