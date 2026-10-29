package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class SetType implements IEffect {
    

    private Value typeToSet;
    private TypeId type;


    private SetType(
        Value typeToSet,
        TypeId type
    ) {
        this.typeToSet = typeToSet;
        this.type = type;
    }


    private SetType(SetType copy) {
        this.typeToSet = copy.typeToSet;
        this.type = copy.type;
    }
    public SetType deepCopy() {
        return new SetType(this);
    }


    public static IEffect userPrimary(TypeId type) {
        return new SetType(Value.USER_PRIMARY_TYPE, type);
    }
    public static IEffect targetPrimary(TypeId type) {
        return new SetType(Value.TARGET_PRIMARY_TYPE, type);
    }
    public static IEffect userSecondary(TypeId type) {
        return new SetType(Value.USER_SECONDARY_TYPE, type);
    }
    public static IEffect targetSecondary(TypeId type) {
        return new SetType(Value.TARGET_SECONDARY_TYPE, type);
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        BattleContext outcome = context.deepCopy();
        

        return List.of(new Branch<>(outcome, 1));
    }
}
