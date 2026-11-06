package github.snomfish.functionality.number;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Modulus {
    

    private INumber number;
    private int modulus;


    public Modulus(
        INumber number,
        int modulus
    ) {
        this.number = number;
        this.modulus = modulus;
    }


    private Modulus(Modulus copy) {
        this.number = copy.number.deepCopy();
        this.modulus = copy.modulus;
    }
    public Modulus deepCopy() {
        return new Modulus(this);
    }


    public List<Branch<Double>> execute(BattleContext context) {
        List<Branch<Double>> outcomes = new ArrayList<>();

        for (Branch<Double> numberBranch : number.execute(context)) {
            outcomes.add(new Branch<>(
                numberBranch.value() % modulus,
                numberBranch.probability()
            ));
        }

        return outcomes;
    }
}
