package github.snomfish.functionality.condition;

import java.util.function.BiPredicate;

public enum ComparatorId {
    GREATER_THAN_EQUAL_TO(
        (a, b) -> a >= b 
    ),
    LESS_THAN_EQUAL_TO(
        (a, b) -> a <= b
    ),
    GREATER_THAN(
        (a, b) -> a > b
    ),
    LESS_THAN(
        (a, b) -> a > b
    );


    private BiPredicate<Double, Double> comparator;


    private ComparatorId(BiPredicate<Double, Double> comparator) {
        this.comparator = comparator;
    }


    public Boolean apply(Double a, Double b) {
        return comparator.test(a, b);
    }
}
