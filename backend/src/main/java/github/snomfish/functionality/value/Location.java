package github.snomfish.functionality.value;

import github.snomfish.functionality.value.subject.ISubject;

public record Location<T>(
    SubjectRoot main,
    ISubject<T> side
) {}
