

package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetType implements IEffect {
    

    private Value<TypeId> typeToSet;
    private Object typeObject;


    private SetType(
        Value<TypeId> typeToSet,
        Object typeObject
    ) {
        this.typeToSet = typeToSet;
        this.typeObject = typeObject;
    }


    private SetType(SetType copy) {
        this.typeToSet = copy.typeToSet;
        this.typeObject = copy.typeObject;
    }
    public SetType deepCopy() {
        return new SetType(this);
    }


    // TypeId
    public static IEffect userPrimary(TypeId typeId) {
        return new SetType(Value.USER().PRIMARY_TYPE(), typeId);
    }
    public static IEffect targetPrimary(TypeId typeId) {
        return new SetType(Value.TARGET().PRIMARY_TYPE(), typeId);
    }
    public static IEffect userSecondary(TypeId typeId) {
        return new SetType(Value.USER().SECONDARY_TYPE(), typeId);
    }
    public static IEffect targetSecondary(TypeId typeId) {
        return new SetType(Value.TARGET().SECONDARY_TYPE(), typeId);
    }
    public static IEffect move(TypeId typeId) {
        return new SetType(Value.USER().MOVE().TYPE(), typeId);
    }
    // Value
    public static IEffect userPrimary(Value<TypeId> typeValue) {
        return new SetType(Value.USER().PRIMARY_TYPE(), typeValue);
    }
    public static IEffect targetPrimary(Value<TypeId> typeValue) {
        return new SetType(Value.TARGET().PRIMARY_TYPE(), typeValue);
    }
    public static IEffect userSecondary(Value<TypeId> typeValue) {
        return new SetType(Value.USER().SECONDARY_TYPE(), typeValue);
    }
    public static IEffect targetSecondary(Value<TypeId> typeValue) {
        return new SetType(Value.TARGET().SECONDARY_TYPE(), typeValue);
    }
    public static IEffect move(Value<TypeId> typeValue) {
        return new SetType(Value.USER().MOVE().TYPE(), typeValue);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        
        TypeId type = Value.resolve(typeObject, outcome);
        typeToSet.set(outcome, type);

        return List.of(new Branch<>(outcome, 1));
    }
}
