package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.domain.modifiers.MoveModifierId;
import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.tags.AbilityTagId;
import github.snomfish.domain.tags.ItemTagId;
import github.snomfish.domain.tags.MoveTagId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality._schematics.Conditions;
import github.snomfish.functionality._schematics.Effects;
import github.snomfish.functionality._schematics.Numbers;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.False;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.condition.ListContains;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.ApplyStatus;
import github.snomfish.functionality.effect.effects.ClearHazards;
import github.snomfish.functionality.effect.effects.ClearStatChanges;
import github.snomfish.functionality.effect.effects.ClearStatus;
import github.snomfish.functionality.effect.effects.ClutchEffect;
import github.snomfish.functionality.effect.effects.DispatchEventEffect;
import github.snomfish.functionality.effect.effects.RemoveHealth;
import github.snomfish.functionality.effect.effects.RemoveMechanic;
import github.snomfish.functionality.effect.effects.RestoreEnergy;
import github.snomfish.functionality.effect.effects.RestoreHealth;
import github.snomfish.functionality.effect.effects.SetAbility;
import github.snomfish.functionality.effect.effects.SetFieldMechanic;
import github.snomfish.functionality.effect.effects.SetHazards;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetValueIEffect;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Hook;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.gateIds.GateForTargetId;
import github.snomfish.functionality.event.gateIds.GateForUserId;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForAllId;
import github.snomfish.functionality.event.ruleIds.RuleForTargetId;
import github.snomfish.functionality.event.ruleIds.RuleForUserId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumberValueConstant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.domain.tags.AbilityTagId.*;
import static github.snomfish.functionality.context.ContextSideId.*;

public class AbilityRegistry {
    

    private static final Map<AbilityId, Ability> registry = new HashMap<>();


    private AbilityRegistry() {}


	public static Map<AbilityId, Ability> registry() {return registry;}
    public static Ability get(AbilityId id) {return registry.get(id);}


    private static void register(Ability ability) {
		if (registry.containsKey(ability.id())) throw new RuntimeException("AbilityRegistry attempted to register duplicate entry \"" + ability.id().name() + "\"");
		registry.put(ability.id(), ability);
	}


    static {
		register(Ability.build() // completed
			.id(NO_ABILITY)
			.name("no ability")
		);
        register(Ability.build() // completed
			.id(ABILITY_THIEF)
			.name("ability thief")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, USER))
				.condition(new GateCondition(GateForAllId.CAN_REMOVE_ABILITY, TARGET))
				.effect(new EffectSequence(List.of(
					SetAbility.userActual(Value.TARGET().ABILITY_ID()),
					SetAbility.user(Value.TARGET().ABILITY_ID()),
					SetAbility.targetActual(AbilityId.NO_ABILITY),
					SetAbility.target(AbilityId.NO_ABILITY),
					new DispatchEventEffect(RuleForAllId.LOOMIAN_SWAPS_IN, USER)
				)))
			)
			.tag(CANNOT_BE_REPLICATED)
			.setOncePerBattle()
		);
		register(Ability.build() // completed
			.id(ACE)
			.name("ace")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE) // right before a moves damage is calculated
				.condition(new ListContains<>(Value.USER().TYPES(), Value.USER().MOVE().TYPE()))
				.effect(new SetValueIEffect<>(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.STAB), new Constant(2.0)))
            )
		);
		register(Ability.build() // completed
			.id(ADAPTABLE)
			.name("adaptable")
			.rule(Rule.build()
				.event(RuleForAllId.LOOMIAN_SET_WEATHER)
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.userPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.userPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.userPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.userPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.userPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.userPrimary(TypeId.ELECTRIC))
				)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
		);
		register(Ability.build() // completed
			.id(ADORABLE)
			.name("adorable")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
				)
			)
		);
		register(Ability.build()
			.id(AGGRESSIVE)
			.name("aggressive")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))	
				)
			)
		);
		register(Ability.build()
			.id(AIR_GUARD)
			.name("air guard")
		);
		register(Ability.build()
			.id(ALL_SEEING)
			.name("all seeing")
		);
		register(Ability.build()
			.id(AMBUSH)
			.name("ambush")
		);
		register(Ability.build() // completed
			.id(ANALYZE)
			.name("analyze")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(new ConditionalEffect(
					new Or(
						Comparator.greaterThan()
							.left(new ValueConstant(Value.TARGET().MDEFENSE()))
							.right(new ValueConstant(Value.TARGET().RDEFENSE())),
						new And(
							new Equals<>(Value.TARGET().MDEFENSE(), Value.TARGET().RDEFENSE()),
							new RandomCondition(new Constant(0.5))
						)
					), 
					ApplyStatChanges.userChangingUser() // true effect
						.rattack(new Constant(1)), 
					ApplyStatChanges.userChangingUser() // false effect
						.mattack(new Constant(1))
				))
			)
		);
		register(Ability.build()
			.id(ANOMALY)
			.name("anomaly")
			.rule(Rule.build() // when the loomian is the user
				.event(RuleForUserId.SELF_PRE_STATS_CHANGED) 
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MATTACK_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().MDEFENSE_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().RATTACK_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().RDEFENSE_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().SPEED_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().ACCURACY_STAGE_MODIFIER(), new Constant(-1)),
					Effects.multiplyValue(Value.USER().EVASION_STAGE_MODIFIER(), new Constant(-1))
				)))
			)
		);
		register(Ability.build()
			.id(APPETITE)
			.name("appetite")
		);
		register(Ability.build() // completed
			.id(APPLIED_FRUSTRATION)
			.name("applied frustration")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_STATS_LOWERED_BY_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(2))
				)
			)
		);
		register(Ability.build() // completed
			.id(APPREHENSION)
			.name("apprehension")
		);
		 // this has to use type modifier, you CAN alter the typechart for this as if the target has two types, that fire resistance will be applied twice
		register(Ability.build() // completed
            .id(AQUA_BODY)
            .name("aqua body")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE) // before the targets move damage is calculated
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
                .effect(new ConditionalEffect(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE),
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.TYPE), new Constant(0.5))
				))
            )
        );
        register(Ability.build()
            .id(ARCTIC)
            .name("arctic")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.snowstormActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
        );
        register(Ability.build() // completed
            .id(ASSERTIVE)
            .name("assertive")
			.rule(Rule.build()
                .event(RuleForUserId.SELF_PRE_MOVE) // before anything about the move is actually used
                .effect(new AlterTypeChart(TypeId.BRAWLER, TypeId.SPIRIT, 1))
            )
        );
        register(Ability.build() // completed
            .id(AWAKENING)
            .name("awakening")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE) // right before a moves damage is calculated
                .effect(new SetValueIEffect<>(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.STAB), new Constant(1.5)))
            )
        );
        register(Ability.build() // completed
            .id(BANEFUL)
            .name("baneful")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.USER().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.USER().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2)))
            )
        );
		register(Ability.build()
			.id(BATTLE_ARMOUR)
			.name("armour")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE) 
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
				// TODO: battle armour protects the user from the secondary effects of moves
		);
		register(Ability.build()
			.id(BERSERK)
			.name("berserk")
		);
		register(Ability.build()
			.id(BITTER_TOUCH)
			.name("bitter touch")
		);
		register(Ability.build()
			.id(BLACK_HEART)
			.name("Black Heart")
		);
		register(Ability.build()
			.id(BLISTERING_HEAT)
			.name("blistering heat")
		);
		register(Ability.build() // Increases the damage of the user's health draining moves
			.id(BLOODSUCKER)
			.name("bloodsucker")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.HEALTH_DRAIN), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(BOAST)
			.name("boast")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(new ConditionalEffect(
					Comparator.greaterThan()
						.left(new ValueConstant(Value.USER().MATTACK()))
						.right(new ValueConstant(Value.TARGET().MATTACK())),
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1)),
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(-1))
				))
			)
		);
		register(Ability.build()
			.id(BONEHEADED)
			.name("boneheaded")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_TAKE_RECOIL)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(BRAVE_CUB)
			.name("Brave Cub")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BUG),
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.DARK),
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.SPIRIT)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(BRUTAL_WRATH)
			.name("brutal wrath")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.TARGET().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.TARGET().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.addValue(Value.USER().MOVE().CRIT_CHANCE(), new Constant(3))) // move should always crit now
			)
		);
		register(Ability.build()
			.id(BRUTE_FORCE)
			.name("brute force")
			// TODO implement boosts for moves with secondary effects 
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELFS_MOVE_USE_SECONDARY_EFFECTS)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(BUG_FEVER)
			.name("bug fever")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(2))
				)
			)
		);
		register(Ability.build()
			.id(BULLY)
			.name("bully")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_DEFENSIVE_ABILITY)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.TARGET().HEIGHT()))
					.right(new ValueConstant(Value.USER().HEIGHT()))
				)
				// if the user is shorter than the target, bully does not activate so the target can use their defensive ability
			)
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_DEFENDING_STAT_BOOSTS)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.TARGET().HEIGHT()))
					.right(new ValueConstant(Value.USER().HEIGHT()))
				)
			)
		);
		register(Ability.build()
			.id(BURGLAR)
			.name("burglar")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_REMOVE_ITEM, TARGET))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, USER))
				.effect(new ConditionalEffect(
					new Equals<>(Value.USER().ITEM_ID(), ItemId.NO_ITEM),
					new EffectSequence(List.of(

					)),
					new EffectSequence(List.of(

					))
				))
			)
		);
		// increases the damage of the users fire type moves by 25%
		register(Ability.build()
			.id(BURNING_RAGE)
			.name("burning rage")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(BURSTING_SEAMS)
			.name("bursting seams")
		);
		register(Ability.build() // doubles exclusive ability
			.id(CADDIE)
			.name("caddie")
		);
		register(Ability.build() // doubles exclusive ability
			.id(CAPTIVATING)
			.name("captivating")
		);
		register(Ability.build() // useless
			.id(CAROL)
			.name("carol")
		);
		register(Ability.build()
			.id(CAVERNOUS)
			.name("Cavernous")
		);
		register(Ability.build()
			.id(CHARGED_ARC)
			.name("charged arc")
		);
		register(Ability.build()
			.id(CHILL)
			.name("chill")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(List.of(
					SetType.move(TypeId.ICE),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				)))
			)
		);
		// increases the damage of the user's ice type moves by 25%
		register(Ability.build()
			.id(CHILLING_PASSION)
			.name("chilling passion")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(CIRCADIAN)
			.name("circadian")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.event(ConstantRuleId.START_OF_TURN)
				.effect(new ConditionalEffect(
					Conditions.isOdd(new ValueConstant(Value.TURN_NUMBER())),
					new EffectSequence(List.of(
						SetType.userPrimary(TypeId.LIGHT),
						SetType.userSecondary(TypeId.NULL)
					)),
					new EffectSequence(List.of(
						SetType.userPrimary(TypeId.DARK),
						SetType.userSecondary(TypeId.NULL)
					))
				))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build()
			.id(CLINGY)
			.name("clingy")
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ITEM)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(CLUTCH)
			.name("clutch")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_CLUTCHES)
				.condition(Conditions.userIsAtFullHealth())
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, TARGET))
				.effect(new ClutchEffect())
			)
		);
		register(Ability.build()
			.id(COMBATIVE)
			.name("combative")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_STATS_LOWERED_BY_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(2))
				)
			)
		);
		register(Ability.build()
			.id(COMBUSTIBLE)
			.name("combustible")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE))
				.effect(ApplyMechanic.toUser(LoomianMechanicId.COMBUSTIBLE))
			)
		);
		register(Ability.build() // doubles exclusive ability
			.id(COMMUNICATION)
			.name("communication")
		);
		register(Ability.build()
			.id(COMPLIANT)
			.name("compliant")
		);
		register(Ability.build()
			.id(CONDITIONED)
			.name("Conditioned")
			.rule(Rule.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(Comparator.greaterThan()
					.left(new ValueConstant(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.TYPE)))
					.right(new Constant(1)
				))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DAMAGE(), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(CONFIDENCE)
			.name("confidence")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_MOVE_FLINCH)
				.condition(new False())
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_FLINCH)
				.condition(new False())
			)
		);
		register(Ability.build() // doubles exclusive
			.id(CONSPIRE)
			.name("conspire")
		);
		register(Ability.build()
			.id(COSMIC_PRESSURE)
			.name("cosmic pressure")
			.gate(Gate.build()
				.event(GateForAllId.CAN_WEATHER_EFFECTS_AFFECT)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(COURSING_VENOM) // when hit by a toxic type move, absorb it to boost your own toxic type damage by 1.5x
			.name("coursing venom")
			.rule(Rule.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.TOXIC))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
				.effect(new EffectSequence(List.of(
					ApplyMechanic.toUser(LoomianMechanicId.COURSING_VENOM)
				)))
			)
		);
		register(Ability.build()
			.id(DAUNTLESS)
			.name("dauntless")
		);
		register(Ability.build() // doubles exclusive
			.id(DAWN)
			.name("dawn")
		);
		register(Ability.build()
			.id(DEAD_OF_WINTER)
			.name("Dead of Winter")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
				.effect(SetWeather.userSetting(WeatherId.SNOWSTORM, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(DEEP_FROSTBITE)
			.name("deep frostbite")
		);
		register(Ability.build()
			.id(DEFENSIVE_PRIORITY)
			.name("defensive priority")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(ApplyStatChanges.userChangingUser()
					.mdefense(new Constant(1))
					.rdefense(new Constant(1))
					.speed(new Constant(-2)))
					
			)
		);
		register(Ability.build()
			.id(DEMANDING)
			.name("demanding")
		);
		// increases the power of the users water type moves by 1.25x
		register(Ability.build()
			.id(DEPTHS_BORNE)
			.name("depths borne")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(DESIGNATED_CHOMPERS)
			.name("designated chompers")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_MOVE_MAKE_CONTACT)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), false))
			)
		);
		register(Ability.build()
			.id(DETONATE)
			.name("Detonate")
		);
		register(Ability.build()
			.id(DEVIOUS)
			.name("devious")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_DEFENSIVE_ABILITY)
				.condition(new NoCondition())
			)
		);
		register(Ability.build()
			.id(DIRT_DASH)
			.name("Dirt Dash")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.duststormActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(DISENCHANT)
			.name("disenchant")
		);
		register(Ability.build()
			.id(DO_OR_DIE)
			.name("do or die")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_MOVE_DEALT_DAMAGE)
				.effect(RemoveHealth.user(Numbers.sixteenthUserMaxHealth()))
			)
		);
		register(Ability.build()
			.id(DOUBLE_STRIKE)
			.name("double strike")
		);
		register(Ability.build()
            .id(DRAINAGE)
            .name("drainage")
			.rule(Rule.build()
                .event(RuleForUserId.SELF_PRE_DRAINING_HEALTH) 
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.HEALTH_DRAIN), new Constant(1.5)))
            )
        );
		register(Ability.build()
			.id(DRIVEN)
			.name("driven")
		);
		register(Ability.build()
			.id(DUST_DEVIL)
			.name("dust devil")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.duststormActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build() // doubles exclusive
			.id(DUSK)
			.name("dusk")
		);
		register(Ability.build()
			.id(EAGER)
			.name("eager")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
			.gate(Gate.build()	
				.event(GateForUserId.CAN_SELF_USE_SUPPORT_MOVES)
				.condition(new False())
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_USE_ITEMS)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(EARTHMOVER)
			.name("Earthmover")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.EARTH),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(RestoreHealth.user(Numbers.quarterUserMaxHealth()))
			)
		);
		register(Ability.build()
			.id(EFFULGENT)
			.name("effulgent")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_CONSUME_ITEM)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(ELDER_MANE)
			.name("Elder Mane")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SPIRIT)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
			)
		);
		register(Ability.build()
			.id(ELECTRIFY)
			.name("Electrify")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					SetType.move(TypeId.ELECTRIC),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)
		);
		register(Ability.build()
			.id(ELUSIVE)
			.name("elusive")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.userHasStatusAilment())
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(2/3)))
			)
		);
		register(Ability.build()
			.id(ENCHANT)
			.name("enchant")
		);
		register(Ability.build()
			.id(ENCHANTED_COAT)
			.name("enchanted coat")
			.rule(Rule.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(Comparator.greaterThan()
					.left(new ValueConstant(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.TYPE)))
					.right(new Constant(1))
				)
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DAMAGE(), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(ENERGY_FEEDING)
			.name("Energy Feeding")
		);
		register(Ability.build()
			.id(EXPERTISE)
			.name("expertise")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(EXTENDED_REACH)
			.name("Extended Reach")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_MOVE_MAKE_CONTACT)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(EXTRATERRESTRIAL)
			.name("Extraterrestrial")
			// TODO implement sure hit effect
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0.9)))
			)
		);
		register(Ability.build()
			.id(FACADE)
			.name("facade")
			// TODO implement
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build() // reduces the damage taken from air type attacks and boosts it's rattack one stage when hit by one
			.id(FANNING_FLAME)
			.name("fanning flame")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, TARGET))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FESTIVE_SPIRIT)
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build()
			.id(FINESSE)
			.name("finesse")
		);
		register(Ability.build()
			.id(FLUTTER)
			.name("flutter")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.POWDER), true),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_HAZARDS_AFFECT_SELF)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(FOG_SUMMON)
			.name("fog summon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.FOG, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(FORESIGHT)
			.name("foresight")
			.rule(Rule.build()
				.event(ConstantRuleId.START_OF_TURN)
				.condition(new Equals<>(Value.USER().CURRENT_HEALTH(), Value.USER().HEALTH()))
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(Effects.addValue(Value.USER().PRIORITY(), new Constant(1)))
			)
		);
		register(Ability.build() // completed
			.id(FORGE)
			.name("forge")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, TARGET))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FORTISSIMO)
			.name("fortissimo")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.SOUND), true),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build() // completed
			.id(FRAIL_ARMOUR)
			.name("frail armour")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(ApplyStatChanges.userChangingUser()
					.mdefense(new Constant(-1))
					.speed(new Constant(2))
				)
			)
		);
		register(Ability.build()
			.id(FRENZY)
			.name("frenzy")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.effect(RestoreEnergy.user(new ValueConstant(Value.TARGET().CURRENT_ENERGY())))
			)
		);
		register(Ability.build()
			.id(GAIN_FEEDING)
			.name("Gain Feeding")
		);
		register(Ability.build() // completed
			.id(GLIDE)
			.name("glide")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1))
				)
			)
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1))
				)
			)
		);
		register(Ability.build() // completed
			.id(GLOOMY)
			.name("gloomy")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.fogActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(GLUTTON)
			.name("glutton")
		);
		register(Ability.build()
			.id(GORGE)
			.name("gorge")
		);
		register(Ability.build()
			.id(GREED)
			.name("greed")
		);
		register(Ability.build() // doubles exclusive ability
			.id(GUARDIAN)
			.name("guardian")
		);
		register(Ability.build() // immunity to bomb moves
			.id(GUMMY)
			.name("gummy")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.EXPLOSIVE), true),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build() // technician from pokemon
			.id(GURU)
			.name("guru")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(Comparator.lessThanEqualTo()
					.left(new INumberValueConstant(Value.OPP().MOVE().BASE_POWER()))
					.right(new Constant(60)))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.6)))
			)
		);
		register(Ability.build()
			.id(HAG)
			.name("hag")
		);
		register(Ability.build()
			.id(HANDY)
			.name("handy")
		);
		register(Ability.build()
			.id(HARD_CANDY)
			.name("hard candy")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(HARMONIZE)
			.name("harmonize")
		);
		register(Ability.build()
			.id(HASTY)
			.name("hasty")
		);
		register(Ability.build()
			.id(HEAT_SUMMON)
			.name("heat summon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.HEATWAVE, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(HEAVY_FISTS)
			.name("heavy fists")
		);
		register(Ability.build()
			.id(HERD_BEHAVIOR)
			.name("herd behavior")
		);
		register(Ability.build()
			.id(HIGH_EXPLOSIVE)
			.name("high explosive")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.EXPLOSIVE), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELFS_MOVE_USE_SECONDARY_EFFECTS)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(HIGH_PRESSURE)
			.name("high pressure")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(HIGH_STRUNG)
			.name("High Strung")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_DAMAGED_BELOW_HALF)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))
					.rattack(new Constant(1))
					.speed(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(HOTFOOT)
			.name("hotfoot")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.heatwaveActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(HOVER)
			.name("hover")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.EARTH),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_HAZARDS_AFFECT_SELF)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(HYDRATE)
			.name("hydrate")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(RestoreEnergy.target(Numbers.quarterTargetMaxEnergy()))
			)
		);
		register(Ability.build()
			.id(HYDRO_VORTEX)
			.name("hydro vortex")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1)
				))
			)
		);
		register(Ability.build()
			.id(IDIOSYNCRATIC)
			.name("idiosyncratic")
		);
		register(Ability.build()
			.id(IGNORANT)
			.name("ignorant")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_DEFENDING_STAT_BOOSTS)
				.event(GateForTargetId.CAN_OPP_USE_DEFENDING_STAT_DROPS)
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
			)
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_ATTACKING_STAT_BOOSTS)
				.event(GateForTargetId.CAN_OPP_USE_ATTACKING_STAT_DROPS)
				.condition(new GateCondition(GateForAllId.CAN_USE_OFFENSIVE_ABILITY, USER))
			)
		);
		register(Ability.build()
			.id(ILLUMINATE)
			.name("illuminate")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					SetType.move(TypeId.LIGHT),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)		
		);
		register(Ability.build()
			.id(IMMUNIZED)
			.name("immunized")
		);
		register(Ability.build()
			.id(IMPOSE)
			.name("impose")
		);
		register(Ability.build()
			.id(INCANDESCENT)
			.name("incandescent")
		);
		register(Ability.build()
			.id(INFERNO)
			.name("inferno")
		);
		register(Ability.build() // decreases the damage done by ice and fire attacks to the user by 50%
			.id(INSULATED)
			.name("insulated")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, TARGET))
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
			)
		);
		register(Ability.build()
			.id(INTENSIFY)
			.name("intensify")
		);
		register(Ability.build()
			.id(INTERN)
			.name("intern")
		);
		register(Ability.build() // increase the damage of the users earth type moves by 25%
			.id(LAND_BORNE)
			.name("land borne")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.EARTH))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(LAZY)
			.name("lazy")
		);
		register(Ability.build()
			.id(LIFE_FORCE)
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build()
			.id(LIGHTNING_ROD)
			.name("lightning rod")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1)
				))
			)
		);
		register(Ability.build()
			.id(LOCKED_IN)
			.name("Locked In")
		);
		register(Ability.build()
			.id(LUCK_OF_THE_SEA)
			.name("luck_of_the sea")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.CRIT), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
			// increases crit chance
		);
		register(Ability.build()
			.id(LUCKY)
			.name("lucky")
		);
		register(Ability.build()
			.id(MADCAP)
			.name("madcap")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.RECOIL), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Ability.build()
			.id(MALWARE)
			.name("malware")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE) // if hit by a move
				.condition(new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, TARGET))
				.effect(SetAbility.target(MALWARE))
			)
		);
		register(Ability.build()
			.id(MARKSMAN)
			.name("marksman")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.CRIT), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
		);
		register(Ability.build()
			.id(MASK_SWAP)
			.name("mask swap")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.effect(new ConditionalEffect(
					new Equals<>(Value.USER().MOVE().TYPE(), Value.USER().PRIMARY_TYPE()), // if the user's move doesnt match the type of move they are using, gain it as a secondary type
					SetType.userSecondary(TypeId.NULL),
					SetType.userSecondary(Value.USER().MOVE().TYPE())
				))
			)
		);
		register(Ability.build()
			.id(MASTER)
			.name("master")
		);
		register(Ability.build()
			.id(MEAN_SPIRITED)
			.name("mean spirited")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.targetHasStatusAilment())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Ability.build() // doubles exclusive
			.id(MEDIC)
			.name("medic")
		);
		register(Ability.build()
			.id(MERGE)
			.name("merge")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_USE_SUPPORT_MOVES)
				.condition(new False())
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(MESMERIZING)
			.name("mesmerizing")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_USE_MOVE)
				.condition(Comparator.lessThanEqualTo()
					.left(new ValueConstant(Value.USER().MOVE().PRIORITY()))
					.right(new Constant(0))
				)
			)
		);
		register(Ability.build()
			.id(METALLIC)
			.name("metallic")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.effect(new AlterTypeChart(TypeId.ICE, 		TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.AIR, 		TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.BUG, 		TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.METAL, 	TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.MIND, 	TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.ANCIENT, 	TypeId.METAL, 0.5))
				.effect(new AlterTypeChart(TypeId.SIMPLE, 	TypeId.METAL, 0.5))
			)
		);
		register(Ability.build()
			.id(METAMORPHOSIS)
			.name("metamorphosis")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					SetType.move(TypeId.BUG),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)
		);
		register(Ability.build()
			.id(MIMIC)
			.name("mimic")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(
					ApplyStatChanges.userChangingUser()
						.mattack(	new ValueConstant(Value.TARGET().MATTACK_STAGE_MODIFIER()))
						.mdefense(	new ValueConstant(Value.TARGET().MDEFENSE_STAGE_MODIFIER()))
						.rattack(	new ValueConstant(Value.TARGET().RATTACK_STAGE_MODIFIER()))
						.rdefense(	new ValueConstant(Value.TARGET().RDEFENSE_STAGE_MODIFIER()))
						.speed(		new ValueConstant(Value.TARGET().SPEED_STAGE_MODIFIER()))
						.accuracy(	new ValueConstant(Value.TARGET().ACCURACY_STAGE_MODIFIER()))
						.evasion(	new ValueConstant(Value.TARGET().EVASION_STAGE_MODIFIER()))
				)
			)
		);
		register(Ability.build()
			.id(MOTIVATIONAL)
			.name("motivational")
		);
		register(Ability.build()
			.id(MYCOTIC)
			.name("mycotic")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetFieldMechanic.fungusCurse().withMaxAge(5))
			)
		);
		register(Ability.build()
			.id(MYSTERIOUS_CLOAK)
			.name("mysterious cloak")
		);
		register(Ability.build()
			.id(MYSTERY_TOXINS)
			.name("mystery toxins")
		);
		register(Ability.build()
			.id(MYSTIC_TONE)
			.name("mystic tone")
		);
		register(Ability.build()
			.id(NEUTRALIZE)
			.name("neutralize")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Not(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE)))
				.effect(new EffectSequence(
					SetType.move(TypeId.SIMPLE),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)
		);
		register(Ability.build()
			.id(NIGHT_LIGHT)
			.name("night light")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.75)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.TARGET().MOVE().TYPE(), TypeId.DARK))
				.effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(NIGHTMARISH)
			.name("nightmarish")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.condition(new Equals<>(Value.TARGET().STATUS_ID(), StatusId.SLEEP))
				.effect(RemoveHealth.target(Numbers.eighthTargetMaxHealth()))
			)
		);
		register(Ability.build()
			.id(NOXIOUS_RAZORBACK)
			.name("Noxious Razorback")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.TARGET().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.effect(RemoveHealth.target(Numbers.eighthTargetMaxHealth()))
				.effect(new ConditionalEffect(
					new RandomCondition(new Constant(0.1)),
					ApplyStatus.toTarget(StatusId.POISON)
				))
			)
		);
		register(Ability.build()
			.id(NOXIOUS_WEEDS)
			.name("noxious weeds")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyMechanic.toUser(LoomianMechanicId.NOXIOUS_WEEDS))
			)
		);
		register(Ability.build()
			.id(OBSIDIAN_HEART)
			.name("obsidian heart")
			//.rule() // increase the cost of ancient type moves by 10%
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(ODD_HUSK)
			.name("odd husk")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPPS_MOVE_SECONDARY_EFFECTS_AFFECT_SELF)
				.condition(new Not(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)))
			)
		);
		// clear the status effects before they occur
		register(Ability.build()
			.id(ONE_OF_MANY)
			.name("one_of many")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.effect(ClearStatus.user())
				.effect(ClearStatChanges.userDrops())
				.effect(RemoveMechanic.fromUser(LoomianMechanicId.PROVOKE))
				.effect(RemoveMechanic.fromUser(LoomianMechanicId.SAP_PLANT))
				.effect(RemoveMechanic.fromUser(LoomianMechanicId.TEASE))
			)
		);
		// the current rule implementations change the context when dispatching the same event to either side, so targetting the move that will be used cannot be done
		/// to fix another dispatch function must exists where no context switching occurs with constant rules
		register(Ability.build()
			.id(OVERCHARGED)
			.name("overcharged")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.TARGET().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(OVERCLOCK)
			.name("overclock")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.thunderstormActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(OVERSHADOW)
			.name("overshadow")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					SetType.move(TypeId.DARK),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)
		);
		register(Ability.build()
			.id(OXIDIZE)
			.name("oxidize")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED))
				.condition(new Or(
					Conditions.rainActive(),
					Conditions.strongWindsActive()
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DEFENDING_STAT), new Constant(1.5)))
			)
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(PARTING_GIFT)
			.name("parting gift")
		);
		// doubles exclusive
		register(Ability.build()
			.id(PARTNERSHIP)
			.name("partnership")
		);
		register(Ability.build()
			.id(PARTY_TRICK)
			.name("party trick")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(ApplyMechanic.toUser(LoomianMechanicId.WILL_SET_BOH))
			)
		);
		register(Ability.build() // increases the lifespan of some side mechanics
			.id(PERSISTENCE)
			.name("persistence")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_NEAR_ENCHANTMENT)
				.effect(Effects.addValue(Value.USER().SIDE_MECHANIC(SideMechanicId.NEAR_ENCHANTMENT).MAX_AGE(), new Constant(1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_FAR_ENCHANTMENT)
				.effect(Effects.addValue(Value.USER().SIDE_MECHANIC(SideMechanicId.FAR_ENCHANTMENT).MAX_AGE(), new Constant(1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_BANE_OF_HASTE)
				.effect(Effects.addValue(Value.FIELD_MECHANIC(FieldMechanicId.BANE_OF_HASTE).MAX_AGE(), new Constant(1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_BANE_OF_WIELDING)
				.effect(Effects.addValue(Value.FIELD_MECHANIC(FieldMechanicId.BANE_OF_WIELDING).MAX_AGE(), new Constant(1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_FUNGUS_CURSE)
				.effect(Effects.addValue(Value.FIELD_MECHANIC(FieldMechanicId.FUNGUS_CURSE).MAX_AGE(), new Constant(1)))
			)
		);
		register(Ability.build()
			.id(PETRIFYING)
			.name("petrifying")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new GateCondition(GateForAllId.CAN_BE_INTIMIDATED, TARGET))
				.effect(ApplyStatChanges.userChangingTarget().mattack(new Constant(-1)))
			)
		);
		register(Ability.build()
			.id(PINCER_TRAP)
			.name("pincer trap")
		);
		register(Ability.build()
			.id(PITCH_BLACK)
			.name("pitch black")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build() // doubles exclusive
			.id(PLAYFUL)
			.name("playful")
		);
		register(Ability.build()
			.id(PLUVIAL)
			.name("pluvial")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.condition(Conditions.rainActive())
				.effect(RestoreHealth.user(Numbers.sixteenthUserMaxHealth()))
			)
		);
		register(Ability.build()
			.id(POWER_CLAW)
			.name("power claw")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.SLASHING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_MOVE_CRIT)
				.condition(new Not(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.SLASHING), true)))
			)
		);
		register(Ability.build()
			.id(POWER_JAW)
			.name("power jaw")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))	
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(POWER_LEGS)
			.name("power legs")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.KICKING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))	
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.KICKING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(POWER_NAPPER)
			.name("power napper")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPS_OUT)
				.effect(RestoreHealth.user(Numbers.thirdOfUserMissingHealth()))
			)
		);
		register(Ability.build()
			.id(PREMONITION)
			.name("premonition")
		);
		register(Ability.build()
			.id(PRISMATIC)
			.name("prismatic")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_DAMAGING)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
					ApplyMechanic.toUser(LoomianMechanicId.PRISMATIC)
				)))
			)
		);
		register(Ability.build()
			.id(PROTECTIVE_SHELL)
			.name("protective shell")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_MOVE_CRIT)
				.condition(new Not(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)))
			)
		);
		register(Ability.build()
			.id(PROWLER)
			.name("prowler")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.fogActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(PUGILIST)
			.name("pugilist")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.PUNCHING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))	
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.PUNCHING), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(PUNCTURE)
			.name("puncture")
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_DEFENDING_STAT_BOOSTS)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), false))
				.condition(new Not(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE)))
			)
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_ABILITY)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), false))
				.condition(new Equals<>(Value.USER().ABILITY_TAG(AbilityTagId.PUNCTURABLE), false))
			)
			.gate(Gate.build()
				.event(GateForTargetId.CAN_OPP_USE_ITEM)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.BITING), false))
				.condition(new Equals<>(Value.USER().ITEM_TAG(ItemTagId.PUNCTURABLE), false))
			)
		);
		register(Ability.build()
			.id(PYRO_PRO)
			.name("pyro pro")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0.9)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(PYRO)
			.name("pyro")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(QUICK_RECOVERY)
			.name("quick recovery")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPS_OUT)
				.effect(ClearStatus.user())
			)
		);
		register(Ability.build()
			.id(RADIANCE)
			.name("radiance")
		);
		register(Ability.build()
			.id(RAGING_FIRE)
			.name("raging fire")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(RestoreEnergy.target(Numbers.quarterTargetMaxEnergy()))
			)
		);
		register(Ability.build()
			.id(RAIL_GUN)
			.name("rail gun")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(RAIN_RUSH)
			.name("rain rush")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.rainActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(RAIN_SUMMON)
			.name("rain summon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.RAIN, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(RAVENOUS)
			.name("ravenous")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(ClearStatChanges.targetBoosts())
			)
		);
		register(Ability.build()
			.id(RAZOR_SHARP)
			.name("razor sharp")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.addValue(Value.USER().CRIT_CHANCE(), new Constant(1)))	
			)
		);
		register(Ability.build()
			.id(REAPER)
			.name("reaper")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.effect(new ConditionalEffect(
					new ListContains<>(Value.TARGET().TYPES(), TypeId.LIGHT),
					RestoreHealth.user(Numbers.halfUserMaxHealth()),
					RestoreHealth.user(Numbers.quarterUserMaxHealth())
				))
			)
		);
		register(Ability.build()
			.id(RECHARGEABLE)
			.name("rechargeable")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1)
				))
			)
		);
		// users mdefense and rdefense are boosted by 10%
		register(Ability.build()
			.id(RECRUIT)
			.name("recruit")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.TARGET().MOVE().MOVE_MODIFIER(MoveModifierId.DEFENDING_STAT), new Constant(1.1)))
			)
		);
		register(Ability.build()
			.id(RECURRENT)
			.name("recurrent")
		);
		register(Ability.build()
			.id(REFLECTIVE)
			.name("reflective")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.LIGHT),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build()
			.id(REGIFT)
			.name("regift")
		);
		register(Ability.build()
			.id(REGURGITATE)
			.name("regurgitate")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_FAINTED)
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.effect(ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
					.rdefense(new Constant(-1))
				)
			)
		);
		register(Ability.build()
			.id(REIGN)
			.name("reign")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_ENERGY_CALC)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(0.9)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.OPP_PRE_ENERGY_CALC)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ENERGY_COST), new Constant(1.1)))
			)
		);
		register(Ability.build()
			.id(REMORSELESS)
			.name("remorseless")
			.rule(Rule.build()
				.event(RuleForTargetId.OPP_FAINTED)
				.event(RuleForTargetId.OPP_KNOCKED_OUT)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))	
					.rattack(new Constant(1))	
				)
			)
		);
		register(Ability.build()
			.id(REPLICATE)
			.name("replicate")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Equals<>(Value.TARGET().ABILITY_TAG(CANNOT_BE_REPLICATED), false))
				.effect(SetAbility.user(Value.TARGET().ABILITY_ID()))
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build()
			.id(REPUGNANT)
			.name("repugnant")
		);
		register(Ability.build()
			.id(RESENTFUL)
			.name("resentful")
			.rule(Rule.build()
				.event(RuleForTargetId.OPP_KNOCKED_OUT)
				.effect(RemoveHealth.target(
					new Multiply(new ValueConstant(Value.USER().MOVE().DAMAGE()), new Constant(0.5))
				))
			)
		);
		register(Ability.build()
			.id(RESILIENCE)
			.name("resilience")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.effect(ApplyStatChanges.userChangingUser().mdefense(new Constant(1)))
			)
		);
		register(Ability.build()
			.id(REV_UP)
			.name("rev up")
		);
		register(Ability.build()
			.id(REVERBERATE)
			.name("reverberate")
		);
		register(Ability.build()
			.id(ROYAL_DECREE)
			.name("royal decree")
			.rule(Rule.build()
                .event(RuleForUserId.SELF_PRE_MOVE) // before anything about the move is actually used
                .effect(new AlterTypeChart(TypeId.ELECTRIC, TypeId.EARTH, 1))
            )
		);
		register(Ability.build()
			.id(RUSH_HOUR)
			.name("rush hour")
		);
		register(Ability.build()
			.id(SAFETY_POT)
			.name("safety pot")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(SALVAGE)
			.name("salvage")
		);
		register(Ability.build()
			.id(SCORCHING_SKIN)
			.name("scorching skin")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.TARGET().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new RandomCondition(new Constant(0.3)))
				.effect(ApplyStatus.toTarget(StatusId.BURN))
			)
		);
		register(Ability.build()
			.id(SEIZE)
			.name("seize")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Equals<>(Value.TARGET().MOVE().MOVE_TAG(MoveTagId.PIVOT), true))
				.condition(new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER))
			)
		);
		register(Ability.build()
			.id(SENDOFF)
			.name("sendoff")
		);
		register(Ability.build()
			.id(SELF_DESTRUCT)
			.name("Self Destruct")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_FAINTED)
				.effect(ClearHazards.fromUser())
			)
		);
		register(Ability.build()
			.id(SHAKEDOWN)
			.name("shakedown")
		);
		register(Ability.build()
			.id(SHARP_CLAWS)
			.name("sharp claws")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build() // when hit by a contact move, the attacker loses 1/16 of their max hp
			.id(SHARP_EDGES)
			.name("sharp edges")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, TARGET))
				.effect(RemoveHealth.target(Numbers.sixteenthTargetMaxHealth()))
			)
		);
		register(Ability.build()
			.id(SHARP_FOCUS)
			.name("Sharp Focus")
		);
		register(Ability.build()
			.id(SHIFTY)
			.name("Shifty")
		);
		register(Ability.build() // boosts the power of air type moves by 1.25x
			.id(SKY_BORNE)
			.name("sky borne")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.AIR))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(SLICK_SCALES)
			.name("Slick Scales")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(SLICK_SHELL)
			.name("slick shell")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(SLIMY)
			.name("slimy")
		);
		register(Ability.build()
			.id(SLY)
			.name("sly")
		);
		register(Ability.build()
			.id(SNOWBALL)
			.name("Snowball")
		);
		register(Ability.build()
			.id(SNOW_GLIDER)
			.name("Snow Glider")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_CALC_SPEED_ORDER)
				.condition(Conditions.snowstormActive())
				.effect(Effects.multiplyValue(Value.USER().SIDE_MODIFIER(SideModifierId.SPEED), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(SOB)
			.name("sob")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(ClearStatChanges.targetChanges())
			)
		);
		register(Ability.build()
			.id(SOLAR_POWERED)
			.name("Solar Powered")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.LIGHT),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(RestoreEnergy.target(Numbers.quarterTargetMaxEnergy()))
			)
		);
		register(Ability.build()
			.id(SOUL_HOARDER)
			.name("Soul Hoarder")
		);
		register(Ability.build()
			.id(SOUL_SIPHON)
			.name("soul siphon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_KNOCKED_OUT_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1))	
				)
			)
		);
		register(Ability.build()
			.id(SPECIALIZATION)
			.name("specialization")

		);
		register(Ability.build()
			.id(SPINE_BREAK)
			.name("spine break")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, TARGET))
				.effect(SetHazards.poisonBarbs())
			)
		);
		register(Ability.build()
			.id(SPOILS_OF_WAR)
			.name("Spoils of War")
		);
		register(Ability.build()
			.id(SPONGE)
			.name("sponge")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(RestoreHealth.user(Numbers.quarterUserMaxHealth()))
			)
		);
		register(Ability.build()
			.id(STAUNCH)
			.name("staunch")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_GET_STAT_CHANGES)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(STICKY)
			.name("Sticky")
		);
		register(Ability.build()
			.id(STOKED)
			.name("Stoked")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.heatwaveActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(SUGAR_RUSH)
			.name("sugar rush")
		);
		register(Ability.build()
			.id(SURROGATE)
			.name("surrogate")
		);
		register(Ability.build()
			.id(SWAMPY)
			.name("swampy")
		);
		register(Ability.build()
			.id(SWEET_AROMA)
			.name("sweet aroma")
		);
		register(Ability.build()
			.id(SWEET_TOUCH)
			.name("sweet touch")
		);
		register(Ability.build()
			.id(TANK)
			.name("tank")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(TEMPER)
			.name("temper")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_DAMAGED_BELOW_HALF)
				.effect(ApplyStatChanges.userChangingUser().rattack(new Constant(1)))
			)
		);
		register(Ability.build()
			.id(TERRIFYING)
			.name("terrifying")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new GateCondition(GateForAllId.CAN_BE_INTIMIDATED, TARGET))
				.effect(ApplyStatChanges.userChangingTarget().mattack(new Constant(-1)))
			)
		);
		register(Ability.build()
			.id(TERRITORIAL)
			.name("territorial")
		);
		register(Ability.build()
			.id(THIRD_DEGREE_BURN)
			.name("third_degree burn")
		);
		register(Ability.build()
			.id(THRIVING_PACE)
			.name("thriving pace")
		);
		register(Ability.build()
			.id(THUNDER_SUMMON)
			.name("thunder summon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.THUNDERSTORM, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(TONE_DEAF)
			.name("tone deaf")
		);
		register(Ability.build()
			.id(TOTAL_ECLIPSE)
			.name("total eclipse")
		);
		register(Ability.build()
			.id(TOXIC_FILTER)
			.name("toxic filter")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_DAMAGING)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
					ApplyMechanic.toUser(LoomianMechanicId.TOXIC_FILTER)
				)))
			)
		);
		register(Ability.build()
			.id(TOXIC_SAC)
			.name("toxic sac")
		);
		register(Ability.build()
			.id(TOXIC_SPINES)
			.name("toxic spines")
		);
		register(Ability.build()
			.id(TOXIC_TOUCH)
			.name("toxic touch")
		);
		register(Ability.build()
			.id(TRADER)
			.name("trader")
		);
		register(Ability.build()
			.id(TRASH_ARMOUR)
			.name("trash armour")
			.tag(PUNCTURABLE)
		);
		register(Ability.build()
			.id(TRIUMPH)
			.name("triumph")
		);
		register(Ability.build()
			.id(TUMULTUOUS)
			.name("tumultuous")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.strongWindsActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(TURBULENT)
			.name("turbulent")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					SetType.move(TypeId.LIGHT),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.2))
				))
			)
		);
		register(Ability.build()
			.id(TWO_FACE)
			// TODO implement
			.name("two face")
			.gate(Gate.build()
				.event(GateForUserId.CAN_REMOVE_SELFS_ABILITY)
				.condition(new False())
			)
			.tag(CANNOT_BE_REPLICATED)
		);
		register(Ability.build() // completed
			.id(UNGRACIOUS_HOST)
			.name("ungracious host")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.HEALTH_DRAIN), new Constant(-1)))
			)
		);
		register(Ability.build()
			.id(UPPER_HAND)
			.name("upper hand")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.TARGET().HAS_ACTED_THIS_TURN(), false),
					new Equals<>(Value.TARGET().HAS_SWAPPED_IN_THIS_TURN(), true)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(VENGEANCE)
			.name("vengeance")
		);
		register(Ability.build()
			.id(VENOMOUS)
			.name("venomous")
		);
		register(Ability.build()
			.id(VETERAN)
			.name("Veteran")
		);
		// increases the users damage when the user is poisoned
		register(Ability.build() // build
			.id(VICIOUS)
			.name("vicious")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_DAMAGING)
				.condition(new Or(
					new Equals<>(Value.USER().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.USER().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		// the user cannot fall asleep
		register(Ability.build()
			.id(VIGILANT)
			.name("vigilant")
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_FALL_ASLEEP)
				.condition(new False())
			)
		);
		// increases the users melee attack by 50% if they are inflicted by a status ailment and removes the damage drop from burn
		register(Ability.build() // completed
			.id(VIGOROUS)
			.name("vigorous")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.userHasStatusAilment())
				.condition(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.5)))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().STATUS_ID(), StatusId.BURN))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(2)))
			)
		);
		// reduces the energy cost of the user's sound based moves by 25%
		register(Ability.build()
			.id(VIRTUOSO)
			.name("virtuoso")
		);
		register(Ability.build()
			.id(VIRULENT_HYPNOSIS)
			.name("virulent hypnosis")
		);
		// the user cannot be forced off the field
		register(Ability.build()
			.id(VISCID)
			.name("viscid")
		);
		// increases the accuracy of the user's moves by 30%
		register(Ability.build() // completed
			.id(VIVID_SIGHT)
			.name("vivid sight")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_MOVE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.ACCURACY), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(VOLCANIC)
			.name("volcanic")
		);
		register(Ability.build()
			.id(WATCHER)
			.name("watcher")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.TARGET().HAS_ACTED_THIS_TURN(), true),
					new Equals<>(Value.TARGET().HAS_SWAPPED_IN_THIS_TURN(), true)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(1.3)))
			)
		);
		register(Ability.build() // completed
			.id(WEBBING)
			.name("webbing")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.condition(new GateCondition(GateForAllId.CAN_MOVE_MAKE_CONTACT, TARGET))
				.effect(ApplyStatChanges.targetChangingUser()
					.speed(new Constant(1))
				)
			)
		);
		// reduces the damage of brawler and mind type moves by 50%
		register(Ability.build() // completed
			.id(WHOLESOME)
			.name("wholesome")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BRAWLER),
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.MIND)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(MoveModifierId.DAMAGE), new Constant(0.5)))
			)
		);
		register(Ability.build()
			.id(WILDFIRE)
			.name("wildfire")
			.rule(Rule.build()
				.event(ConstantRuleId.PRE_TURN)
				.condition(new Equals<>(Value.USER().CURRENT_HEALTH(), Value.USER().HEALTH()))
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(Effects.addValue(Value.SELF().PRIORITY(), new Constant(1)))
			)
		);
		register(Ability.build() // completed
			.id(WIND_SUMMON)
			.name("wind summon")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(5)))
			)
		);
		register(Ability.build() // completed
			.id(WISE)
			.name("wise")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_DAMAGING)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.USER().MOVE().MOVE_MODIFIER(MoveModifierId.TYPE)))
					.right(new Constant(1))
				)
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(WOODSMAN)
			.name("woodman")
			.hook(Hook.build()
				.event(RuleForTargetId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT),
					new GateCondition(GateForAllId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1)
				))
			)
		);
    }
}
