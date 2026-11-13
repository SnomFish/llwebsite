package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.domain.move.MoveTagId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Conditions;
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
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.ClutchEffect;
import github.snomfish.functionality.effect.effects.DealDirectSelfDamage;
import github.snomfish.functionality.effect.effects.DispatchEventEffect;
import github.snomfish.functionality.effect.effects.SetAbility;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Interceptor;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.ids.GateId;
import github.snomfish.functionality.event.ids.GenericAntiId;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.event.ids.IsolatedId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumberValueConstant;
import github.snomfish.functionality.number.Multiply;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.ability.AbilityId.*;
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
				.event(GenericId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true),
					new GateCondition(GateId.CAN_REMOVE_TARGET_ABILITY, USER)
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
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE) // right before a moves damage is calculated
				.condition(new ListContains<>(Value.USER().TYPES(), Value.USER().MOVE().TYPE()))
				.effect(new SetValueEffect<>(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.STAB), new Constant(2.0)))
            )
		);
		register(Ability.build() // completed
			.id(ADAPTABLE)
			.name("adaptable")
			.rule(Rule.build()
				.event(IsolatedId.LOOMIAN_SET_WEATHER)
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
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
			)
		);
		register(Ability.build() // completed
			.id(ADORABLE)
			.name("adorable")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
				)
			)
		);
		register(Ability.build()
			.id(AGGRESSIVE)
			.name("aggressive")
			.rule(Rule.build()
				.event(GenericId.SELF_KNOCKED_OUT_OPP)
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
			.rule(Rule.build() // when the loomian is the user
				.event(GenericId.SELF_PRE_STATS_CHANGED) 
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
			.rule(Rule.build()
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
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE) // before the targets move damage is calculated
				.condition(new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER))
                .effect(new ConditionalEffect(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE),
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.TYPE), new Constant(0.5))
				))
            )
        );
        register(Ability.build() // completed
            .id(ARCTIC)
            .name("arctic")
        );
        register(Ability.build() // completed
            .id(ASSERTIVE)
            .name("assertive")
			.rule(Rule.build()
                .event(GenericId.SELF_PRE_MOVE) // before anything about the move is actually used
                .effect(new AlterTypeChart(TypeId.BRAWLER, TypeId.SPIRIT, 1))
            )
        );
        register(Ability.build() // completed
            .id(AWAKENING)
            .name("awakening")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE) // right before a moves damage is calculated
                .effect(new SetValueEffect<>(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.STAB), new Constant(1.5)))
            )
        );
        register(Ability.build() // completed
            .id(BANEFUL)
            .name("baneful")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.USER().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.USER().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
        );
		register(Ability.build()
			.id(BATTLE_ARMOUR)
			.name("armour")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE) 
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
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
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.HEALTH_DRAIN), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(BOAST)
			.name("boast")
			.rule(Rule.build()
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
				.event(GateId.CAN_TAKE_RECOIL, USER)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(BRAVE_CUB)
			.name("Brave Cub")
		);
		register(Ability.build()
			.id(BRUTAL_WRATH)
			.name("brutal wrath")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
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
				.event(GateId.CAN_USE_SECONDARY_EFFECTS, USER)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(BUG_FEVER)
			.name("bug_fever")
			.rule(Rule.build()
				.event(GenericId.SELF_KNOCKED_OUT_OPP)
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
				.event(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.TARGET().HEIGHT()))
					.right(new ValueConstant(Value.USER().HEIGHT()))
				)
				// if the user is shorter than the target, bully does not activate so the target can use their defensive ability
			)
			.gate(Gate.build()
				.event(GateId.CAN_DEFENDING_STAT_USE_STAT_BOOSTS, USER)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.USER().HEIGHT()))
					.right(new ValueConstant(Value.TARGET().HEIGHT()))
				)
			)
		);
		register(Ability.build()
			.id(BURGLAR)
			.name("burglar")
			.rule(Rule.build()
				.event(GenericId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true),
					new GateCondition(GateId.CAN_REMOVE_TARGET_ITEM, USER)
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
		// increases the damage of the users fire type moves by 25%
		register(Ability.build()
			.id(BURNING_RAGE)
			.name("burning_rage")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
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
				.event(GenericId.SELF_PRE_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(List.of(
					SetType.move(TypeId.ICE),
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2))
				)))
			)
		);
		// increases the damage of the user's ice type moves by 25%
		register(Ability.build()
			.id(CHILLING_PASSION)
			.name("chilling_passion")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(CIRCADIAN)
			.name("circadian")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.event(IsolatedId.START_OF_TURN)
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
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(CLINGY)
			.name("clingy")
			.gate(Gate.build()
				.event(GateId.CAN_REMOVE_USER_ITEM, USER)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(CLUTCH)
			.name("clutch")
			.rule(Rule.build()
				.event(GenericId.SELF_CLUTCHES)
				.condition(new And(
					Conditions.userIsAtFullHealth(),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, TARGET)
				))
				.effect(new ClutchEffect())
			)
		);
		register(Ability.build()
			.id(COMBATIVE)
			.name("combative")
			.rule(Rule.build()
				.event(GenericId.SELF_STATS_LOWERED_BY_OPP)
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(2)))
			)
		);
		register(Ability.build()
			.id(COMBUSTIBLE)
			.name("combustible")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new And(
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER),
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE)
				))
				.effect(
					ApplyMechanic.toUser(MechanicId.COMBUSTIBLE)
				)
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
			.id(CONDITIONED)
			.name("Conditioned")
		);
		register(Ability.build()
			.id(CONFIDENCE)
			.name("confidence")
			.gate(Gate.build()
				.event(GateId.CAN_MOVE_FLINCH, USER)
				.condition(new False())
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
			.rule(Rule.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new And(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.TOXIC),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(new EffectSequence(List.of(
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
		register(Ability.build() // doubles exclusive
			.id(DEAD_OF_WINTER)
			.name("Dead of Winter")
		);
		register(Ability.build()
			.id(DEEP_FROSTBITE)
			.name("deep_frostbite")
		);
		register(Ability.build()
			.id(DEFENSIVE_PRIORITY)
			.name("defensive priority")
			.rule(Rule.build()
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
		register(Ability.build() // increases the power of the users water type moves by 1.25x
			.id(DEPTHS_BORNE)
			.name("depths_borne")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(DESIGNATED_CHOMPERS)
			.name("designated chompers")
		);
		register(Ability.build()
			.id(DETONATE)
			.name("Detonate")
		);
		register(Ability.build()
			.id(DEVIOUS)
			.name("devious")
			.gate(Gate.build()
				.event(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				.condition(new NoCondition())
			)
		);
		register(Ability.build()
			.id(DIRT_DASH)
			.name("Dirt Dash")
		);
		register(Ability.build()
			.id(DISENCHANT)
			.name("disenchant")
		);
		register(Ability.build()
			.id(DO_OR_DIE)
			.name("do or die")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.3)))
			)
			.rule(Rule.build()
				.event(GenericId.SELF_MOVE_DEALT_DAMAGE)
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
			.rule(Rule.build()
                .event(GenericId.SELF_PRE_DRAINING_HEALTH) 
                .effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.HEALTH_DRAIN), new Constant(1.5)))
            )
        );
		register(Ability.build()
			.id(DRIVEN)
			.name("driven")
		);
		register(Ability.build()
			.id(DUST_DEVIL)
			.name("dust devil")
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
			.id(EARTHMOVER)
			.name("Earthmover")
		);
		register(Ability.build()
			.id(EFFULGENT)
			.name("effulgent")
		);
		register(Ability.build()
			.id(ELDER_MANE)
			.name("Elder Mane")
		);
		register(Ability.build()
			.id(ELECTRIFY)
			.name("Electrify")
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
			.rule(Rule.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new And(
					Comparator.greaterThan()
						.left(new ValueConstant(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.TYPE)))
						.right(new Constant(1)),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
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
		);
		register(Ability.build()
			.id(EXTENDED_REACH)
			.name("Extended Reach")
		);
		register(Ability.build()
			.id(EXTRATERRESTRIAL)
			.name("Extraterrestrial")
		);
		register(Ability.build()
			.id(FACADE)
			.name("facade")
			.gate(Gate.build() // prevents the ability being removed
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
			)
		);
		register(Ability.build() // reduces the damage taken from air type attacks and boosts it's rattack one stage when hit by one
			.id(FANNING_FLAME)
			.name("fanning_flame")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, TARGET)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)))
			)
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.rattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FESTIVE_SPIRIT)
			.name("festive spirit").gate(Gate.build() // prevents the ability being removed
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
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
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.POWDER), true),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build()
			.id(FOG_SUMMON)
			.name("fog summon")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.FOG, new Constant(5)))
			)
		);
		register(Ability.build()
			.id(FORESIGHT)
			.name("foresight")
			.rule(Rule.build()
				.event(IsolatedId.PRE_TURN)
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
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, TARGET)
				))
				.effect(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5))
				)
			)
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1))
				)
			)
		);
		register(Ability.build()
			.id(FORTISSIMO)
			.name("fortissimo")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.SOUND), true),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build() // completed
			.id(FRAIL_ARMOUR)
			.name("frail armour")
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE)
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
		);
		register(Ability.build()
			.id(GAIN_FEEDING)
			.name("Gain Feeding")
		);
		register(Ability.build() // completed
			.id(GLIDE)
			.name("glide")
			.rule(Rule.build()
				.event(GenericId.SELF_MOVE_DEALT_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1))
				)
			)
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE)
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
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.fogActive())
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.3)))
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
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.EXPLOSIVE), true),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build() // technician from pokemon
			.id(GURU)
			.name("guru")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(Comparator.lessThanEqualTo()
					.left(new INumberValueConstant(Value.OPP().MOVE().BASE_POWER()))
					.right(new Constant(60)))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.6)))
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
			.rule(Rule.build()
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
			.id(HIGH_PRESSURE)
			.name("high pressure")
		);
		register(Ability.build()
			.id(HIGH_STRUNG)
			.name("High Strung")
		);
		register(Ability.build()
			.id(HOTFOOT)
			.name("hotfoot")
		);
		register(Ability.build()
			.id(HOVER)
			.name("hover")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.EARTH),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build()
			.id(HYDRATE)
			.name("hydrate")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
			)
		);
		register(Ability.build()
			.id(HYDRO_VORTEX)
			.name("hydro vortex")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
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
				.event(GateId.CAN_DEFENDING_STAT_USE_STAT_BOOSTS, TARGET)
				.event(GateId.CAN_DEFENDING_STAT_USE_STAT_DROPS, TARGET)
				.condition(new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER))
			)
			.gate(Gate.build()
				.event(GateId.CAN_ATTACKING_STAT_USE_STAT_BOOSTS, USER)
				.event(GateId.CAN_ATTACKING_STAT_USE_STAT_DROPS, USER)
				.condition(new GateCondition(GateId.CAN_USE_OFFENSIVE_ABILITY, USER))
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
		register(Ability.build() // decreases the damage done by ice and fire attacks to the user by 50%
			.id(INSULATED)
			.name("insulated")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, TARGET),
					new Or(
						new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE),
						new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE)
					)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)))
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
			.name("land_borne")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.EARTH))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(LAZY)
			.name("lazy")
		);
		register(Ability.build()
			.id(LIFE_FORCE)
			.name("life force").gate(Gate.build() // prevents the ability being removed
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
			)
		);
		register(Ability.build()
			.id(LIGHTNING_ROD)
			.name("lightning rod")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
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
			.name("luck_of_the_sea")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.CRIT), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
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
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.RECOIL), true))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Ability.build()
			.id(MALWARE)
			.name("malware")
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE) // if hit by a move
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.effect(SetAbility.target(MALWARE))
			)
		);
		register(Ability.build()
			.id(MARKSMAN)
			.name("marksman")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.CRIT), new Constant(1.5))) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
		);
		register(Ability.build()
			.id(MASK_SWAP)
			.name("mask_swap")
			.rule(Rule.build()
				.event(GenericId.SELF_PRE_MOVE)
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
			.id(MERGE)
			.name("merge")
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
			.id(NOXIOUS_RAZORBACK)
			.name("Noxious Razorback")
		);
		register(Ability.build()
			.id(NOXIOUS_WEEDS)
			.name("noxious weeds")
			
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyMechanic.toUser(MechanicId.NOXIOUS_WEEDS))
			)
		);
		register(Ability.build()
			.id(OBSIDIAN_HEART)
			.name("obsidian_heart")
			//.rule() // increase the cost of ancient type moves by 10%
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
			)
		);
		register(Ability.build()
			.id(ODD_HUSK)
			.name("odd_husk")
			.gate(Gate.build()
				.event(GateId.CAN_SECONDARY_EFFECTS_AFFECT_TARGET, TARGET)
				.condition(new Not(new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)))
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
			.rule(Rule.build()
				.event(GenericId.SELF_PRE_DAMAGING)
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
				.event(GateId.CAN_MOVE_CRIT, TARGET)
				.condition(new Not(new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)))
			)
		);
		register(Ability.build()
			.id(PROWLER)
			.name("prowler")
		);
		register(Ability.build()
			.id(PUGILIST)
			.name("pugilist")
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
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
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
			.rule(Rule.build()
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
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.speed(new Constant(1)
				))
			)
		);
		register(Ability.build()
			.id(RECRUIT)
			.name("recruit")
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
			.rule(Rule.build()
                .event(GenericId.SELF_PRE_MOVE) // before anything about the move is actually used
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
			.id(SELF_DESTRUCT)
			.name("Self Destruct")
		);
		register(Ability.build()
			.id(SHAKEDOWN)
			.name("shakedown")
		);
		register(Ability.build()
			.id(SHARP_CLAWS)
			.name("sharp claws")
		);
		register(Ability.build() // when hit by a contact move, the attacker loses 1/16 of their max hp
			.id(SHARP_EDGES)
			.name("sharp edges")
			.rule(Rule.build()
				.event(GenericAntiId.HIT_BY_MOVE)
				.condition(new Equals<>(Value.OPP().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
				.effect(new DealDirectSelfDamage(new Multiply(new Constant(1/16), new ValueConstant(Value.OPP().HEALTH()))))
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
			.name("sky_borne")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.SELF().MOVE().TYPE(), TypeId.AIR))
				.effect(Effects.multiplyValue(Value.SELF().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.25)))
			)
		);
		register(Ability.build()
			.id(SLICK_SCALES)
			.name("Slick Scales")
		);
		register(Ability.build()
			.id(SLICK_SHELL)
			.name("slick shell")
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
		);
		register(Ability.build()
			.id(SOB)
			.name("sob")
		);
		register(Ability.build()
			.id(SOLAR_POWERED)
			.name("Solar Powered")
		);
		register(Ability.build()
			.id(SOUL_HOARDER)
			.name("Soul Hoarder")
		);
		register(Ability.build()
			.id(SOUL_SIPHON)
			.name("soul siphon")
			.rule(Rule.build()
				.event(GenericId.SELF_KNOCKED_OUT_OPP)
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
		);
		register(Ability.build()
			.id(SPOILS_OF_WAR)
			.name("Spoils of War")
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
			.id(STICKY)
			.name("Sticky")
		);
		register(Ability.build()
			.id(STOKED)
			.name("Stoked")
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
			.rule(Rule.build()
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
			.rule(Rule.build()
				.event(GenericId.SELF_PRE_DAMAGING)
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
				.event(GateId.CAN_REMOVE_USER_ABILITY, USER)
				.event(GateId.CAN_REMOVE_TARGET_ABILITY, TARGET)
				.condition(new False())
			)
		);
		register(Ability.build() // completed
			.id(UNGRACIOUS_HOST)
			.name("ungracious host")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.HEALTH_DRAIN), new Constant(-1)))
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
		register(Ability.build()
			.id(VETERAN)
			.name("Veteran")
		);
		// increases the users damage when the user is poisoned
		register(Ability.build() // build
			.id(VICIOUS)
			.name("vicious")
			.rule(Rule.build()
				.event(GenericId.SELF_PRE_DAMAGING)
				.condition(new Or(
					new Equals<>(Value.USER().STATUS_ID(), StatusId.POISON),
					new Equals<>(Value.USER().STATUS_ID(), StatusId.BAD_POISON)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
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
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.userHasStatusAilment(),
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
			)
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().STATUS_ID(), StatusId.BURN))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(2)))
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
				.event(GenericId.SELF_PRE_MOVE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.ACCURACY), new Constant(1.3)))
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
			.rule(Rule.build()
				.event(GenericAntiId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(new Equals<>(Value.USER().MOVE().MOVE_TAG(MoveTagId.CONTACT), true))
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
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BRAWLER),
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.MIND)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)))
			)
		);
		register(Ability.build()
			.id(WILDFIRE)
			.name("wildfire")
			.rule(Rule.build()
				.event(IsolatedId.PRE_TURN)
				.condition(new And(
					new Equals<>(Value.USER().CURRENT_HEALTH(), Value.USER().HEALTH()),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND)
				))
				.effect(Effects.addValue(Value.SELF().PRIORITY(), new Constant(1)))
			)
		);
		register(Ability.build() // completed
			.id(WIND_SUMMON)
			.name("wind summon")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.effect(SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(5)))
			)
		);
		register(Ability.build() // completed
			.id(WISE)
			.name("wise")
			.rule(Rule.build()
				.event(GenericId.SELF_PRE_DAMAGING)
				.condition(Comparator.lessThan()
					.left(new ValueConstant(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.TYPE)))
					.right(new Constant(1))
				)
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE(), new Constant(2)))
			)
		);
		register(Ability.build()
			.id(WOODSMAN)
			.name("woodman")
			.interceptor(Interceptor.build()
				.event(GenericAntiId.SELF_PRE_DAMAGED)
				.condition(new Or(
					new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT),
					new GateCondition(GateId.CAN_USE_DEFENSIVE_ABILITY, USER)
				))
				.effect(ApplyStatChanges.userChangingUser()
					.mattack(new Constant(1)
				))
			)
		);
    }
}
