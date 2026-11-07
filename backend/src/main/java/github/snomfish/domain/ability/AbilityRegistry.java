package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Conditions;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.FalseCondition;
import github.snomfish.functionality.condition.GateCondition;
import github.snomfish.functionality.condition.ListContains;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.ClutchEffect;
import github.snomfish.functionality.effect.effects.DealDirectSelfDamage;
import github.snomfish.functionality.effect.effects.DispatchEventEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.effect.effects.SetAbility;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumberValueConstant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.functionality.context.ContextSideId.*;
import static github.snomfish.functionality.event.ids.GateId.*;
import static github.snomfish.functionality.event.ids.InterceptorId.*;
import static github.snomfish.functionality.event.ids.RuleId.*;

public class AbilityRegistry {
    

    private static final Map<AbilityId, Ability> registry = new HashMap<>();


    private AbilityRegistry() {}


    public static Ability get(AbilityId id) {
        return registry.get(id);
    }


    private static void register(Ability ability) {
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
			.rule(AbilityRule.build()
				.event(POST_DAMAGE_EVENT, USER)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().CONTACT_TAG(), true),
					new GateCondition(CAN_REMOVE_TARGET_ABILITY)
				))
				.effect(new EffectSequence(List.of(
					SetAbility.userActual(Value.TARGET().ABILITY_ID()),
					SetAbility.user(Value.TARGET().ABILITY_ID()),
					SetAbility.targetActual(AbilityId.NO_ABILITY),
					SetAbility.target(AbilityId.NO_ABILITY),
					new DispatchEventEffect(GenericId.SELF_SWAPPED_IN, USER)
				)))
			)
			.setOncePerBattle()
		);
		register(Ability.build() // completed
			.id(ACE)
			.name("ace")
			.rule(AbilityRule.build()
                .event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER) // right before a moves damage is calculated
				.condition(new ListContains<>(Value.USER().TYPES(), Value.USER().MOVE().TYPE()))
				.effect(new SetValueEffect<>(Value.USER().MOVE().STAB_MODIFIER(), new Constant(2.0)))
            )
		);
		register(Ability.build() // completed
			.id(ADAPTABLE)
			.name("adaptable")
			.rule(AbilityRule.build()
				.event(IsolatedId.WEATHER_SET_EVENT)
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.userPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.userPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.userPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.userPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.userPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.userPrimary(TypeId.ELECTRIC))
				)))
			)
			.gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build() // completed
			.id(ADORABLE)
			.name("adorable")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
				)
			)
		);
		register(Ability.build()
			.id(AGGRESSIVE)
			.name("aggressive")
			.rule(AbilityRule.build()
				.event(USER_KNOCKED_OUT_TARGET_EVENT, USER)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))	
				)
			)
			.rule(AbilityRule.build()
				.event(TARGET_KNOCKED_OUT_USER_EVENT, TARGET)
				.effect(ApplyStatChanges.targetChangingTarget()
					.mattack(new Constant(1))	
				)
			)
		);
		register(Ability.build()
			.id(ALL_SEEING)
			.name("all_seeing")
		);
		register(Ability.build()
			.id(AMBUSH)
			.name("ambush")
		);
		register(Ability.build() // completed
			.id(ANALYZE)
			.name("analyze")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
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
			.rule(AbilityRule.build() // when the loomian is the user
				.event(GenericId.SELF_SWAPPED_IN) 
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
			.name("applied_frustration")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_STATS_LOWERED_BY_OPP)
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
			.rule(AbilityRule.build()
                .event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET) // before the targets move damage is calculated
				.condition(new GateCondition(CAN_USE_DEFENSIVE_ABILITY))
                .effect(new ConditionalEffect(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
					Effects.multiplyValue(Value.USER().MOVE().TYPE_MODIFIER(), new Constant(0.5))
				))
            )
        );
        register(Ability.build() // completed
            .id(ASSERTIVE)
            .name("assertive")
			.rule(AbilityRule.build()
                .event(PRE_MOVE_EVENT, USER) // before anything about the move is actually used
                .effect(new AlterTypeChart(TypeId.BRAWLER, TypeId.SPIRIT, 1))
            )
        );
        register(Ability.build() // completed
            .id(AWAKENING)
            .name("awakening")
			.rule(AbilityRule.build()
                .event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER) // right before a moves damage is calculated
                .effect(new SetValueEffect<>(Value.USER().MOVE().STAB_MODIFIER(), new Constant(1.5)))
            )
        );
        register(Ability.build() // completed
            .id(BANEFUL)
            .name("baneful")
			.rule(AbilityRule.build()
                .event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Or(
					new Equals<>(Value.USER().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.USER().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
        );
		register(Ability.build()
			.id(BATTLE_ARMOUR)
			.name("armour")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER) 
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.25)))
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
			.id(BLISTERING_HEAT)
			.name("blistering heat")
		);
		register(Ability.build() // Increases the damage of the user's health draining moves
			.id(BLOODSUCKER)
			.name("bloodsucker")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().HEALTH_DRAIN_TAG(), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(BOAST)
			.name("boast")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
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
				.event(CAN_TAKE_RECOIL, USER)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(BRUTAL_WRATH)
			.name("brutal wrath")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
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
			.gate(Gate.build()
				.event(CAN_USE_SECONDARY_EFFECTS, USER)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(BUG_FEVER)
			.name("bug_fever")
			.rule(AbilityRule.build()
				.event(USER_KNOCKED_OUT_TARGET_EVENT, USER)
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
				.event(CAN_USE_DEFENSIVE_ABILITY, USER)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.TARGET().HEIGHT()))
					.right(new ValueConstant(Value.USER().HEIGHT()))
				)
				// if the user is shorter than the target, bully does not activate so the target can use their defensive ability
			)
			.gate(Gate.build()
				.event(CAN_DEFENDING_STAT_USE_STAT_BOOSTS, USER)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.USER().HEIGHT()))
					.right(new ValueConstant(Value.TARGET().HEIGHT()))
				)
			)
		);
		register(Ability.build()
			.id(BURGLAR)
			.name("burglar")
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, USER)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().CONTACT_TAG(), true),
					new GateCondition(CAN_REMOVE_TARGET_ITEM)
				))
				.effect(new ConditionalEffect(
					new Equals<>(Value.USER().ITEM_ID(), ItemId.NO_ITEM),
					new EffectSequence(List.of(

					)),
					new EffectSequence(List.of(

					))
				))
			)
		);
		register(Ability.build()
			.id(BURGLAR)
			.name("burgler")
		);
		// increases the damage of hte users fire type moves by 25%
		register(Ability.build()
			.id(BURNING_RAGE)
			.name("burning_rage")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(BURSTING_SEAMS)
			.name("bursting_seams")
		);
		register(Ability.build() // doubles exclusive ability
			.id(CADDIE)
			.name("caddie")
		);
		register(Ability.build()
			.id(CAPTIVATING)
			.name("captivating")
		);
		register(Ability.build()
			.id(CAROL)
			.name("carol")
		);
		register(Ability.build()
			.id(CHARGED_ARC)
			.name("charged arc")
		);
		register(Ability.build()
			.id(CHILL)
			.name("chill")
			.rule(AbilityRule.build()
				.event(PRE_MOVE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(List.of(
					SetType.move(TypeId.ICE),
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2))
				)))
			)
		);
		// increases the damage of the user's ice type moves by 25%
		register(Ability.build()
			.id(CHILLING_PASSION)
			.name("chilling_passion")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(CIRCADIAN)
			.name("circadian")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.event(IsolatedId.START_OF_TURN_EVENT)
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
			.gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(CLINGY)
			.name("clingy")
			.gate(Gate.build()
				.event(CAN_REMOVE_USER_ITEM, USER)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(CLUTCH)
			.name("clutch")
			.rule(AbilityRule.build()
				.event(CLUTCH_EVENT, TARGET)
				.condition(new And(
					Conditions.targetIsAtFullHealth(),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new ClutchEffect())
			)
		);
		register(Ability.build()
			.id(COMBATIVE)
			.name("combative")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_STATS_LOWERED_BY_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(2)))
			)
		);
		register(Ability.build()
			.id(COMBUSTIBLE)
			.name("combustible")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, TARGET)
				.condition(new And(
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE)
				))
				.effect(new EffectSequence(List.of(
						Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.COMBUSTIBLE)
				)))
			)
		);
		register(Ability.build() // doubles exclusive
			.id(COMMUNICATION)
			.name("communication")
		);
		register(Ability.build()
			.id(COMPLIANT)
			.name("compliant")
		);
		register(Ability.build()
			.id(CONFIDENCE)
			.name("confidence")
			.gate(Gate.build()
				.event(CAN_FLINCH, USER)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build() // doubles exclusive
			.id(CONSPIRE)
			.name("conspire")
		);
		register(Ability.build()
			.id(COSMIC_PRESSURE)
			.name("cosmic_pressure")
		);
		register(Ability.build()
			.id(COURSING_VENOM) // when hit by a toxic type move, absorb it to boost your own toxic type damage by 1.5x
			.name("coursing venom")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, TARGET)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.COURSING_VENOM)
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
			.id(DEEP_FROSTBITE)
			.name("deep_frostbite")
		);
		register(Ability.build()
			.id(DEFENSIVE_PRIORITY)
			.name("defensive priority")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
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
		register(Ability.build()
			.id(DEPTHS_BORNE)
			.name("depths_borne")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(DESIGNATED_CHOMPERS)
			.name("designated chompers")
		);
		register(Ability.build()
			.id(DEVIOUS)
			.name("devious")
			.gate(Gate.build()
				.event(CAN_USE_DEFENSIVE_ABILITY, USER)
				.condition(new NoCondition())
			)
		);
		register(Ability.build()
			.id(DISENCHANT)
			.name("disenchant")
		);
		register(Ability.build()
			.id(DO_OR_DIE)
			.name("do or die")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.3)))
			)
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, USER)
				.effect(new DealDirectSelfDamage(new Multiply(
					new ValueConstant(Value.USER().HEALTH()),
					new Constant(1/16)
				)))
			)
		);
		register(Ability.build()
			.id(DOUBLE_STRIKE)
			.name("double_strike")
		);
		register(Ability.build()
            .id(DRAINAGE)
            .name("drainage")
			.rule(AbilityRule.build()
                .event(PRE_HEALTH_DRAIN_EVENT, USER) 
                .effect(Effects.multiplyValue(Value.USER().MOVE().HEALTH_DRAIN_MODIFIER(), new Constant(1.5)))
            )
        );
		register(Ability.build()
			.id(DRIVEN)
			.name("driven")
		);
		register(Ability.build()
			.id(DUSK)
			.name("dusk")
		);
		register(Ability.build()
			.id(EAGER)
			.name("eager")
		);
		register(Ability.build()
			.id(EFFULGENT)
			.name("effulgent")
		);
		register(Ability.build()
			.id(ELUSIVE)
			.name("elusive")
		);
		register(Ability.build()
			.id(ENCHANT)
			.name("enchant")
		);
		register(Ability.build()
			.id(ENCHANTED_COAT)
			.name("enchanted coat")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, TARGET)
				.condition(new And(
					Comparator.greaterThan()
						.left(new ValueConstant(Value.USER().MOVE().TYPE_MODIFIER()))
						.right(new Constant(1)),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.75)))
			)
		);
		register(Ability.build()
			.id(EXPERTISE)
			.name("expertise")
		);
		register(Ability.build()
			.id(FACADE)
			.name("facade")
			.gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(FANNING_FLAME)
			.name("fanning_flame")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)))
			)
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.targetChangingTarget()
					.rattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FESTIVE_SPIRIT)
			.name("festive spirit").gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(FINESSE)
			.name("finesse")
		);
		register(Ability.build()
			.id(FLUTTER)
			.name("flutter")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().POWDER_TAG(), true),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new NoEffect())
			)
		);
		register(Ability.build()
			.id(FOG_SUMMON)
			.name("fog summon")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.FOG, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(FORESIGHT)
			.name("foresight")
			.rule(AbilityRule.build()
				.event(PRE_TURN_EVENT, USER)
				.condition(new And(
					new Equals<>(Value.USER().CURRENT_HEALTH(), Value.USER().HEALTH()),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND)
				))
				.effect(Effects.addValue(Value.USER().PRIORITY(), new Constant(1)))
			)
		);
		register(Ability.build() // completed
			.id(FORGE)
			.name("forge")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(0.5))
				)
			)
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(ApplyStatChanges.userChangingTarget()
					.mattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FORTISSIMO)
			.name("fortissimo")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().SOUND_TAG(), true),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new NoEffect())
			)
		);
		register(Ability.build() // completed
			.id(FRAIL_ARMOUR)
			.name("frail armour")
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(ApplyStatChanges.targetChangingTarget()
					.mdefense(new Constant(-1))
					.speed(new Constant(2))
				)
			)
		);
		register(Ability.build()
			.id(FRENZY)
			.name("frenzy")
		);
		register(Ability.build() // completed
			.id(GLIDE)
			.name("glide")
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1))
				)
			)
			.rule(AbilityRule.build()
				.event(DEALT_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.targetChangingTarget()
					.speed(new Constant(1))
				)
			)
		);
		register(Ability.build() // completed
			.id(GLOOMY)
			.name("gloomy")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(Conditions.fogActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.3)))
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
		register(Ability.build() // doubles exclusive ability
			.id(GUARDIAN)
			.name("guardian")
		);
		register(Ability.build() // immunity to bomb moves
			.id(GUMMY)
			.name("gummy")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().EXPLOSIVE_TAG(), true),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new NoEffect())
			)
		);
		register(Ability.build() // technician from pokemon
			.id(GURU)
			.name("guru")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.condition(Comparator.lessThanEqualTo()
					.left(new INumberValueConstant(Value.USER().MOVE().BASE_POWER()))
					.right(new Constant(60)))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.6)))
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
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.HEATWAVE, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(HEAVY_FISTS)
			.name("heavy_fists")
		);
		register(Ability.build()
			.id(HERD_BEHAVIOR)
			.name("herd_behavior")
		);
		register(Ability.build()
			.id(HIGH_EXPLOSIVE)
			.name("high_explosive")
		);
		register(Ability.build()
			.id(HOTFOOT)
			.name("hotfoot")
		);
		register(Ability.build()
			.id(HOVER)
			.name("hover")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.EARTH),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
			)
		);
		register(Ability.build()
			.id(HYDRATE)
			.name("hydrate")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new NoEffect())
			)
		);
		register(Ability.build()
			.id(HYDRO_VORTEX)
			.name("hydro vortex")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
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
				.event(CAN_DEFENDING_STAT_USE_STAT_BOOSTS, TARGET)
				.event(CAN_DEFENDING_STAT_USE_STAT_DROPS, TARGET)
				.condition(new GateCondition(CAN_USE_DEFENSIVE_ABILITY))
			)
			.gate(Gate.build()
				.event(CAN_ATTACKING_STAT_USE_STAT_BOOSTS, USER)
				.event(CAN_ATTACKING_STAT_USE_STAT_DROPS, USER)
				.condition(new GateCondition(CAN_USE_OFFENSIVE_ABILITY))
			)
		);
		register(Ability.build()
			.id(ILLUMINATE)
			.name("illuminate")
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
		register(Ability.build()
			.id(INSULATED)
			.name("insulated")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.condition(new And(
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY),
					new Or(
						new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
						new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE)
					)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)))
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
		register(Ability.build()
			.id(LAND_BORNE)
			.name("land_borne")
		);
		register(Ability.build()
			.id(LAZY)
			.name("lazy")
		);
		register(Ability.build()
			.id(LIFE_FORCE)
			.name("life force").gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build()
			.id(LIGHTNING_ROD)
			.name("lightning rod")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1)
				))
			)
		);
		register(Ability.build()
			.id(LUCK_OF_THE_SEA)
			.name("luck_of_the_sea")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.effect(Effects.multiplyValue(Value.USER().MOVE().CRIT_MODIFIER(), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
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
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().RECOIL_TAG(), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Ability.build()
			.id(MALWARE)
			.name("malware")
			.rule(AbilityRule.build()
				.event(POST_DAMAGE_EVENT, TARGET) // if hit by a move
				.condition(new Equals<>(Value.USER().MOVE().CONTACT_TAG(), true))
				.effect(SetAbility.target(MALWARE))
			)
		);
		register(Ability.build()
			.id(MARKSMAN)
			.name("marksman")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.effect(Effects.multiplyValue(Value.USER().MOVE().CRIT_MODIFIER(), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
		);
		register(Ability.build()
			.id(MASK_SWAP)
			.name("mask_swap")
			.rule(AbilityRule.build()
				.event(PRE_MOVE_EVENT, USER)
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
			.name("mean_spirited")
		);
		register(Ability.build()
			.id(MEDIC)
			.name("medic")
		);
		register(Ability.build()
			.id(MESMERIZING)
			.name("mesmerizing")
		);
		register(Ability.build()
			.id(METALLIC)
			.name("metallic")
		);
		register(Ability.build()
			.id(METAMORPHOSIS)
			.name("metamorphosis")
		);
		register(Ability.build()
			.id(MIMIC)
			.name("mimic")
		);
		register(Ability.build()
			.id(MOTIVATIONAL)
			.name("motivational")
		);
		register(Ability.build()
			.id(MYCOTIC)
			.name("mycotic")
		);
		register(Ability.build()
			.id(MYSTERIOUS_CLOAK)
			.name("mysterious cloak")
		);
		register(Ability.build()
			.id(MYSTERIOUS_TOXINS)
			.name("mysterious_toxins")
		);
		register(Ability.build()
			.id(MYSTERY_TOXINS)
			.name("mystery toxins")
		);
		register(Ability.build()
			.id(MYSTIC_TONE)
			.name("mystic_tone")
		);
		register(Ability.build()
			.id(NEUTRALIZE)
			.name("neutralize")
		);
		register(Ability.build()
			.id(NIGHT_LIGHT)
			.name("night_light")
		);
		register(Ability.build()
			.id(NIGHTMARISH)
			.name("nightmarish")
		);
		register(Ability.build()
			.id(NOXIOUS_WEEDS)
			.name("noxious weeds")
			
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.PLANT),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(ApplyMechanic.toUser(MechanicId.NOXIOUS_WEEDS))
			)
		);
		register(Ability.build()
			.id(OBSIDIAN_HEART)
			.name("obsidian_heart")
			//.rule() // increase the cost of ancient type moves by 10%
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(ODD_HUSK)
			.name("odd_husk")
			.gate(Gate.build()
				.event(CAN_SECONDARY_EFFECTS_AFFECT_TARGET, TARGET)
				.condition(new Not(new GateCondition(CAN_USE_DEFENSIVE_ABILITY)))
			)
		);
		register(Ability.build()
			.id(ONE_OF_MANY)
			.name("one_of_many")
		);
		register(Ability.build()
			.id(OVERCHARGED)
			.name("overcharged")
		);
		register(Ability.build()
			.id(OVERCLOCK)
			.name("overclock")
		);
		register(Ability.build()
			.id(OVERSHADOW)
			.name("overshadow")
		);
		register(Ability.build()
			.id(OXIDIZE)
			.name("oxidize")
		);
		register(Ability.build()
			.id(PARTING_GIFT)
			.name("parting gift")
		);
		register(Ability.build()
			.id(PARTNERSHIP)
			.name("partnership")
		);
		register(Ability.build()
			.id(PERSISTENCE)
			.name("persistence")
		);
		register(Ability.build()
			.id(PETRIFYING)
			.name("petrifying")
		);
		register(Ability.build()
			.id(PINCER_TRAP)
			.name("pincer_trap")
		);
		register(Ability.build()
			.id(PITCH_BLACK)
			.name("pitch black")
		);
		register(Ability.build()
			.id(PLAYFUL)
			.name("playful")
		);
		register(Ability.build()
			.id(PLUVIAL)
			.name("pluvial")
		);
		register(Ability.build()
			.id(POWER_CLAW)
			.name("power claw")
		);
		register(Ability.build()
			.id(POWER_JAW)
			.name("power jaw")
		);
		register(Ability.build()
			.id(POWER_LEGS)
			.name("power legs")
		);
		register(Ability.build()
			.id(POWER_NAPPER)
			.name("power napper")
		);
		register(Ability.build()
			.id(PREMONITION)
			.name("premonition")
		);
		register(Ability.build()
			.id(PRISMATIC)
			.name("prismatic")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.PRISMATIC)
				)))
			)
		);
		register(Ability.build()
			.id(PROTECTIVE_SHELL)
			.name("protective shell")
			.gate(Gate.build()
				.event(CAN_MOVE_CRIT, TARGET)
				.condition(new Not(new GateCondition(CAN_USE_DEFENSIVE_ABILITY)))
			)
		);
		register(Ability.build()
			.id(PROWLER)
			.name("prowler")
		);
		register(Ability.build()
			.id(PUNCTURE)
			.name("puncture")
		);
		register(Ability.build()
			.id(PYRO_PRO)
			.name("pyro_pro")
		);
		register(Ability.build()
			.id(PYRO)
			.name("pyro")
		);
		register(Ability.build()
			.id(QUICK_RECOVERY)
			.name("quick recovery")
		);
		register(Ability.build()
			.id(RADIANCE)
			.name("radiance")
		);
		register(Ability.build()
			.id(RAGING_FIRE)
			.name("raging fire")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(new NoEffect())
			)
		);
		register(Ability.build()
			.id(RAIL_GUN)
			.name("rail_gun")
		);
		register(Ability.build()
			.id(RAIN_RUSH)
			.name("rain rush")
		);
		register(Ability.build()
			.id(RAIN_SUMMON)
			.name("rain summon")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.RAIN, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(RAVENOUS)
			.name("ravenous")
		);
		register(Ability.build()
			.id(RAZOR_SHARP)
			.name("razor sharp")
		);
		register(Ability.build()
			.id(REAPER)
			.name("reaper")
		);
		register(Ability.build()
			.id(RECHARGEABLE)
			.name("rechargeable")
			.interceptor(Interceptor.build()
				.event(MOVE_ABOUT_TO_AFFECT_TARGET, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(CAN_USE_DEFENSIVE_ABILITY)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1)
				))
			)
		);
		register(Ability.build()
			.id(RECURRENT)
			.name("recurrent")
		);
		register(Ability.build()
			.id(REFLECTIVE)
			.name("reflective")
		);
		register(Ability.build()
			.id(REGIFT)
			.name("regift")
		);
		register(Ability.build()
			.id(REGURGITATE)
			.name("regurgitate")
		);
		register(Ability.build()
			.id(REIGN)
			.name("reign")
		);
		register(Ability.build()
			.id(REMORSELESS)
			.name("remorseless")
		);
		register(Ability.build()
			.id(REPLICATE)
			.name("replicate")
		);
		register(Ability.build()
			.id(REPUGNANT)
			.name("repugnant")
		);
		register(Ability.build()
			.id(RESENTFUL)
			.name("resentful")
		);
		register(Ability.build()
			.id(RESILIENCE)
			.name("resilience")
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
			.name("royal_decree")
			.rule(AbilityRule.build()
                .event(PRE_MOVE_EVENT, USER) // before anything about the move is actually used
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
		);
		register(Ability.build()
			.id(SALVAGE)
			.name("salvage")
		);
		register(Ability.build()
			.id(SCORCHING_SKIN)
			.name("scorching skin")
		);
		register(Ability.build()
			.id(SEIZE)
			.name("seize")
		);
		register(Ability.build()
			.id(SENDOFF)
			.name("sendoff")
		);
		register(Ability.build()
			.id(SHAKEDOWN)
			.name("shakedown")
		);
		register(Ability.build()
			.id(SHARP_CLAWS)
			.name("sharp claws")
		);
		register(Ability.build()
			.id(SHARP_EDGES)
			.name("sharp edges")
		);
		register(Ability.build()
			.id(SHARP_FOCUS)
			.name("sharp_focus")
		);
		register(Ability.build()
			.id(SKY_BORNE)
			.name("sky_borne")
		);
		register(Ability.build()
			.id(SLICK_SHELL)
			.name("slick_shell")
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
			.id(SOB)
			.name("sob")
		);
		register(Ability.build()
			.id(SOUL_SIPHON)
			.name("soul siphon")
			.rule(AbilityRule.build()
				.event(USER_KNOCKED_OUT_TARGET_EVENT, USER)
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1))	
				)
			)
			.rule(AbilityRule.build()
				.event(TARGET_KNOCKED_OUT_USER_EVENT, TARGET)
				.effect(ApplyStatChanges.targetChangingTarget()
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
		);
		register(Ability.build()
			.id(SPONGE)
			.name("sponge")
		);
		register(Ability.build()
			.id(STAUNCH)
			.name("staunch")
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
			.name("sweet_aroma")
		);
		register(Ability.build()
			.id(SWEET_TOUCH)
			.name("sweet touch")
		);
		register(Ability.build()
			.id(TANK)
			.name("tank")
		);
		register(Ability.build()
			.id(TEMPER)
			.name("temper")
		);
		register(Ability.build()
			.id(TERRIFYING)
			.name("terrifying")
		);
		register(Ability.build()
			.id(TERRITORIAL)
			.name("territorial")
		);
		register(Ability.build()
			.id(THIRD_DEGREE_BURN)
			.name("third_degree_burn")
		);
		register(Ability.build()
			.id(THRIVING_PACE)
			.name("thriving pace")
		);
		register(Ability.build()
			.id(THUNDER_SUMMON)
			.name("thunder summon")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.THUNDERSTORM, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(TONE_DEAF)
			.name("tone deaf")
		);
		register(Ability.build()
			.id(TOTAL_ECLIPSE)
			.name("total_eclipse")
		);
		register(Ability.build()
			.id(TOXIC_FILTER)
			.name("toxic filter")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.TOXIC_FILTER)
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
			.name("toxic_touch")
		);
		register(Ability.build()
			.id(TRADER)
			.name("trader")
		);
		register(Ability.build()
			.id(TRASH_ARMOUR)
			.name("trash armour")
		);
		register(Ability.build()
			.id(TRIUMPH)
			.name("triumph")
		);
		register(Ability.build()
			.id(TUMULTUOUS)
			.name("tumultuous")
		);
		register(Ability.build()
			.id(TURBULENT)
			.name("turbulent")
		);
		register(Ability.build()
			.id(TWO_FACE)
			.name("two_face").gate(Gate.build() // prevents the ability being removed
				.event(CAN_REMOVE_USER_ABILITY, USER)
				.event(CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new FalseCondition())
			)
		);
		register(Ability.build() // completed
			.id(UNGRACIOUS_HOST)
			.name("ungracious host")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.effect(Effects.multiplyValue(Value.USER().MOVE().HEALTH_DRAIN_MODIFIER(), new Constant(-1)))
			)
		);
		register(Ability.build()
			.id(UPPER_HAND)
			.name("upper_hand")
		);
		register(Ability.build()
			.id(VENGEANCE)
			.name("vengeance")
		);
		register(Ability.build()
			.id(VENOMOUS)
			.name("venomous")
		);
		// increases the users damage when the user is poisoned
		register(Ability.build() // build
			.id(VICIOUS)
			.name("vicious")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().STATUS_ID(), StatusId.BURN))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(VIGILANT)
			.name("vigilant")
		);
		// increases the users melee attack by 50% if they are inflicted by a status ailment and removes the damage drop from burn
		register(Ability.build() // completed
			.id(VIGOROUS)
			.name("vigorous")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new And(
					Conditions.userHasStatusAilment(),
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
			)
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)
				.condition(new Equals<>(Value.USER().STATUS_ID(), StatusId.BURN))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(2)))
			)
		);
		// reduces the energy cost of the user's sound based moves by 25%
		register(Ability.build()
			.id(VIRTUOSO)
			.name("virtuoso")
		);
		// the user CAN be forced off the field
		register(Ability.build()
			.id(VISCID)
			.name("viscid")
		);
		// increases the accuracy of the user's moves by 30%
		register(Ability.build() // completed
			.id(VIVID_SIGHT)
			.name("vivid sight")
			.rule(AbilityRule.build()
				.event(PRE_MOVE_EVENT, USER)
				.effect(Effects.multiplyValue(Value.USER().MOVE().ACCURACY_MODIFIER(), new Constant(1.3)))
			)
		);
		register(Ability.build()
			.id(VOLCANIC)
			.name("volcanic")
		);
		register(Ability.build()
			.id(WATCHER)
			.name("watcher")
		);
		register(Ability.build() // completed
			.id(WEBBING)
			.name("webbing")
			.rule(AbilityRule.build()
				.event(POST_DAMAGE_EVENT, TARGET)
				.condition(new Equals<>(Value.USER().MOVE().CONTACT_TAG(), true))
				.effect(ApplyStatChanges.targetChangingUser()
					.speed(new Constant(1))
				)
			)
		);
		// reduces the damage of brawler and mind type moves by 50%
		register(Ability.build() // completed
			.id(WHOLESOME)
			.name("wholesome")
			.rule(AbilityRule.build()
				.event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)
				.condition(new Or(
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BRAWLER),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)))
			)
		);
		register(Ability.build()
			.id(WILDFIRE)
			.name("wildfire")
		);
		register(Ability.build() // completed
			.id(WIND_SUMMON)
			.name("wind summon")
			.rule(AbilityRule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(5)))
			)
		);
		register(Ability.build() // completed
			.id(WISE)
			.name("wise")
			.rule(AbilityRule.build()
				.event(PRE_DAMAGE_EVENT, USER)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.USER().MOVE().TYPE_MODIFIER()))
					.right(new Constant(1))
				)
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(WOODSMAN)
			.name("woodman")
		);
    }
}
