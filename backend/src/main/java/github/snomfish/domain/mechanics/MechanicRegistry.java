package github.snomfish.domain.mechanics;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.mechanics.ids.IMechanicId;
import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality._schematics.Effects;
import github.snomfish.functionality._schematics.Numbers;
import github.snomfish.functionality.condition.predicates.PredicateFalse;
import github.snomfish.functionality.condition.variable.And;
import github.snomfish.functionality.condition.variable.Equals;
import github.snomfish.functionality.condition.variable.False;
import github.snomfish.functionality.condition.variable.GateCondition;
import github.snomfish.functionality.condition.variable.ListContains;
import github.snomfish.functionality.condition.variable.Not;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.AddType;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.ApplyStatus;
import github.snomfish.functionality.effect.effects.MultiplyINumberValue;
import github.snomfish.functionality.effect.effects.RemoveHealth;
import github.snomfish.functionality.effect.effects.RemoveMechanic;
import github.snomfish.functionality.effect.effects.RestoreHealth;
import github.snomfish.functionality.effect.effects.SetValue;
import github.snomfish.functionality.event.Door;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.gateIds.GateForTargetId;
import github.snomfish.functionality.event.gateIds.GateForUserId;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForTargetId;
import github.snomfish.functionality.event.ruleIds.RuleForUserId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.TypeMultiplier;

import static github.snomfish.domain.mechanics.ids.FieldMechanicId.*;
import static github.snomfish.domain.mechanics.ids.LoomianMechanicId.*;
import static github.snomfish.domain.mechanics.ids.SideMechanicId.*;
import static github.snomfish.domain.tags.MechanicTagId.*;

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
            .id(NO_MECHANIC)
            .name("no mechanic")
        );
        register(new Mechanic()
            .id(RESTING)
            .name("resting")
            .rule(Rule.build() 
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
                .effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DEFENDING_STAT), new Constant(2/3)))
            )
        );


        register(new Mechanic()
            .id(DAZE)
            .name("Daze")
        );
        register(new Mechanic()
            .id(FLINCH)
            .name("Flinch")
        );
        register(new Mechanic()
            .id(SEMI_INVUNRABLE)
            .name("semi invunrable")
            .gate(Gate.build()
                .event(GateForTargetId.CAN_OPP_MOVE_HIT_SELF)
                .condition(new False())
            )
        );


        // MOVE SPECIFIC
        register(new Mechanic()
            .id(ALL_OR_NOTHING_FOCUSING)
            .name("Focusing for All or Nothing")
            .rule(Rule.build()
                .event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
                .effect(RemoveMechanic.fromUser(ALL_OR_NOTHING_FOCUSING))
            )
        );
        register(new Mechanic()
            .id(BEASTS_SLUMBER)
            .name("beasts slumber")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RestoreHealth.user(Numbers.thirdUserMaxHealth()))
            )
            .onAppliedEffect(ApplyStatChanges.userChangingUser()
                .mdefense(new Constant(1))
                .rdefense(new Constant(1))
            )
            .onRemoveEffect(ApplyStatChanges.userChangingUser()
                .mdefense(new Constant(-1))
                .rdefense(new Constant(-1))
            )
        );
        register(new Mechanic()
            .id(FOUNTAIN_BEAM_CHARGING)
            .name("fountain beam charging")
            .rule(Rule.build()
                .event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
                .effect(new MultiplyINumberValue(Value.USER().MOVE().BASE_POWER(), 0.5))
                .effect(RemoveMechanic.fromUser(FOUNTAIN_BEAM_CHARGING))
            )
        );
        register(new Mechanic()
            .id(PERCHING)
            .name("perching")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(AddType.toUser(TypeId.AIR))
                .effect(RemoveMechanic.fromUser(PERCHING))
            )
        );
        register(new Mechanic()
            .id(PROVOKE)
            .name("provoke")
            .gate(Gate.build()
                .event(GateForUserId.CAN_SELF_USE_SUPPORT_MOVES)
                .condition(new False())
            )
            .gate(Gate.build()
                .event(GateForUserId.CAN_SELF_SELECT_MOVE)
                .condition(new Not(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.STATUS)))
            )
        );
        register(new Mechanic()
            .id(REVIVING)
            .name("reviving")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(AddType.toUser(TypeId.SPIRIT))
                .effect(RemoveMechanic.fromUser(REVIVING))
            )
        );
        register(new Mechanic()
            .id(SAP_PLANT)
            .name("sap plant")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.user(Numbers.eighthUserMaxHealth()))
                .effect(RestoreHealth.target(Numbers.eighthUserMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(SLUMBER)
            .name("slumber")
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RestoreHealth.user(Numbers.thirdUserMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_OATH)
            .name("trapped by binding oath")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_PESTILENCE)
            .name("trapped by pestilence")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.target(Numbers.eighthTargetMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_PINCER_TRAP)
            .name("trapped by pincer trap")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_QUICKSAND)
            .name("trapped by quicksand")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.target(Numbers.eighthTargetMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_ROOT)
            .name("trapped by take root")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RestoreHealth.user(Numbers.eighthUserMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_STEEL_TRAP)
            .name("trapped by steel trap")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
            .rule(Rule.build()
                .event(ConstantRuleId.END_OF_TURN)
                .effect(RemoveHealth.target(Numbers.eighthTargetMaxHealth()))
            )
        );
        register(new Mechanic()
            .id(TRAPPED_BY_SWEET_AROMA)
            .name("trapped by sweet aroma")
            .door(Door.build()
                .event(GateForUserId.CAN_SELF_SWITCH)
                .predicate(new PredicateFalse())
            )
        );
        register(new Mechanic()
            .id(TEASE)
            .name("tease")
            // TODO implement tease mechanic
        );

        // mechanics caused by abilities
        register(new Mechanic()
            .id(COMBUSTIBLE)
            .name("combustible")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.FIRE))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(COURSING_VENOM)
            .name("coursing venom")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.TOXIC))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(NOXIOUS_WEEDS)
            .name("Noxious Weeds")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.PLANT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(PRISMATIC)
            .name("Prismatic")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.LIGHT))
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
            )
        );
        register(new Mechanic()
            .id(TOXIC_FILTER)
            .name("Toxic Filter")
            .rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.AIR))
                .effect(new EffectSequence(
                    Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)),
                    RemoveMechanic.fromUser(TOXIC_FILTER)
                ))
            )
        );
    }



    public static void loadSide() {
        register(new Mechanic()
            .id(ICICLE_TRAP)
            .name("icicle trap")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.ICE)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(RemoveHealth.user(
                    new Multiply(Numbers.eighthUserMaxHealth(), TypeMultiplier.onUser(TypeId.ICE))
                ))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(HALF_ICICLE_TRAP)
            .name("half icicle trap")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.ICE)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(RemoveHealth.user(
                    new Multiply(Numbers.sixteenthUserMaxHealth(), TypeMultiplier.onUser(TypeId.ICE))
                ))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(SINGLE_POISON_BARBS)
            .name("single poison barbs")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.TOXIC)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(ApplyStatus.toUser(StatusId.POISON))
            )
            .rule(Rule.build() // removes the poison barbs if a toxic type swaps in
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new ListContains<>(Value.USER().TYPES(), TypeId.TOXIC))
                .effect(RemoveMechanic.fromUserSide(SINGLE_POISON_BARBS))
            )
            .tag(HAZARD)
        ); // TODO removes the poison barbs if a toxic type swaps in
        register(new Mechanic()
            .id(DOUBLE_POISON_BARBS)
            .name("double poison barbs")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.TOXIC)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(ApplyStatus.toUser(StatusId.BAD_POISON))
            )
            .rule(Rule.build() // removes the poison barbs if a toxic type swaps in
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new ListContains<>(Value.USER().TYPES(), TypeId.TOXIC))
                .effect(RemoveMechanic.fromUserSide(DOUBLE_POISON_BARBS))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(SINGLE_BARBS)
            .name("single barbs")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(RemoveHealth.user(Numbers.eighthUserMaxHealth()))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(DOUBLE_BARBS)
            .name("double barbs")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(RemoveHealth.user(Numbers.sixthUserMaxHealth()))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(TRIPLE_BARBS)
            .name("triple barbs")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(RemoveHealth.user(Numbers.quarterUserMaxHealth()))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(BANANA_PEEL)
            .name("banana peel")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_SWAPS_IN)
                .condition(new And(
                    new Not(new ListContains<>(Value.USER().TYPES(), TypeId.AIR)),
                    new GateCondition(GateForAllId.CAN_HAZARDS_AFFECT, ContextSideId.USER)
                ))
                .effect(ApplyStatChanges.targetChangingUser().speed(new Constant(-1)))
            )
            .tag(HAZARD)
        );
        register(new Mechanic()
            .id(MOTIVATED)
            .name("motivated")
            .rule(Rule.build()
                .event(RuleForUserId.SELF_PRE_ENERGY_CALC)
                .effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0.9)))
            )
        );
        register(new Mechanic()
            .id(AUGMENT)
            .name("augment")
        );
        register(new Mechanic()
            .id(SWIFTEN)
            .name("swiften")
        );
        register(new Mechanic()
            .id(NEAR_ENCHANTMENT)
            .name("near enchantment")
            .rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.TARGET().MOVE().CATEGORY(), CategoryId.MELEE))
                .effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
            )
            .tag(ENCHANTMENT)
        );
        register(new Mechanic()
            .id(FAR_ENCHANTMENT)
            .name("far enchantment")
            .rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
                .condition(new Equals<>(Value.TARGET().MOVE().CATEGORY(), CategoryId.RANGED))
                .effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
            )
            .tag(ENCHANTMENT)
        );
    }



    public static void loadField() {
        register(new Mechanic()
            .id(BANE_OF_HASTE)
            .name("bane of haste")
            .tag(BANE)
        );
        register(new Mechanic()
            .id(BANE_OF_WIELDING)
            .name("bane of wielding")
            .gate(Gate.build()
                .event(GateForAllId.CAN_USE_ITEMS)
                .condition(new False())
            )
            .tag(BANE)
        );
        register(new Mechanic()
            .id(FUNGUS_CURSE)
            .name("fungus curse")
            .rule(Rule.build()
                .event(RuleForAllId.PRE_DEAL_FORMULA_DAMAGE)
                .effect(new AlterTypeChart(TypeId.LIGHT, TypeId.PLANT, 2))
                .effect(new AlterTypeChart(TypeId.DARK, TypeId.PLANT, 0))
            )
        );
    }
}
