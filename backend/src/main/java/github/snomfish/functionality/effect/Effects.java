package github.snomfish.functionality.effect;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.number.Add;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

public class Effects {
    

    private Effects() {}


    public static IEffect addValue(Value value, INumber addition) {
        return new SetValueEffect(value, new Add(new ValueConstant(value), addition));
    }
    public static IEffect addValue(Value value, Value addition) {
        return new SetValueEffect(value, new Add(new ValueConstant(value), new ValueConstant(addition)));
    }

    public static IEffect multiplyValue(Value value, INumber multiplier) {
        return new SetValueEffect(value, new Multiply(new ValueConstant(value), multiplier));
    }
    public static IEffect multiplyValue(Value value, Value multiplier) {
        return new SetValueEffect(value, new Multiply(new ValueConstant(value), new ValueConstant(multiplier)));
    }
}
