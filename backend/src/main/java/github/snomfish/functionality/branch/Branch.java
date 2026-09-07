package github.snomfish.functionality.branch;

public class Branch<T> {
    

    private final T value;
    private double probability;
    

    public Branch (
        T value, double probability
    ) {
        this.value = value;
        this.probability = probability;
    }


    // getter
    public T value() {return value;}
    public double probability() {return probability;}

    
    public void multiplyProbability(double delta) {
        this.probability *= delta;
    }
}
