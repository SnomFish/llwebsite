package github.snomfish.functionality.branch;

public class Branch<T> {
    

    private T value;
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

    
    // setter
    public void setValue(T value) {this.value = value;}
    public void setProbability(double probability) {this.probability = probability;}
    
    public void multiplyProbability(double delta) {
        this.probability *= delta;
    }
}
