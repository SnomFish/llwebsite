package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Conditions;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.GreaterThan;
import github.snomfish.functionality.condition.LessThan;
import github.snomfish.functionality.condition.ListContains;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.DispatchEventEffect;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.effect.effects.SetAbility;
import github.snomfish.functionality.effect.effects.SetAbilityEffect;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.domain.type.TypeId.*;
import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.event.EventSideId.*;

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
		register(AbilityBuilder.newAbility() // completed
			.id(NO_ABILITY)
			.name("no ability")
			.build()
		);
        register(AbilityBuilder.newAbility() // completed
			.id(ABILITY_THIEF)
			.name("ability thief")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(POST_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_CONTACT_TAG, true))
				.effect(new EffectSequence(List.of(
					SetAbilityEffect.userActual(Value.TARGET_ABILITY_ID),
					SetAbilityEffect.user(Value.TARGET_ABILITY_ID),
					SetAbilityEffect.targetActual(AbilityId.NO_ABILITY),
					SetAbilityEffect.target(AbilityId.NO_ABILITY),
					new DispatchEventEffect(USER_SWITCHED_IN_EVENT)
				)))
				.build()
			)
			.oncePerBattle()
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(ACE)
			.name("ace")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)) // right before a moves damage is calculated
				.condition(new ListContains(Value.USER_TYPES, Value.MOVE_TYPE))
				.effect(new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(2.0)))
				.build()
            )
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(ADAPTABLE)
			.name("adaptable")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(WEATHER_SET_EVENT, USER))
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.userPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.userPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.userPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.userPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.userPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.userPrimary(TypeId.ELECTRIC))
				)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(WEATHER_SET_EVENT, TARGET))
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.targetPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.targetPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.targetPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.targetPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.targetPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.targetPrimary(TypeId.ELECTRIC))
				)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.userPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.userPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.userPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.userPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.userPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.userPrimary(TypeId.ELECTRIC))
				)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(TARGET_SWITCHED_IN_EVENT, TARGET))
				.effect(new EffectSequence(List.of(	
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.targetPrimary(TypeId.SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(),			SetType.targetPrimary(TypeId.SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.targetPrimary(TypeId.FIRE)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.targetPrimary(TypeId.AIR)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.targetPrimary(TypeId.WATER)),
					new ConditionalEffect(Conditions.thunderstormActive(),	SetType.targetPrimary(TypeId.ELECTRIC))
				)))
				.build()
			)
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(ADORABLE)
			.name("adorable")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
				)
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(AGGRESSIVE)
			.name("aggressive")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ALL_SEEING)
			.name("all_seeing")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(AMBUSH)
			.name("ambush")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(ANALYZE)
			.name("analyze")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(new ConditionalEffect(
					new Or(
						new GreaterThan(Value.TARGET_BATTLE_MDEFENSE, Value.TARGET_BATTLE_RDEFENSE),
						new And(
							new Equals(Value.TARGET_BATTLE_MDEFENSE, Value.TARGET_BATTLE_RDEFENSE),
							new RandomCondition(new Constant(0.5))
						)
					), 
					ApplyStatChanges.userChangingUser() // true effect
						.rattack(new Constant(1)), 
					ApplyStatChanges.userChangingUser() // false effect
						.mattack(new Constant(1))
				))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(ANOMALY)
			.name("anomaly")
			.rule(AbilityRuleBuilder.newRule() // when the loomian is the user
				.event(new Event(USER_SWITCHED_IN_EVENT, USER)) 
				.effect(new EffectSequence(List.of(
					new MultiplyValueEffect(Value.USER_MATTACK_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_MDEFENSE_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_RATTACK_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_RDEFENSE_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_SPEED_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_ACCURACY_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.USER_EVASION_STAGE_MODIFIER, new Constant(-1))
				)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule() // when the loomian is the target
				.event(new Event(TARGET_SWITCHED_IN_EVENT, TARGET))
				.effect(new EffectSequence(List.of(
					new MultiplyValueEffect(Value.TARGET_MATTACK_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_MDEFENSE_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_RATTACK_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_RDEFENSE_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_SPEED_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_ACCURACY_STAGE_MODIFIER, new Constant(-1)),
					new MultiplyValueEffect(Value.TARGET_EVASION_STAGE_MODIFIER, new Constant(-1))
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(APPETITE)
			.name("appetite")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(APPLIED_FRUSTRATION)
			.name("applied_frustration")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(TARGET_LOWERED_USER_STATS_EVENT, USER))
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(2))
				)
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_LOWERED_TARGET_STATS_EVENT, TARGET))
				.effect(ApplyStatChanges.targetChangingTarget()
					.rattack(new Constant(2))
				)
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(APPREHENSION)
			.name("apprehension")
			.build()
		);
		 // this has to use type modifier, you cannot alter the typechart for this as if the target has two types, that fire resistance will be applied twice
		register(AbilityBuilder.newAbility() // completed
            .id(AQUA_BODY)
            .name("aqua body")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)) // before the targets move damage is calculated 
                .effect(new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.FIRE),
					new MultiplyValueEffect(Value.MOVE_TYPE_MODIFIER, new Constant(0.5))
				))
				.build()
            )
			.build()
        );
        register(AbilityBuilder.newAbility() // completed
            .id(ASSERTIVE)
            .name("assertive")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(BEFORE_MOVE_EVENT, USER)) // before anything about the move is actually used
                .effect(new AlterTypeChart(BRAWLER, SPIRIT, 1))
				.build()
            )
			.build()
        );
        register(AbilityBuilder.newAbility() // completed
            .id(AWAKENING)
            .name("awakening")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)) // right before a moves damage is calculated
                .effect(new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(1.5)))
				.build()
            )
			.build()
        );
        register(AbilityBuilder.newAbility() // completed
            .id(BANEFUL)
            .name("baneful")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Or(
					new Equals(Value.USER_STATUS_ID, StatusId.POISON),
					new Equals(Value.USER_STATUS_ID, StatusId.BAD_POISON)
				))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2)))
				.build()
            )
			.build()
        );
		register(AbilityBuilder.newAbility()
			.id(BATTLE_ARMOUR)
			.name("battle_armour")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)) 
				.condition(new Equals(Value.MOVE_TYPE, TypeId.METAL))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25)))
				.build()
			)
				// TODO: battle armour protects the user from the secondary effects of moves
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BERSERK)
			.name("berserk")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BITTER_TOUCH)
			.name("bitter touch")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BLISTERING_HEAT)
			.name("blistering heat")
			.build()
		);
		register(AbilityBuilder.newAbility() // Increases the damage of the user's health draining moves
			.id(BLOODSUCKER)
			.name("bloodsucker")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_HEALTH_DRAIN_TAG, true))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BOAST)
			.name("boast")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(new ConditionalEffect(
					new GreaterThan(Value.USER_BATTLE_MATTACK, Value.TARGET_BATTLE_MATTACK),
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1)),
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(-1))
				))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(TARGET_SWITCHED_IN_EVENT, TARGET))
				.effect(new ConditionalEffect(
					new GreaterThan(Value.TARGET_BATTLE_MATTACK, Value.USER_BATTLE_MATTACK),
					ApplyStatChanges.targetChangingTarget()
						.mattack(new Constant(1)),
					ApplyStatChanges.targetChangingTarget()
						.mattack(new Constant(-1))
				))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BONEHEADED)
			.name("boneheaded")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BRUTAL_WRATH)
			.name("brutal wrath")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BRUTE_FORCE)
			.name("brute force")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BUG_FEVER)
			.name("bug_fever")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BULLY)
			.name("bully")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BURGLAR)
			.name("burglar")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BURGLAR)
			.name("burgler")
			.build()
		);
		// increases the damage of hte users fire type moves by 25%
		register(AbilityBuilder.newAbility()
			.id(BURNING_RAGE)
			.name("burning_rage")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_TYPE, FIRE))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(BURSTING_SEAMS)
			.name("bursting_seams")
			.build()
		);
		register(AbilityBuilder.newAbility() // doubles exclusive ability
			.id(CADDIE)
			.name("caddie")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CAPTIVATING)
			.name("captivating")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CAROL)
			.name("carol")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CHARGED_ARC)
			.name("charged arc")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CHILL)
			.name("chill")
			.build()
		);
		// increases the damage of the user's ice type moves by 25%
		register(AbilityBuilder.newAbility()
			.id(CHILLING_PASSION)
			.name("chilling_passion")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_TYPE, ICE))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CIRCADIAN)
			.name("circadian")
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CLINGY)
			.name("clingy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CLUTCH)
			.name("clutch")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(COMBATIVE)
			.name("combative")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(TARGET_LOWERED_USER_STATS_EVENT, USER))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(2)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_LOWERED_TARGET_STATS_EVENT, TARGET))
				.effect(ApplyStatChanges.targetChangingTarget()
					.mattack(new Constant(2)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(COMBUSTIBLE)
			.name("combustible")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.FIRE))
				.effect(new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.COMBUSTIBLE)
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // doubles exclusive
			.id(COMMUNICATION)
			.name("communication")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(COMPLIANT)
			.name("compliant")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(CONFIDENCE)
			.name("confidence")
			.build()
		);
		register(AbilityBuilder.newAbility() // doubles exclusive
			.id(CONSPIRE)
			.name("conspire")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(COSMIC_PRESSURE)
			.name("cosmic_pressure")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(COURSING_VENOM) // when hit by a toxic type move, absorb it to boost your own toxic type damage by 1.5x
			.name("coursing venom")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.COURSING_VENOM)
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DAUNTLESS)
			.name("dauntless")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DAWN)
			.name("dawn")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DEEP_FROSTBITE)
			.name("deep_frostbite")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DEFENSIVE_PRIORITY)
			.name("defensive priority")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(ApplyStatChanges.userChangingUser()
					.mdefense(new Constant(1))
					.rdefense(new Constant(1))
					.speed(new Constant(-2)))
				.build()	
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(TARGET_SWITCHED_IN_EVENT, TARGET))
				.effect(ApplyStatChanges.targetChangingTarget()
					.mdefense(new Constant(1))
					.rdefense(new Constant(1))
					.speed(new Constant(-2)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DEMANDING)
			.name("demanding")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DEPTHS_BORNE)
			.name("depths_borne")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.WATER))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DESIGNATED_CHOMPERS)
			.name("designated chompers")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DEVIOUS)
			.name("devious")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DISENCHANT)
			.name("disenchant")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DO_OR_DIE)
			.name("do or die")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DOUBLE_STRIKE)
			.name("double_strike")
			.build()
		);
		register(AbilityBuilder.newAbility()
            .id(DRAINAGE)
            .name("drainage")
			.rule(AbilityRuleBuilder.newRule()
                .event(new Event(PRE_HEALTH_DRAIN_EVENT, USER)) 
                .effect(new MultiplyValueEffect(Value.MOVE_HEALTH_DRAIN_MODIFIER, new Constant(1.5)))
				.build()
            )
			.build()
        );
		register(AbilityBuilder.newAbility()
			.id(DRIVEN)
			.name("driven")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(DUSK)
			.name("dusk")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(EAGER)
			.name("eager")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(EFFULGENT)
			.name("effulgent")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ELUSIVE)
			.name("elusive")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ENCHANT)
			.name("enchant")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ENCHANTED_COAT)
			.name("enchanted coat")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new GreaterThan(Value.MOVE_TYPE_MODIFIER, new Constant(1)))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.75)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(EXPERTISE)
			.name("expertise")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FACADE)
			.name("facade")
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FANNING_FLAME)
			.name("fanning_flame")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, AIR))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)))
				.build()
			)// TODO: add +1 ranged attack when hit
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FESTIVE_SPIRIT)
			.name("festive spirit")
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FINESSE)
			.name("finesse")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FLUTTER)
			.name("flutter")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FOG_SUMMON)
			.name("fog summon")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(SetWeather.userSetting(WeatherId.FOG, new Constant(5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FORESIGHT)
			.name("foresight")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(FORGE)
			.name("forge")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.FIRE))
				.effect(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5))
				)
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(DEALT_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.FIRE))
				.effect(ApplyStatChanges.userChangingTarget()
					.mattack(new Constant(1))
				)
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FORTISSIMO)
			.name("fortissimo")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(FRAIL_ARMOUR)
			.name("frail armour")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(DEALT_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_CATEGORY, CategoryId.MELEE))
				.effect(ApplyStatChanges.targetChangingTarget()
					.mdefense(new Constant(-1))
					.speed(new Constant(2))
				)
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(FRENZY)
			.name("frenzy")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(GLIDE)
			.name("glide")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(DEALT_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_TYPE, AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1))
				)
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(DEALT_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, AIR))
				.effect(ApplyStatChanges.targetChangingTarget()
					.speed(new Constant(1))
				)
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(GLOOMY)
			.name("gloomy")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(Conditions.fogActive())
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.3)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(GLUTTON)
			.name("glutton")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(GORGE)
			.name("gorge")
			.build()
		);
		register(AbilityBuilder.newAbility() // doubles exclusive ability
			.id(GUARDIAN)
			.name("guardian")
			.build()
		);
		register(AbilityBuilder.newAbility() // immunity to bomb moves
			.id(GUMMY)
			.name("gummy")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_EXPLOSIVE_TAG, true))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // technician from pokemon
			.id(GURU)
			.name("guru")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_EXPLOSIVE_TAG, true))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HAG)
			.name("hag")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HANDY)
			.name("handy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HARD_CANDY)
			.name("hard candy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HARMONIZE)
			.name("harmonize")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HASTY)
			.name("hasty")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HEAT_SUMMON)
			.name("heat summon")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(SetWeather.userSetting(WeatherId.HEATWAVE, new Constant(5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HEAVY_FISTS)
			.name("heavy_fists")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HERD_BEHAVIOR)
			.name("herd_behavior")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HIGH_EXPLOSIVE)
			.name("high_explosive")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HOTFOOT)
			.name("hotfoot")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HOVER)
			.name("hover")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HYDRATE)
			.name("hydrate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(HYDRO_VORTEX)
			.name("hydro vortex")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(IDIOSYNCRATIC)
			.name("idiosyncratic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(IGNORANT)
			.name("ignorant")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ILLUMINATE)
			.name("illuminate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(IMMUNIZED)
			.name("immunized")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(IMPOSE)
			.name("impose")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(INCANDESCENT)
			.name("incandescent")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(INFERNO)
			.name("inferno")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(INSULATED)
			.name("insulated")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(INTENSIFY)
			.name("intensify")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(INTERN)
			.name("intern")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LAND_BORNE)
			.name("land_borne")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LAZY)
			.name("lazy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LIFE_FORCE)
			.name("life force")
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LIGHTNING_ROD)
			.name("lightning rod")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LUCK_OF_THE_SEA)
			.name("luck_of_the_sea")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.effect(new MultiplyValueEffect(Value.MOVE_CRIT_MODIFIER, new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
				.build()
			)
			// increases crit chance
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(LUCKY)
			.name("lucky")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MADCAP)
			.name("madcap")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.MOVE_RECOIL_TAG, true))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MALWARE)
			.name("malware")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(POST_DAMAGE_EVENT, TARGET)) // if hit by a move
				.condition(new Equals(Value.MOVE_CONTACT_TAG, true))
				.effect(SetAbility.target(MALWARE))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MARKSMAN)
			.name("marksman")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.effect(new MultiplyValueEffect(Value.MOVE_CRIT_MODIFIER, new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MASK_SWAP)
			.name("mask_swap")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(BEFORE_MOVE_EVENT, USER))
				.effect(new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, Value.USER_PRIMARY_TYPE), // if the user's move doesnt match the type of move they are using, gain it as a secondary type
					SetType.userSecondary(NULL),
					SetType.userSecondary(Value.MOVE_TYPE)
				))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MASTER)
			.name("master")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MEAN_SPIRITED)
			.name("mean_spirited")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MEDIC)
			.name("medic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MESMERIZING)
			.name("mesmerizing")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(METALLIC)
			.name("metallic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(METAMORPHOSIS)
			.name("metamorphosis")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MIMIC)
			.name("mimic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MOTIVATIONAL)
			.name("motivational")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MYCOTIC)
			.name("mycotic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MYSTERIOUS_CLOAK)
			.name("mysterious cloak")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MYSTERIOUS_TOXINS)
			.name("mysterious_toxins")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MYSTERY_TOXINS)
			.name("mystery toxins")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(MYSTIC_TONE)
			.name("mystic_tone")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(NEUTRALIZE)
			.name("neutralize")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(NIGHT_LIGHT)
			.name("night_light")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(NIGHTMARISH)
			.name("nightmarish")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(NOXIOUS_WEEDS)
			.name("noxious weeds")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.PLANT))
				.effect(new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.NOXIOUS_WEEDS)
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(OBSIDIAN_HEART)
			.name("obsidian_heart")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ODD_HUSK)
			.name("odd_husk")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ONE_OF_MANY)
			.name("one_of_many")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(OVERCHARGED)
			.name("overcharged")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(OVERCLOCK)
			.name("overclock")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(OVERSHADOW)
			.name("overshadow")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(OXIDIZE)
			.name("oxidize")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PARTING_GIFT)
			.name("parting gift")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PARTNERSHIP)
			.name("partnership")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PERSISTENCE)
			.name("persistence")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PETRIFYING)
			.name("petrifying")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PINCER_TRAP)
			.name("pincer_trap")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PITCH_BLACK)
			.name("pitch black")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PLAYFUL)
			.name("playful")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PLUVIAL)
			.name("pluvial")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(POWER_CLAW)
			.name("power claw")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(POWER_JAW)
			.name("power jaw")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(POWER_LEGS)
			.name("power legs")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(POWER_NAPPER)
			.name("power napper")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PREMONITION)
			.name("premonition")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PRISMATIC)
			.name("prismatic")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.LIGHT))
				.effect(new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.PRISMATIC)
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PROTECTIVE_SHELL)
			.name("protective shell")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PROWLER)
			.name("prowler")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PUNCTURE)
			.name("puncture")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PYRO_PRO)
			.name("pyro_pro")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(PYRO)
			.name("pyro")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(QUICK_RECOVERY)
			.name("quick recovery")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RADIANCE)
			.name("radiance")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAGING_FIRE)
			.name("raging fire")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAIL_GUN)
			.name("rail_gun")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAIN_RUSH)
			.name("rain rush")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAIN_SUMMON)
			.name("rain summon")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(SetWeather.userSetting(WeatherId.RAIN, new Constant(5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAVENOUS)
			.name("ravenous")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RAZOR_SHARP)
			.name("razor sharp")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REAPER)
			.name("reaper")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RECHARGEABLE)
			.name("rechargeable")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RECURRENT)
			.name("recurrent")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REFLECTIVE)
			.name("reflective")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REGIFT)
			.name("regift")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REGURGITATE)
			.name("regurgitate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REIGN)
			.name("reign")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REMORSELESS)
			.name("remorseless")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REPLICATE)
			.name("replicate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REPUGNANT)
			.name("repugnant")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RESENTFUL)
			.name("resentful")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RESILIENCE)
			.name("resilience")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REV_UP)
			.name("rev up")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(REVERBERATE)
			.name("reverberate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(ROYAL_DECREE)
			.name("royal_decree")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(RUSH_HOUR)
			.name("rush hour")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SAFETY_POT)
			.name("safety pot")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SALVAGE)
			.name("salvage")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SCORCHING_SKIN)
			.name("scorching skin")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SEIZE)
			.name("seize")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SENDOFF)
			.name("sendoff")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SHAKEDOWN)
			.name("shakedown")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SHARP_CLAWS)
			.name("sharp claws")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SHARP_EDGES)
			.name("sharp edges")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SHARP_FOCUS)
			.name("sharp_focus")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SKY_BORNE)
			.name("sky_borne")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SLICK_SHELL)
			.name("slick_shell")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SLIMY)
			.name("slimy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SLY)
			.name("sly")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SOB)
			.name("sob")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SOUL_SIPHON)
			.name("soul siphon")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SPECIALIZATION)
			.name("specialization")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SPINE_BREAK)
			.name("spine break")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SPONGE)
			.name("sponge")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(STAUNCH)
			.name("staunch")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SUGAR_RUSH)
			.name("sugar rush")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SURROGATE)
			.name("surrogate")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SWAMPY)
			.name("swampy")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SWEET_AROMA)
			.name("sweet_aroma")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(SWEET_TOUCH)
			.name("sweet touch")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TANK)
			.name("tank")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TEMPER)
			.name("temper")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TERRIFYING)
			.name("terrifying")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TERRITORIAL)
			.name("territorial")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(THIRD_DEGREE_BURN)
			.name("third_degree_burn")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(THRIVING_PACE)
			.name("thriving pace")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(THUNDER_SUMMON)
			.name("thunder summon")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(SetWeather.userSetting(WeatherId.THUNDERSTORM, new Constant(5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TONE_DEAF)
			.name("tone deaf")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TOTAL_ECLIPSE)
			.name("total_eclipse")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TOXIC_FILTER)
			.name("toxic filter")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_TYPE, TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
					ApplyMechanic.toUser(MechanicId.TOXIC_FILTER)
				)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TOXIC_SAC)
			.name("toxic sac")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TOXIC_SPINES)
			.name("toxic spines")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TOXIC_TOUCH)
			.name("toxic_touch")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TRADER)
			.name("trader")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TRASH_ARMOUR)
			.name("trash armour")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TRIUMPH)
			.name("triumph")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TUMULTUOUS)
			.name("tumultuous")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TURBULENT)
			.name("turbulent")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(TWO_FACE)
			.name("two_face")
			.isLocked()
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(UNGRACIOUS_HOST)
			.name("ungracious host")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.effect(new MultiplyValueEffect(Value.MOVE_HEALTH_DRAIN_MODIFIER, new Constant(-1)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(UPPER_HAND)
			.name("upper_hand")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(VENGEANCE)
			.name("vengeance")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(VENOMOUS)
			.name("venomous")
			.build()
		);
		// increases the users damage when the user is poisoned
		register(AbilityBuilder.newAbility() // build
			.id(VICIOUS)
			.name("vicious")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.USER_STATUS_ID, StatusId.BURN))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(VIGILANT)
			.name("vigilant")
			.build()
		);
		// increases the users melee attack by 50% if they are inflicted by a status ailment and removes the damage drop from burn
		register(AbilityBuilder.newAbility() // completed
			.id(VIGOROUS)
			.name("vigorous")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new And(
					Conditions.userHasStatusAilment(),
					new Equals(Value.MOVE_CATEGORY, CategoryId.MELEE)
				))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)))
				.build()
			)
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER))
				.condition(new Equals(Value.USER_STATUS_ID, StatusId.BURN))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(2)))
				.build()
			)
			.build()
		);
		// reduces the energy cost of the user's sound based moves by 25%
		register(AbilityBuilder.newAbility()
			.id(VIRTUOSO)
			.name("virtuoso")
			.build()
		);
		// the user cannot be forced off the field
		register(AbilityBuilder.newAbility()
			.id(VISCID)
			.name("viscid")
			.build()
		);
		// increases the accuracy of the user's moves by 30%
		register(AbilityBuilder.newAbility() // completed
			.id(VIVID_SIGHT)
			.name("vivid sight")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(BEFORE_MOVE_EVENT, USER))
				.effect(new MultiplyValueEffect(Value.MOVE_ACCURACY_MODIFIER, new Constant(1.3)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(VOLCANIC)
			.name("volcanic")
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(WATCHER)
			.name("watcher")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(WEBBING)
			.name("webbing")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(POST_DAMAGE_EVENT, TARGET))
				.condition(new Equals(Value.MOVE_CONTACT_TAG, true))
				.effect(ApplyStatChanges.targetChangingUser()
					.speed(new Constant(1))
				)
				.build()
			)
			.build()
		);
		// reduces the damage of brawler and mind type moves by 50%
		register(AbilityBuilder.newAbility() // completed
			.id(WHOLESOME)
			.name("wholesome")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET))
				.condition(new Or(
					new Equals(Value.MOVE_TYPE, BRAWLER),
					new Equals(Value.MOVE_TYPE, MIND)
				))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(WILDFIRE)
			.name("wildfire")
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(WIND_SUMMON)
			.name("wind summon")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(USER_SWITCHED_IN_EVENT, USER))
				.effect(SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(5)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility() // completed
			.id(WISE)
			.name("wise")
			.rule(AbilityRuleBuilder.newRule()
				.event(new Event(PRE_DAMAGE_EVENT, USER))
				.condition(new LessThan(Value.MOVE_TYPE_MODIFIER, new Constant(1)))
				.effect(new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(2)))
				.build()
			)
			.build()
		);
		register(AbilityBuilder.newAbility()
			.id(WOODSMAN)
			.name("woodman")
			.build()
		);
    }
}
