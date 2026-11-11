package github.snomfish.functionality.effect;

import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.event.ids.GateId;
import github.snomfish.functionality.number.Add;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

public class Effects {
    

    private Effects() {}


    public static IEffect flinch() {
        return new ConditionalEffect(
            new GateCondition(GateId.CAN_MOVE_FLINCH, ContextSideId.USER),
            ApplyMechanic.toTarget(MechanicId.FLINCH)
        );
    }
    public static IEffect flinchChance(INumber flinchChanceNumber) {
        return new ConditionalEffect(
            new RandomCondition(flinchChanceNumber),
            flinch()
        );
    }


    public static IEffect daze() {
        return new ConditionalEffect(
            new GateCondition(GateId.CAN_MOVE_DAZE, ContextSideId.USER),
            ApplyMechanic.toTarget(MechanicId.DAZE)
        );
    }
    public static IEffect dazeChance(INumber dazeChanceNumber) {
        return new ConditionalEffect(
            new RandomCondition(dazeChanceNumber),
            daze()
        );
    }


    public static IEffect addValue(Value<? extends Number> value, INumber addition) {
        return new SetValueEffect<>(value, new Add(new ValueConstant(value), addition));
    }
    public static IEffect addValue(Value<? extends Number> value, Value<? extends Number> addition) {
        return new SetValueEffect<>(value, new Add(new ValueConstant(value), new ValueConstant(addition)));
    }

    public static IEffect multiplyValue(Value<? extends Number> value, INumber multiplier) {
        return new SetValueEffect<>(value, new Multiply(new ValueConstant(value), multiplier));
    }
    public static IEffect multiplyValue(Value<? extends Number> value, Value<? extends Number> multiplier) {
        return new SetValueEffect<>(value, new Multiply(new ValueConstant(value), new ValueConstant(multiplier)));
    }
}
