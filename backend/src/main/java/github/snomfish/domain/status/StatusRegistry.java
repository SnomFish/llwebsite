package github.snomfish.domain.status;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.modifiers.ids.LoomModifierId;
import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality._schematics.Effects;
import github.snomfish.functionality._schematics.Numbers;
import github.snomfish.functionality.condition.variable.Equals;
import github.snomfish.functionality.condition.variable.False;
import github.snomfish.functionality.effect.effects.RemoveHealth;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.GateForUserId;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForUserId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.status.StatusId.*;

public class StatusRegistry {
    

    private static final Map<StatusId, Status> registry = new HashMap<>();


    private StatusRegistry() {}


    public static Map<StatusId, Status> registry() {return registry;}
    public static Status get(StatusId id) {return registry.get(id);}


    private static void register(Status status) {
		if (registry.containsKey(status.id())) throw new RuntimeException("StatusRegistry attempted to register duplicate entry \"" + status.id().name() + "\"");
        registry.put(status.id(), status);
    }


    static {
        register(Status.build()
			.id(NO_STATUS)
            .name("no status")
        );  
        register(Status.build()
			.id(POISON)
            .name("posion")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.user(Numbers.eighthTargetMaxHealth()))
            )
        );
        register(Status.build()
			.id(BAD_POISON)
            .name("poison")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.user(new Multiply(
                    Numbers.sixteenthUserMaxHealth(),
                    new ValueConstant(Value.USER().STATUS_TURNS())
                )))
            )
        );
        register(Status.build()
			.id(PARALYSIS)
            .name("paralysis")
            .rule(Rule.build()
                .event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
                .effect(Effects.multiplyValue(Value.USER().LOOMIAN_MODIFIER(LoomModifierId.SPEED), new Constant(0.5)))
            )
            .gate(Gate.build()
                .event(GateForUserId.CAN_SELF_RECOVER_PASSIVE_ENERGY)
                .condition(new False())
            )
        );
        register(Status.build()
			.id(BURN)
            .name("burn")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.user(Numbers.sixteenthUserMaxHealth()))
            )
            .rule(Rule.build() // reduces mattack by 33%
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().CATEGORY(), CategoryId.MELEE))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.ATTACKING_STAT), new Constant (2/3)))
            )
        );
        register(Status.build()
			.id(FROSTBITE)
            .name("frostbite")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.user(Numbers.sixteenthUserMaxHealth()))
            )
            .rule(Rule.build() // reduces rattack by 33%
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().CATEGORY(), CategoryId.RANGED))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.ATTACKING_STAT), new Constant (2/3)))
            )
        );
        register(Status.build()
			.id(SLEEP)
            .name("sleep")
        );
    }
}
