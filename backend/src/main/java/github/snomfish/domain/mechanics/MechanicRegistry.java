package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality._schematics.Effects;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.AddType;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.RemoveMechanic;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForTargetId;
import github.snomfish.functionality.event.ruleIds.RuleForUserId;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.mechanics.ids.FieldMechanicId.*;
import static github.snomfish.domain.mechanics.ids.LoomianMechanicId.*;
import static github.snomfish.domain.mechanics.ids.SideMechanicId.*;

public class MechanicRegistry {


    private static final Map<IMechanicId, Mechanic> registry = new HashMap<>();


    private MechanicRegistry() {}


    public static Map<IMechanicId, Mechanic> registry() {return registry;}
    public static Mechanic get(IMechanicId id) {return registry.get(id);}


    private static void register(Mechanic mechanic) {
		if (registry.containsKey(mechanic.id())) throw new RuntimeException("LoomainMechanicRegistry attempted to register duplicate entry \"" + mechanic.name() + "\"");
        registry.put(mechanic.id(), mechanic);
    }


    static {
        loadLoomian();
        loadSide();
        loadField();
    }


    public static void loadLoomian() {
        register(new Mechanic()
            .id(DAZE)
            .name("Daze")
        );
        register(new Mechanic()
            .id(FLINCH)
            .name("Flinch")
        );

        register(new Mechanic()
            .id(WILL_SET_BOH)
            .name("will set up bane of haste at end of turn")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(new EffectSequence(
                    ApplyMechanic.toField(BANE_OF_HASTE).withMaxAge(4),
                    RemoveMechanic.fromUser(WILL_SET_BOH)
                ))
            )
        );
        register(new Mechanic()
            .id(PERCHING)
            .name("perching")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(new EffectSequence(
                    AddType.toUser(TypeId.AIR),
                    RemoveMechanic.fromUser(PERCHING)
                ))
            )
        );
        register(new Mechanic()
            .id(REVIVING)
            .name("reviving")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(new EffectSequence(
                    AddType.toUser(TypeId.SPIRIT),
                    RemoveMechanic.fromUser(REVIVING)
                ))
            )
        );

        // mechanics caused by abilities
        register(new Mechanic()
            .id(COMBUSTIBLE)
            .name("combustible")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.FIRE))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(COURSING_VENOM)
            .name("coursing venom")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.TOXIC))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(NOXIOUS_WEEDS)
            .name("Noxious Weeds")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.PLANT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(PRISMATIC)
            .name("Prismatic")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.LIGHT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(TOXIC_FILTER)
            .name("Toxic Filter")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.AIR))
                .effect(new EffectSequence(
                    Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
                    RemoveMechanic.fromUser(TOXIC_FILTER)
                ))
            )
        );

        // mechanics caused by moves
        register(new Mechanic()
            .id(ALL_OR_NOTHING_FOCUSING)
            .name("Focusing for All or Nothing")
            .rule(Rule.build()
                .event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
                .effect(RemoveMechanic.fromUser(ALL_OR_NOTHING_FOCUSING))
            )
        );
    }



    public static void loadSide() {
        register(new Mechanic()
            .id(ICICLE_TRAP)
            .name("icicle trap")
        );
        register(new Mechanic()
            .id(POISON_BARBS)
            .name("poison barbs")
        );
        register(new Mechanic()
            .id(BARBS)
            .name("barbs")
        );
        register(new Mechanic()
            .id(HASTEN)
            .name("hasten")
        );
        register(new Mechanic()
            .id(NEAR_ENCHANTMENT)
            .name("near enchantment")
        );
        register(new Mechanic()
            .id(FAR_ENCHANTMENT)
            .name("far enchantment")
        );
        register(new Mechanic()
            .id(AUGMENT)
            .name("augment")
        );
    }



    public static void loadField() {
        register(new Mechanic()
            .id(BANE_OF_HASTE)
            .name("bane of haste")
        );
        register(new Mechanic()
            .id(FUNGUS_CURSE)
            .name("fungus curse")
        );
    }
}
