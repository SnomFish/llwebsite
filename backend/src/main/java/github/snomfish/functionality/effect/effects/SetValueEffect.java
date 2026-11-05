package github.snomfish.functionality.effect.effects;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.NumberUtil;

public class SetValueEffect<T extends Number> implements IEffect {

    private Value<T> value;
    private INumber newValue;

    public SetValueEffect(
        Value<T> value,
        INumber newValue
    ) {
        this.value = value;
        this.newValue = newValue;
    }


    @Override
    public SetValueEffect<T> deepCopy() {
        return new SetValueEffect<>(
            value,
            newValue.deepCopy()
        );
    }


    // got chatgpt to do this one, altered from my multiplyValueEffect, so if it doesnt work it is not my fault
    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {

        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        List<Branch<Double>> valueBranches = newValue.execute(context);

        for (Branch<Double> valueBranch : valueBranches) {
            BattleContext copy = context.deepCopy();

            
            value.set(copy, NumberUtil.convert(valueBranch.value(), value.type()));

            outcomes.add(new Branch<>(
                copy,
                valueBranch.probability()
            ));
        }

        return outcomes;
    }
}