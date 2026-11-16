package github.snomfish.functionality._schematics;

import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.SetValueIEffect;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.number.Add;
import github.snomfish.functionality.number.INumber;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

public class Effects {
    

    private Effects() {}


    public static IEffect flinch() {
        return new ConditionalEffect(
            new GateCondition(GateForAllId.CAN_MOVE_FLINCH, ContextSideId.USER),
            ApplyMechanic.toTarget(LoomianMechanicId.FLINCH)
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
            new GateCondition(GateForAllId.CAN_MOVE_DAZE, ContextSideId.USER),
            ApplyMechanic.toTarget(LoomianMechanicId.DAZE)
        );
    }
    public static IEffect dazeChance(INumber dazeChanceNumber) {
        return new ConditionalEffect(
            new RandomCondition(dazeChanceNumber),
            daze()
        );
    }


    public static IEffect addValue(Value<? extends Number> value, INumber addition) {
        return new SetValueIEffect<>(value, new Add(new ValueConstant(value), addition));
    }
    public static IEffect addValue(Value<? extends Number> value, Value<? extends Number> addition) {
        return new SetValueIEffect<>(value, new Add(new ValueConstant(value), new ValueConstant(addition)));
    }

    public static IEffect multiplyValue(Value<? extends Number> value, INumber multiplier) {
        return new SetValueIEffect<>(value, new Multiply(new ValueConstant(value), multiplier));
    }
    public static IEffect multiplyValue(Value<? extends Number> value, Value<? extends Number> multiplier) {
        return new SetValueIEffect<>(value, new Multiply(new ValueConstant(value), new ValueConstant(multiplier)));
    }
}
