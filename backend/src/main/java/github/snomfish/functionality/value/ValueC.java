package github.snomfish.functionality.value;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.functionality.context.BattleContext;

public enum ValueC {
    USER(
        c -> c.user(),
        null
    ),
    TARGET(
        c -> c.target(),
        null
    ),
    MOVE(
        c -> c.user().activeLoomian().action().move(),
        null
    )
    ;


    private final Function<BattleContext, ?> getter;
    private final BiConsumer<BattleContext, ?> setter;


    private <T> ValueC(
        Function<BattleContext, T> getter,
        BiConsumer<BattleContext, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(BattleContext context) {
        return (T) getter.apply(context);
    } 

    @SuppressWarnings("unchecked")
    public <T> void set(BattleContext context, T value) {
        ((BiConsumer<BattleContext, T>) setter).accept(context, value);
    }
}
