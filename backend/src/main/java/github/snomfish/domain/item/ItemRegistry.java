package github.snomfish.domain.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.move.modifiers.ModifierId;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Conditions;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.False;
import github.snomfish.functionality.condition.Not;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.ApplyStatus;
import github.snomfish.functionality.effect.effects.ClutchEffect;
import github.snomfish.functionality.effect.effects.ConsumeItem;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.event.Gate;
import github.snomfish.functionality.event.Rule;
import github.snomfish.functionality.event.gateIds.GateForAllId;
import github.snomfish.functionality.event.gateIds.GateForUserId;
import github.snomfish.functionality.event.ruleIds.ConstantRuleId;
import github.snomfish.functionality.event.ruleIds.RuleForTargetId;
import github.snomfish.functionality.event.ruleIds.RuleForUserId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.item.ItemId.*;
import static github.snomfish.domain.item.ItemTagId.*;
import static github.snomfish.domain.species.SpeciesId.*;
import static github.snomfish.functionality.context.ContextSideId.*;

// oi add a condition in condition schematics for general item use, offensive item use and defensive.
// simplest solution for whe the loomian cannot use items/ are about to be pile drived by sly cavenish
public class ItemRegistry {


    private static final Map<ItemId, Item> registry = new HashMap<>();


    private ItemRegistry() {}


	public static Map<ItemId, Item> registry() {return registry;}
    public static Item get(ItemId id) {return registry.get(id);}


    private static void register(Item item) {
		if (registry.containsKey(item.id())) throw new RuntimeException("ItemRegistry attempted to register duplicate entry \"" + item.id().name() + "\"");
        registry.put(item.id(), item);
    }


    static {
        register(Item.build()
			.id(NO_ITEM)
			.name("no item")
		);
		register(Item.build()
			.id(USELESS_ITEM)
			.name("useless item")
		);
		register(Item.build()
			.id(CHOCOLATE_BAR)
			.name("chocolate bar")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(ConsumeItem.user())
			)
			.tag(CONSUMABLE)
			// reduce the cost of all moves to 0 then consume when a move is used
		);
		register(Item.build()
			.id(CLUTCH_PLUSHIE)
			.name("clutch plushie")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_CLUTCHES)
				.condition(Conditions.userIsAtFullHealth())
				.effect(new EffectSequence(
					new ClutchEffect(),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(DRAIN_ORB)
			.name("drain orb")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_PRE_DRAINING_HEALTH)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.HEALTH_DRAIN), new Constant(1.2)))
			)
		);
		register(Item.build() // TODO currently will not boost the damage of body crash and stratgem
			.id(DROP_OF_YOUTH)
			.name("drop of youth")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().CAN_EVOLVE(), true))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DEFENDING_STAT), new Constant(1.5)))
			)
		);
		register(Item.build()
			.id(DRY_ICE)
			.name("dry ice")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.effect(ApplyStatus.toUser(StatusId.FROSTBITE))
			)
		);
		register(Item.build()
			.id(ENERGY_ORB)
			.name("energy orb")
			// TODO decrease the cost of moves by 25%
		);
		register(Item.build()
			.id(HEALTH_AMULET)
			.name("health amulet")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				// TODO restore 1/16 of max hp	
			)
		);
		register(Item.build()
			.id(HEAVY_ARMOUR)
			.name("heavy armour")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DEFENDING_STAT), new Constant(1.2)))
			)
			// TODO increase costs of moves by 15%
		);
		register(Item.build()
			.id(HEAVY_SHACKLES)
			.name("heavy shackles")
		);
		register(Item.build()
			.id(HEAVY_SHIELD)
			.name("heavy shield")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DEFENDING_STAT), new Constant(1.2)))
			)
			// TODO increase costs of moves by 15%
		);
		register(Item.build()
			.id(KABUNGA_COFFEE)
			.name("kabunga coffee")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.condition(new RandomCondition(new Constant(0.1)))
				// TODO restore 1/8 of the SELF's max energy
			)
		);
		register(Item.build()
			.id(MILKSHAKE)
			.name("milkshake")
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(MYSTERIOUS_DUST)
			.name("mysterious dust")
		);
		register(Item.build()
			.id(MYSTIC_WAND)
			.name("mystic wand")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().SPECIES_ID(), SpeciesId.SHAWCHI),
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.RANGED)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.ATTACKING_STAT), new Constant(1.5)))
			)
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.SELF().SPECIES_ID(), SpeciesId.SHAWCHI),
					new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DEFENDING_STAT), new Constant(1.5)))
			)
		);
		register(Item.build()
			.id(POWER_CUFFS)
			.name("power cuffs")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
			// TODO increase energy cost of moves by 25%
		);
		register(Item.build()
			.id(RAGEFUL_PLUSHIE)
			.name("rageful plushie")
			.rule(Rule.build()
				.event(RuleForTargetId.RECEIVED_DAMAGE_FROM_MOVE)
				.condition(Comparator.greaterThan()
					.left(new ValueConstant(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.TYPE)))
					.right(new Constant(1))
				)
				.effect(new EffectSequence(
					ApplyStatChanges.targetChangingTarget()
						.mattack(new Constant(2))
						.rattack(new Constant(2)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(RESTRICTIVE_ARMOUR)
			.name("restrictive armour")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(2/3)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_USE_SUPPORT_MOVES)
				.condition(new False())
			)
		);
		register(Item.build()
			.id(RESTRICTIVE_SHIELD)
			.name("restrictive shield")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(2/3)))
			)
			.gate(Gate.build()
				.event(GateForUserId.CAN_SELF_USE_SUPPORT_MOVES)
				.condition(new False())
			)
		);
		register(Item.build()
			.id(SPECIALY_BOOTS)
			.name("specialty boots")
			.rule(Rule.build()
				.condition(new Equals<>(Value.USER().NUM_OF_MOVES(), new Constant(4)))
			)
		);
		register(Item.build()
			.id(SPECIALY_GLOVES)
			.name("specialty gloves")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE),
					new Equals<>(Value.USER().NUM_OF_MOVES(), new Constant(4))
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
			)
			// TODO locks the user into a move
		);
		register(Item.build()
			.id(SPECIALY_GOGGLES)
			.name("specialty goggles")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.RANGED),
					new Equals<>(Value.USER().NUM_OF_MOVES(), new Constant(4))
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)))
			)
			// TODO locks the user into a move
		);
		register(Item.build() // doubles exclusive
			.id(STRONG_MAGNET)
			.name("strong magnet")
		);
		register(Item.build()
			.id(THUNDER_ORB)
			.name("thunder orb")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.effect(ApplyStatus.toUser(StatusId.PARALYSIS))
			)
		);
		register(Item.build()
			.id(VIRULENT_FANG)
			.name("virulent fang")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.effect(ApplyStatus.toUser(StatusId.POISON))
			)
		);
		register(Item.build()
			.id(VOLCANIC_ASH)
			.name("volcanic ash")
			.rule(Rule.build()
				.event(ConstantRuleId.END_OF_TURN)
				.effect(ApplyStatus.toUser(StatusId.BURN))
			)
		);


		register(Item.build()
			.id(FOG_GLOBE)
			.name("Fog Globe")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.FOG)))
				.effect(new EffectSequence(
					SetWeather.userSetting(WeatherId.FOG, new Constant(3)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(HEAT_GLOBE)
			.name("Heat Globe")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE)))
				.effect(new EffectSequence(
					SetWeather.userSetting(WeatherId.HEATWAVE, new Constant(3)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(RAIN_GLOBE)
			.name("Rain Globe")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN)))
				.effect(new EffectSequence(
					SetWeather.userSetting(WeatherId.RAIN, new Constant(3)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(STORM_GLOBE)
			.name("Storm Globe")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM)))
				.effect(new EffectSequence(
					SetWeather.userSetting(WeatherId.THUNDERSTORM, new Constant(3)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(WIND_GLOBE)
			.name("Wind Globe")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS)))
				.effect(new EffectSequence(
					SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(3)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);


		register(Item.build()
			.id(FOG_SCALE)
			.name("Fog Scale")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.FOG))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build()
			.id(HEAT_SCALE)
			.name("Heat Scale")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build()
			.id(RAIN_SCALE)
			.name("Rain Scale")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build()
			.id(STORM_SCALE)
			.name("Storm Scale")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build()
			.id(WIND_SCALE)
			.name("Wind Scale")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SET_WEATHER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);

		
		register(Item.build()
			.id(AIR_ESSENCE)
			.name("air essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Item.build()
			.id(ANCIENT_ESSENCE)
			.name("ancient essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(BRAWLER_ESSENCE)
			.name("brawler essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(BUG_ESSENCE)
			.name("bug essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(DARK_ESSENCE)
			.name("dark essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(EARTH_ESSENCE)
			.name("earth essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.EARTH))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(ELECTRIC_ESSENCE)
			.name("electric essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(FIRE_ESSENCE)
			.name("fire essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(ICE_ESSENCE)
			.name("ice essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(LIGHT_ESSENCE)
			.name("light essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(METAL_ESSENCE)
			.name("metal essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(MIND_ESSENCE)
			.name("mind essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(PLANT_ESSENCE)
			.name("plant essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.PLANT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(SPIRIT_ESSENCE)
			.name("spirit essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(SIMPLE_ESSENCE)
			.name("simple essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(TOXIC_ESSENCE)
			.name("toxic essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Item.build()
			.id(WATER_ESSENCE)
			.name("water essence")
			.rule(Rule.build()
                .event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
            )
		);
		register(Item.build()
			.id(ARCHAIC_PEARL)
			.name("archaic pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(BLOSSOM_PEARL)
			.name("blossom pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(DRAFT_PEARL)
			.name("draft pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(FLAME_PEARL)
			.name("flame pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(FROST_PEARL)
			.name("frost pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ICE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(MARTIAL_PEARL)
			.name("martial pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(MENTAL_PEARL)
			.name("mental pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.MIND))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(METALLIC_PEARL)
			.name("metallic pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.METAL))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(NOXIOUS_PEARL)
			.name("noxious pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(PLAIN_PEARL)
			.name("plain pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SHADE_PEARL)
			.name("shade pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.DARK))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SHINE_PEARL)
			.name("shine pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SPECTRAL_PEARL)
			.name("spectral pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(TERRA_PEARL)
			.name("terra pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.EARTH))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(VOLT_PEARL)
			.name("volt pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(WAVE_PEARL)
			.name("wave pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(WEB_PEARL)
			.name("web pearl")
			.rule(Rule.build()
                .event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BUG))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.OPP().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(0.5)),
					ConsumeItem.target()
				))
            )
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(ARCHAIC_SHELL)
			.name("archaic shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(BLOSSOM_SHELL)
			.name("blossom shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.PLANT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(DRAFT_SHELL)
			.name("draft shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(FLAME_SHELL)
			.name("flame shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(FROST_SHELL)
			.name("frost shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(MARTIAL_SHELL)
			.name("martial shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(MENTAL_SHELL)
			.name("mental shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(METALLIC_SHELL)
			.name("metallic shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(NOXIOUS_SHELL)
			.name("noxious shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(PLAIN_SHELL)
			.name("plain shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SHADE_SHELL)
			.name("shade shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SHINE_SHELL)
			.name("shine shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(SPECTRAL_SHELL)
			.name("spectral shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(TERRA_SHELL)
			.name("terra shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.EARTH))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(VOLT_SHELL)
			.name("volt shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(WAVE_SHELL)
			.name("wave shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);
		register(Item.build()
			.id(WEB_SHELL)
			.name("web shell")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG))
				.effect(new EffectSequence(
					Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.5)),
					ConsumeItem.user()
				))
			)
			.tag(CONSUMABLE)
		);


		register(Item.build()
			.id(FLORABURST)
			.name("Floraburst")
		);
		register(Item.build()
			.id(GASTROBURST)
			.name("Gastroburst")
		);
		register(Item.build()
			.id(ERUPTIBURST)
			.name("Eruptiburst")
		);
		register(Item.build()
			.id(KRAKABURST)
			.name("Krakaburst")
		);
		register(Item.build()
			.id(STRATBURST)
			.name("Strataburst")
		);
		register(Item.build()
			.id(DAKUBURST)
			.name("Dakuburst")
		);
		register(Item.build()
			.id(METROBURST)
			.name("Metroburst")
		);
		register(Item.build()
			.id(ICIBURST)
			.name("Iciburst")
		);
		register(Item.build()
			.id(SKELIBURST)
			.name("Skeliburst")
		);
		register(Item.build()
			.id(JOLTBURST)
			.name("Joltburst")
		);
		register(Item.build()
			.id(NOSHBURST)
			.name("Noshburst")
		);
		register(Item.build()
			.id(CELEBURST)
			.name("Celeburst")
		);
		register(Item.build()
			.id(DREGGOBURST)
			.name("Dreggoburst")
		);
		register(Item.build()
			.id(IMPERIBURST)
			.name("Imperiburst")
		);
		register(Item.build()
			.id(KRAMBURST)
			.name("Kramburst")
		);
		register(Item.build()
			.id(GLINTBURST)
			.name("Glintburst")
		);


		register(Item.build() // drop of youth for scorb line
			.id(SCRAP_METAL)
			.name("scrap metal")
			.rule(Rule.build()
				.event(RuleForTargetId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Or(
					new Equals<>(Value.SELF().SPECIES_ID(), SCORB),
					new Equals<>(Value.SELF().SPECIES_ID(), VEYLENS)
				))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MDEFENSE(), new Constant(1.4)),
					Effects.multiplyValue(Value.OPP().RDEFENSE(), new Constant(1.4))
				)))
			)
		);
		register(Item.build() // milkshake for scorb line
			.id(SPARE_CABLE)
			.name("spare cable")
		);
		register(Item.build() // makes moves from scorb line undodgeable
			.id(CLEAR_LENS)
			.name("clear lens")
		);
		register(Item.build()
			.id(FLAME_CORE)
			.name("flame core")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.FIRE))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Item.build()
			.id(FREEZER_CORE)
			.name("freezer core")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.ICE))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Item.build()
			.id(VOLTAIC_CORE)
			.name("voltaic core")
			.rule(Rule.build()
				.event(RuleForUserId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.ELECTRIC))
			)
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
		);
		register(Item.build() // power cuffs for scorb line
			.id(POWER_CORE)
			.name("power core")
			.rule(Rule.build()
				.event(RuleForUserId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.or(
					new Equals<>(Value.TARGET().SPECIES_ID(), SCORB),
					new Equals<>(Value.TARGET().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.TARGET().SPECIES_ID(), GARDRONE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().MOVE_MODIFIER(ModifierId.DAMAGE), new Constant(1.2)))
			)
			// TODO increase energy cost of moves by 25%
		);
		register(Item.build() // energy orb for scorb line
			.id(QUANTUM_CORE)
			.name("quantum core")
		);
    }
}
