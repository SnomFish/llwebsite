package github.snomfish.functionality.branch;

public class Branch<T> {
    

    private final T value;
    private final double probability;
    

    public Branch (
        T value, double probability
    ) {
        this.value = value;
        this.probability = probability;
    }


    // getter
    public T getValue() {
        return value;
    }
    public double getProbability() {
        return probability;
    }
}
