package github.snomfish.functionality.value.subject;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.domain.move.Move;

public enum MoveSubject implements ISubject<Move> {
    BASE_POWER(
        c -> c.basePower(),
        (c, v) -> c.setBasePower(v)
    )
    ;


    private final Function<Move, ?> getter;
    private final BiConsumer<Move, ?> setter;


    private <T> MoveSubject(
        Function<Move, T> getter,
        BiConsumer<Move, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(Move side) {
        return (T) getter.apply(side);
    } 

    @SuppressWarnings("unchecked")
    public <T> void set(Move context, T value) {
        ((BiConsumer<Move, T>) setter).accept(context, value);
    }
}
