package github.snomfish.functionality.number;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class INumberValueConstant implements INumber {
    

    private Value<INumber> value;
    

    public INumberValueConstant(Value<INumber> value) {
        this.value = value;
    }


    private INumberValueConstant(INumberValueConstant copy) {
        this.value = copy.value;
    }
    public INumberValueConstant deepCopy() {
        return new INumberValueConstant(this);
    }


    @Override 
    public List<Branch<Double>> execute(BattleContext context) {
        return value.get(context).execute(context);
    }
}
