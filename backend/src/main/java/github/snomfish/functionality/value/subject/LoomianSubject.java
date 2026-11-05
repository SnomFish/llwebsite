package github.snomfish.functionality.value.subject;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.domain.Side;

public enum LoomianSubject implements ISubject<Side> {
    CURRENT_HEALTH(
        c -> c.activeLoomian().currentHealth(),
        (c, v) -> c.activeLoomian().setCurrentHealth(v)
    )
    ;


    private final Function<Side, ?> getter;
    private final BiConsumer<Side, ?> setter;


    private <T> LoomianSubject(
        Function<Side, T> getter,
        BiConsumer<Side, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(Side side) {
        return (T) getter.apply(side);
    } 

    @SuppressWarnings("unchecked")
    public <T> void set(Side context, T value) {
        ((BiConsumer<Side, T>) setter).accept(context, value);
    }
}
