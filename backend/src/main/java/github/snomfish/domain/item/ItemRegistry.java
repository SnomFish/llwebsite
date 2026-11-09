package github.snomfish.domain.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Conditions;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.FalseCondition;
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
import github.snomfish.functionality.event.ids.GenericAntiId;
import github.snomfish.functionality.event.ids.GenericId;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.item.ItemId.*;
import static github.snomfish.domain.species.SpeciesId.*;
import static github.snomfish.functionality.context.ContextSideId.*;
import static github.snomfish.functionality.event.ids.GateId.*;
import static github.snomfish.functionality.event.ids.RuleId.*;

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
        register(Item.build(NO_ITEM)
			.name("no item")
		);
		register(Item.build(USELESS_ITEM)
			.name("useless item")
		);
		register(Item.build(CHOCOLATE_BAR)
			.name("chocolate bar")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(ConsumeItem.user())
			)
			// reduce the cost of all moves to 0 then consume when a move is used
		);
		register(Item.build(CLUTCH_PLUSHIE)
			.name("clutch plushie")
			.rule(Rule.build()
				.event(CLUTCH_EVENT, TARGET)
				.condition(new Equals<>(Value.TARGET().HEALTH(), Value.TARGET().CURRENT_HEALTH()))
				.effect(new ClutchEffect())
				
			)
		);
		register(Item.build(DRAIN_ORB)
			.name("drain orb")
			.rule(Rule.build()
				.event(PRE_HEALTH_DRAIN_EVENT, USER)
				.effect(Effects.multiplyValue(Value.USER().MOVE().HEALTH_DRAIN_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(DROP_OF_YOUTH) // TODO currently will not boost the damage of body crash and stratgem
			.name("drop of youth")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().CAN_EVOLVE(), true))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DEFENDING_STAT_MODIFIER(), new Constant(1.5)))
			)
		);
		register(Item.build(DRY_ICE)
			.name("dry ice")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				.effect(ApplyStatus.toUser(StatusId.FROSTBITE))
			)
		);
		register(Item.build(ENERGY_ORB)
			.name("energy orb")
			// TODO decrease the cost of moves by 25%
		);
		register(Item.build(HEALTH_AMULET)
			.name("health amulet")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				// TODO restore 1/16 of max hp	
			)
		);
		register(Item.build(HEAVY_ARMOUR)
			.name("heavy armour")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DEFENDING_STAT_MODIFIER(), new Constant(1.2)))
			)
			// TODO increase costs of moves by 15%
		);
		register(Item.build(HEAVY_SHACKLES)
			.name("heavy shackles")
		);
		register(Item.build(HEAVY_SHIELD)
			.name("heavy shield")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DEFENDING_STAT_MODIFIER(), new Constant(1.2)))
			)
			// TODO increase costs of moves by 15%
		);
		register(Item.build(KABUNGA_COFFEE)
			.name("kabunga coffee")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				.condition(new RandomCondition(new Constant(0.1)))
				// TODO restore 1/8 of the user's max energy
			)
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, TARGET)
				.condition(new RandomCondition(new Constant(0.1)))
				// TODO restore 1/8 of the target's max energy
			)
		);
		register(Item.build(MILKSHAKE)
			.name("milkshake")
		);
		register(Item.build(MYSTERIOUS_DUST)
			.name("mysterious dust")
		);
		register(Item.build(MYSTIC_WAND)
			.name("mystic wand")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.USER().SPECIES_ID(), SpeciesId.SHAWCHI),
					new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.RANGED)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().ATTACKING_STAT_MODIFIER(), new Constant(1.5)))
			)
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					new Equals<>(Value.SELF().SPECIES_ID(), SpeciesId.SHAWCHI),
					new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED)
				))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DEFENDING_STAT_MODIFIER(), new Constant(1.5)))
			)
		);
		register(Item.build(POWER_CUFFS)
			.name("power cuffs")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
			// TODO increase energy cost of moves by 25%
		);
		register(Item.build(RAGEFUL_PLUSHIE)
			.name("rageful plushie")
			.rule(Rule.build()
				.event(DEALT_DAMAGE_EVENT, TARGET)
				.condition(Comparator.greaterThan()
					.left(new ValueConstant(Value.USER().MOVE().TYPE_MODIFIER()))
					.right(new Constant(1))
				)
				.effect(ApplyStatChanges.targetChangingTarget()
					.mattack(new Constant(2))
					.rattack(new Constant(2))
				)
			)
		);
		register(Item.build(RESTRICTIVE_ARMOUR)
			.name("restrictive armour")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(2/3)))
			)
			.gate(Gate.build()
				.event(CAN_USE_SUPPORT_MOVES, USER)
				.condition(new FalseCondition())
			)
		);
		register(Item.build(RESTRICTIVE_SHIELD)
			.name("restrictive shield")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().CATEGORY(), CategoryId.RANGED))
				.effect(Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(2/3)))
			)
			.gate(Gate.build()
				.event(CAN_USE_SUPPORT_MOVES, USER)
				.condition(new FalseCondition())
			)
		);
		register(Item.build(SPECIALY_BOOTS)
			.name("specialty boots")
		);
		register(Item.build(SPECIALY_GLOVES)
			.name("specialty gloves")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.MELEE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
			)
			// TODO locks the user into a move
		);
		register(Item.build(SPECIALY_GOGGLES)
			.name("specialty goggles")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().CATEGORY(), CategoryId.RANGED))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)))
			)
			// TODO locks the user into a move
		);
		register(Item.build(STRONG_MAGNET) // doubles exclusive
			.name("strong magnet")
		);
		register(Item.build(THUNDER_ORB)
			.name("thunder orb")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				.effect(ApplyStatus.toUser(StatusId.PARALYSIS))
			)
		);
		register(Item.build(VIRULENT_FANG)
			.name("virulent fang")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				.effect(ApplyStatus.toUser(StatusId.POISON))
			)
		);
		register(Item.build(VOLCANIC_ASH)
			.name("volcanic ash")
			.rule(Rule.build()
				.event(END_OF_TURN_EVENT, USER)
				.effect(ApplyStatus.toUser(StatusId.BURN))
			)
		);


		register(Item.build(FOG_GLOBE)
			.name("Fog Globe")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.FOG)))
				.effect(SetWeather.userSetting(WeatherId.FOG, new Constant(3)))
			)
		);
		register(Item.build(HEAT_GLOBE)
			.name("Heat Globe")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE)))
				.effect(SetWeather.userSetting(WeatherId.HEATWAVE, new Constant(3)))
			)
		);
		register(Item.build(RAIN_GLOBE)
			.name("Rain Globe")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN)))
				.effect(SetWeather.userSetting(WeatherId.RAIN, new Constant(3)))
			)
		);
		register(Item.build(STORM_GLOBE)
			.name("Storm Globe")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM)))
				.effect(SetWeather.userSetting(WeatherId.THUNDERSTORM, new Constant(3)))
			)
		);
		register(Item.build(WIND_GLOBE)
			.name("Wind Globe")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(new Not(new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS)))
				.effect(SetWeather.userSetting(WeatherId.STRONG_WINDS, new Constant(3)))
			)
		);


		register(Item.build(FOG_SCALE)
			.name("Fog Scale")
			.rule(Rule.build()
				.event(POST_USER_SET_WEATHER_EVENT, USER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.FOG))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
			.rule(Rule.build()
				.event(POST_TARGET_SET_WEATHER_EVENT, TARGET)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.FOG))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build(HEAT_SCALE)
			.name("Heat Scale")
			.rule(Rule.build()
				.event(POST_USER_SET_WEATHER_EVENT, USER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
			.rule(Rule.build()
				.event(POST_TARGET_SET_WEATHER_EVENT, TARGET)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.HEATWAVE))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build(RAIN_SCALE)
			.name("Rain Scale")
			.rule(Rule.build()
				.event(POST_USER_SET_WEATHER_EVENT, USER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
			.rule(Rule.build()
				.event(POST_TARGET_SET_WEATHER_EVENT, TARGET)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.RAIN))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build(STORM_SCALE)
			.name("Storm Scale")
			.rule(Rule.build()
				.event(POST_USER_SET_WEATHER_EVENT, USER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
			.rule(Rule.build()
				.event(POST_TARGET_SET_WEATHER_EVENT, TARGET)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.THUNDERSTORM))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);
		register(Item.build(WIND_SCALE)
			.name("Wind Scale")
			.rule(Rule.build()
				.event(POST_USER_SET_WEATHER_EVENT, USER)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
			.rule(Rule.build()
				.event(POST_TARGET_SET_WEATHER_EVENT, TARGET)
				.condition(new Equals<>(Value.WEATHER_ID(), WeatherId.STRONG_WINDS))
				.effect(Effects.addValue(Value.WEATHER_LIFESPAN(), new Constant(3)))
			)
		);

		
		register(Item.build(AIR_ESSENCE)
			.name("air essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(ANCIENT_ESSENCE)
			.name("ancient essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(BRAWLER_ESSENCE)
			.name("brawler essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(BUG_ESSENCE)
			.name("bug essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(DARK_ESSENCE)
			.name("dark essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(EARTH_ESSENCE)
			.name("earth essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.EARTH))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(ELECTRIC_ESSENCE)
			.name("electric essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(FIRE_ESSENCE)
			.name("fire essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(ICE_ESSENCE)
			.name("ice essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(LIGHT_ESSENCE)
			.name("light essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(METAL_ESSENCE)
			.name("metal essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(MIND_ESSENCE)
			.name("mind essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(PLANT_ESSENCE)
			.name("plant essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.PLANT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(SPIRIT_ESSENCE)
			.name("spirit essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(SIMPLE_ESSENCE)
			.name("simple essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(TOXIC_ESSENCE)
			.name("toxic essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(WATER_ESSENCE)
			.name("water essence")
			.rule(Rule.build()
                .event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
            )
		);
		register(Item.build(ARCHAIC_PEARL)
			.name("archaic pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
			)
		);
		register(Item.build(BLOSSOM_PEARL)
			.name("blossom pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.PLANT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(DRAFT_PEARL)
			.name("draft pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.AIR))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(FLAME_PEARL)
			.name("flame pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.FIRE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(FROST_PEARL)
			.name("frost pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ICE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(MARTIAL_PEARL)
			.name("martial pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(MENTAL_PEARL)
			.name("mental pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.MIND))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(METALLIC_PEARL)
			.name("metallic pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.METAL))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(NOXIOUS_PEARL)
			.name("noxious pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(PLAIN_PEARL)
			.name("plain pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(SHADE_PEARL)
			.name("shade pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.DARK))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(SHINE_PEARL)
			.name("shine pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(SPECTRAL_PEARL)
			.name("spectral pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(TERRA_PEARL)
			.name("terra pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.EARTH))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(VOLT_PEARL)
			.name("volt pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(WAVE_PEARL)
			.name("wave pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.WATER))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(WEB_PEARL)
			.name("web pearl")
			.rule(Rule.build()
                .event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.OPP().MOVE().TYPE(), TypeId.BUG))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.OPP().MOVE().DAMAGE_MODIFIER(), new Constant(0.5)),
					ConsumeItem.target()
				)))
            )
		);
		register(Item.build(ARCHAIC_SHELL)
			.name("archaic shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ANCIENT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(BLOSSOM_SHELL)
			.name("blossom shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.PLANT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(DRAFT_SHELL)
			.name("draft shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.AIR))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(FLAME_SHELL)
			.name("flame shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(FROST_SHELL)
			.name("frost shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(MARTIAL_SHELL)
			.name("martial shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BRAWLER))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(MENTAL_SHELL)
			.name("mental shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.MIND))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(METALLIC_SHELL)
			.name("metallic shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.METAL))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(NOXIOUS_SHELL)
			.name("noxious shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.TOXIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(PLAIN_SHELL)
			.name("plain shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SIMPLE))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(SHADE_SHELL)
			.name("shade shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.DARK))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(SHINE_SHELL)
			.name("shine shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.LIGHT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(SPECTRAL_SHELL)
			.name("spectral shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.SPIRIT))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(TERRA_SHELL)
			.name("terra shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.EARTH))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(VOLT_SHELL)
			.name("volt shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(WAVE_SHELL)
			.name("wave shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.WATER))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(WEB_SHELL)
			.name("web shell")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new Equals<>(Value.USER().MOVE().TYPE(), TypeId.BUG))
				.effect(new EffectSequence(List.of(
					Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.5)),
					ConsumeItem.user()
				)))
			)
		);
		register(Item.build(SCRAP_METAL) // drop of youth for scorb line
			.name("scrap metal")
			.rule(Rule.build()
				.event(GenericAntiId.PRE_OPP_DEAL_FORMULA_DAMAGE)
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
		register(Item.build(SPARE_CABLE) // milkshake for scorb line
			.name("spare cable")
		);
		register(Item.build(CLEAR_LENS) // makes moves from scorb line undodgeable
			.name("clear lens")
		);
		register(Item.build(FLAME_CORE)
			.name("flame core")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.FIRE))
			)
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.FIRE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(FREEZER_CORE)
			.name("freezer core")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.ICE))
			)
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ICE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(VOLTAIC_CORE)
			.name("voltaic core")
			.rule(Rule.build()
				.event(GenericId.SELF_SWAPPED_IN)
				.condition(Conditions.or(
					new Equals<>(Value.USER().SPECIES_ID(), SCORB),
					new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
				))
				.effect(SetType.userSecondary(TypeId.ELECTRIC))
			)
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(new And(
					Conditions.or(
						new Equals<>(Value.USER().SPECIES_ID(), SCORB),
						new Equals<>(Value.USER().SPECIES_ID(), VEYLENS),
						new Equals<>(Value.USER().SPECIES_ID(), GARDRONE)
					),
					new Equals<>(Value.USER().MOVE().TYPE(), TypeId.ELECTRIC)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
		);
		register(Item.build(POWER_CORE) // power cuffs for scorb line
			.name("power core")
			.rule(Rule.build()
				.event(GenericId.PRE_SELF_DEAL_FORMULA_DAMAGE)
				.condition(Conditions.or(
					new Equals<>(Value.TARGET().SPECIES_ID(), SCORB),
					new Equals<>(Value.TARGET().SPECIES_ID(), VEYLENS),
					new Equals<>(Value.TARGET().SPECIES_ID(), GARDRONE)
				))
				.effect(Effects.multiplyValue(Value.USER().MOVE().DAMAGE_MODIFIER(), new Constant(1.2)))
			)
			// TODO increase energy cost of moves by 25%
		);
		register(Item.build(QUANTUM_CORE) // energy orb for scorb line
			.name("quantum core")
		);
    }
}
