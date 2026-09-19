package github.snomfish.domain.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.ClutchEffect;
import github.snomfish.functionality.effect.effects.ConsumeTargetItem;
import github.snomfish.functionality.effect.effects.ConsumeUserItem;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.item.ItemId.*;
import static github.snomfish.domain.species.SpeciesId.*;
import static github.snomfish.domain.type.TypeId.*;
import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.event.EventSideId.*;

// oi add a condition in condition schematics for general item use, offensive item use and defensive.
// simplest solution for whe the loomian cannot use items/ are about to be pile drived by sly cavenish
public class ItemRegistry {


    private static final Map<ItemId, Item> registry = new HashMap<>();


    private ItemRegistry() {}


    public static Item get(ItemId id) {
        return registry.get(id);
    }


    private static void register(
        ItemId id,
        String name,
        TriggerRule rule
    ) {
        registry.put(id, new Item(id, name, List.of(rule)));
    }


    /*private static void register(
        ItemId id,
        String name,
        List<TriggerRule> triggerRules
    ) {
        registry.put(id, new Item(id, name, triggerRules));
    }*/


    static {
        register(
			NO_ITEM,
			"no item",
			null
		);
		register(
			USELESS_ITEM,
			"useless item",
			null
		);
		register(
			CHOCOLATE_BAR,
			"chocolate bar",
			null
		);
		register(
			CLUTCH_PLUSHIE,
			"clutch plushie",
			new TriggerRule(
				new Event(CLUTCH_EVENT, TARGET), 
				new Equals(Value.TARGET_BATTLE_HEALTH, Value.TARGET_CURRENT_HEALTH),
				new ClutchEffect()
			)
		);
		register(
			DRAIN_ORB,
			"drain orb",
			new TriggerRule(
				new Event(HEALTH_DRAIN_EVENT, USER), 
				new NoCondition(),
				new MultiplyValueEffect(Value.MOVE_HEALTH_DRAIN_MODIFIER, new Constant(1.2))
			)
		);
		register(
			DROP_OF_YOUTH,
			"drop of youth",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, TARGET), 
				new Equals(Value.TARGET_CAN_EVOLVE, true),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.TARGET_BATTLE_MDEFENSE, new Constant(1.5)),
					new MultiplyValueEffect(Value.TARGET_BATTLE_RDEFENSE, new Constant(1.5))
				))
			)
		);
		register(
			DRY_ICE,
			"dry ice",
			null
		);
		register(
			ENERGY_ORB,
			"energy orb",
			null
		);
		register(
			HEALTH_AMULET,
			"health amulet",
			null
		);
		register(
			HEAVY_ARMOUR,
			"heavy armour",
			null
		);
		register(
			HEAVY_SHACKLES,
			"heavy shackles",
			null
		);
		register(
			HEAVY_SHIELD,
			"heavy shield",
			null
		);
		register(
			KABUNGA_COFFEE,
			"kabunga coffee",
			null
		);
		register(
			MILKSHAKE,
			"milkshake",
			null
		);
		register(
			MYSTERIOUS_DUST,
			"mysterious dust",
			null
		);
		register(
			MYSTIC_WAND,
			"mystic wand",
			null
		);
		register(
			POWER_CUFFS,
			"power cuffs",
			null
		);
		register(
			RAGEFUL_PLUSHIE,
			"rageful plushie",
			null
		);
		register(
			RESTRICTIVE_ARMOUR,
			"restrictive armour",
			null
		);
		register(
			RESTRICTIVE_SHIELD,
			"restrictive shield",
			null
		);
		register(
			SPECIALY_BOOTS,
			"specialty boots",
			null
		);
		register(
			SPECIALY_GLOVES,
			"specialty gloves",
			null
		);
		register(
			SPECIALY_GOGGLES,
			"specialty goggles",
			null
		);
		register(
			STRONG_MAGNET,
			"strong magnet",
			null
		);
		register(
			THUNDER_ORB,
			"thunder orb",
			null
		);
		register(
			VIRULENT_FANG,
			"virulent fang",
			null
		);
		register(
			VOLCANIC_ASH,
			"volcanic ash",
			null
		);
		register(
			AIR_ESSENCE,
			"air essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, AIR),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			ANCIENT_ESSENCE,
			"ancient essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, ANCIENT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			BRAWLER_ESSENCE,
			"brawler essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, BRAWLER),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			BUG_ESSENCE,
			"bug essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, BUG),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			DARK_ESSENCE,
			"dark essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, DARK),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			EARTH_ESSENCE,
			"earth essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, EARTH),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			ELECTRIC_ESSENCE,
			"electric essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, ELECTRIC),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			FIRE_ESSENCE,
			"fire essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, FIRE),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			ICE_ESSENCE,
			"ice essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, ICE),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			LIGHT_ESSENCE,
			"light essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, LIGHT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			METAL_ESSENCE,
			"metal essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, METAL),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			MIND_ESSENCE,
			"mind essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, MIND),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			PLANT_ESSENCE,
			"plant essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, PLANT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			SPIRIT_ESSENCE,
			"spirit essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, SPIRIT),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			SIMPLE_ESSENCE,
			"simple essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, SIMPLE),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			TOXIC_ESSENCE,
			"toxic essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, TOXIC),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			WATER_ESSENCE,
			"water essence",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, USER),
                new Equals(Value.MOVE_TYPE, WATER),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
		);
		register(
			ARCHAIC_PEARL,
			"archaic pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, ANCIENT),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			BLOSSOM_PEARL,
			"blossom pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, PLANT),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			DRAFT_PEARL,
			"draft pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, AIR),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			FLAME_PEARL,
			"flame pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, FIRE),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			FROST_PEARL,
			"frost pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, ICE),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			MARTIAL_PEARL,
			"martial pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, BRAWLER),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			MENTAL_PEARL,
			"mental pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, MIND),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			METALLIC_PEARL,
			"metallic pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, METAL),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			NOXIOUS_PEARL,
			"noxious pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, TOXIC),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			PLAIN_PEARL,
			"plain pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, SIMPLE),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			SHADE_PEARL,
			"shade pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, DARK),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			SHINE_PEARL,
			"shine pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, LIGHT),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			SPECTRAL_PEARL,
			"spectral pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, SPIRIT),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			TERRA_PEARL,
			"terra pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, EARTH),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			VOLT_PEARL,
			"volt pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, ELECTRIC),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			WAVE_PEARL,
			"wave pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, WATER),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			WEB_PEARL,
			"web pearl",
			new TriggerRule(
                new Event(DAMAGE_MODIFIER_EVENT, TARGET),
                new Equals(Value.MOVE_TYPE, BUG),
                new EffectSequence(List.of(
                    new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5)),
                    new ConsumeTargetItem()
                ))
            )
		);
		register(
			ARCHAIC_SHELL,
			"archaic shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, ANCIENT),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			BLOSSOM_SHELL,
			"blossom shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, PLANT),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			DRAFT_SHELL,
			"draft shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, AIR),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			FLAME_SHELL,
			"flame shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, FIRE),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			FROST_SHELL,
			"frost shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, ICE),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			MARTIAL_SHELL,
			"martial shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, BRAWLER),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			MENTAL_SHELL,
			"mental shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, MIND),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			METALLIC_SHELL,
			"metallic shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, METAL),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			NOXIOUS_SHELL,
			"noxious shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, TOXIC),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			PLAIN_SHELL,
			"plain shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, SIMPLE),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			SHADE_SHELL,
			"shade shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, DARK),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			SHINE_SHELL,
			"shine shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, LIGHT),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			SPECTRAL_SHELL,
			"spectral shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, SPIRIT),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			TERRA_SHELL,
			"terra shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, EARTH),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			VOLT_SHELL,
			"volt shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, ELECTRIC),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			WAVE_SHELL,
			"wave shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, WATER),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			WEB_SHELL,
			"web shell",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, USER),
				new Equals(Value.MOVE_TYPE, BUG),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.5)),
					new ConsumeUserItem()
				))
			)
		);
		register(
			SCRAP_METAL,
			"scrap metal",
			new TriggerRule(
				new Event(DAMAGE_MODIFIER_EVENT, TARGET),
				new Or(List.of(
					new Equals(Value.TARGET_SPECIES_ID, SCORB)
					//new Equals(Value.TARGET_SPECIES_ID, VEYLENS)
				)),
				new EffectSequence(List.of(
					new MultiplyValueEffect(Value.TARGET_BATTLE_MDEFENSE, new Constant(1.5)),
					new MultiplyValueEffect(Value.TARGET_BATTLE_RDEFENSE, new Constant(1.5))
				))
			)
		);
		register(
			SPARE_CABLE,
			"spare cable",
			null
		);
		register(
			CLEAR_LENS,
			"clear lens",
			null
		);
		register(
			FLAME_CORE,
			"flame core",
			null
		);
		register(
			FREEZER_CORE,
			"freezer core",
			null
		);
		register(
			VOLTAIC_CORE,
			"voltaic core",
			null
		);
		register(
			POWER_CORE,
			"power core",
			null
		);
		register(
			QUANTUM_CORE,
			"quantum core",
			null
		);
    }
}
