package github.snomfish.functionality.effect;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.number.Add;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

public class Effects {
    

    private Effects() {}


    public static IEffect addValue(Value<? extends Number> value, INumber addition) {
        return new SetValueEffect(value, new Add(new ValueConstant(value), addition));
    }
    public static IEffect addValue(Value<? extends Number> value, Value<? extends Number> addition) {
        return new SetValueEffect(value, new Add(new ValueConstant(value), new ValueConstant(addition)));
    }

    public static IEffect multiplyValue(Value<? extends Number> value, INumber multiplier) {
        return new SetValueEffect(value, new Multiply(new ValueConstant(value), multiplier));
    }
    public static IEffect multiplyValue(Value<? extends Number> value, Value<? extends Number> multiplier) {
        return new SetValueEffect(value, new Multiply(new ValueConstant(value), new ValueConstant(multiplier)));
    }
}
