package github.snomfish.functionality.value;

import java.util.function.Function;

import github.snomfish.functionality.context.BattleContext;

public enum SubjectRoot {
    USER(
        c -> c.user()
    ),
    TARGET(
        c -> c.target()
    ),
    USER_MOVE(
        c -> c.user().activeLoomian().action().move()
    ),
    TARGET_MOVE(
        c -> c.target().activeLoomian().action().move()
    )
    ;


    private final Function<BattleContext, ?> getter;


    private <T> SubjectRoot(
        Function<BattleContext, T> getter
    ) {
        this.getter = getter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(BattleContext context) {
        return (T) getter.apply(context);
    }
}
