package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.ListContains;
import github.snomfish.functionality.condition.NoCondition;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.effect.effects.SetValueEffect;
import github.snomfish.functionality.event.Event;
import github.snomfish.functionality.event.TriggerRule;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.domain.type.TypeId.*;
import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.event.EventSideId.*;

public class AbilityRegistry {
    

	private static boolean initialised = false;
    private static final Map<AbilityId, Ability> registry = new HashMap<>();


    private AbilityRegistry() {}


    public static Ability get(AbilityId id) {
		if (!initialised) throw new IllegalArgumentException("Abilities has not been initialised");
        return registry.get(id);
    }


    private static void register(
        AbilityId id,
        String name,
        TriggerRule rule
    ) {
		if (rule == null) {
			registry.put(id, new Ability(id, name, new ArrayList<TriggerRule>()));
			return;
		}
        registry.put(id, new Ability(id, name, List.of(rule)));
    }


    public static void init() {
		if (initialised) return;
		initialised = true;
		register(
			NO_ABILITY,
			"no ability",
			null
		);
        register(
			ABILITY_THIEF,
			"ability thief",
			null
		);
		register(
			ACE,
			"ace",
			new TriggerRule(
                List.of(new Event(DAMAGE_MODIFIER_EVENT, USER)), // right before a moves damage is calculated
                new ListContains(Value.USER_TYPES, Value.MOVE_TYPE),
                new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(2.0))
            )
		);
		register(
			ADAPTABLE,
			"adaptable",
			null
		);
		register(
			ADORABLE,
			"adorable",
			null
		);
		register(
			AGGRESSIVE,
			"aggressive",
			null
		);
		register(
			ALL_SEEING,
			"all_seeing",
			null
		);
		register(
			AMBUSH,
			"ambush",
			null
		);
		register(
			ANALYZE,
			"analyze",
			null
		);
		register(
			ANOMALY,
			"anomaly",
			null
		);
		register(
			APPETITE,
			"appetite",
			null
		);
		register(
			APPLIED_FRUSTRATION,
			"applied_frustration",
			null
		);
		register(
			APPLIED_FRUSTRATIONS,
			"applied frustrations",
			null
		);
		register(
			APPREHENSION,
			"apprehension",
			null
		);
		register( // this has to use type modifier, you cannot alter the typechart for this as if the target has two types, that fire resistance will be applied twice
            AQUA_BODY,
            "aqua body",
			new TriggerRule(
                List.of(new Event(DAMAGE_MODIFIER_EVENT, TARGET)), // before the targets move damage is calculated 
                new Equals(Value.MOVE_TYPE, TypeId.FIRE), 
                new MultiplyValueEffect(Value.MOVE_TYPE_MODIFIER, new Constant(0.5))
            )
        );
        register(
            ASSERTIVE,
            "assertive",
			new TriggerRule(
                List.of(new Event(BEFORE_MOVE_EVENT, USER)), // before anything about the move is actually used
                new NoCondition(), 
                new AlterTypeChart(BRAWLER, SPIRIT, 1)
            )
        );
        register(
            AWAKENING,
            "awakening",
			new TriggerRule(
                List.of(new Event(DAMAGE_MODIFIER_EVENT, USER)), // right before a moves damage is calculated
                new ListContains(Value.USER_TYPES, Value.MOVE_TYPE),
                new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(1.5))
            )
        );
        register(
            BANEFUL,
            "baneful",
			new TriggerRule(
                List.of(new Event(DAMAGE_MODIFIER_EVENT, USER)), 
                new Or(List.of(
                    new Equals(Value.USER_STATUS_ID, StatusId.POISON),
                    new Equals(Value.USER_STATUS_ID, StatusId.BAD_POISON)
                )),
                new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
        );
		register(
			BATTLE_ARMOUR,
			"battle_armour",
			null
		);
		register(
			BERSERK,
			"berserk",
			null
		);
		register(
			BITTER_TOUCH,
			"bitter touch",
			null
		);
		register(
			BLISTERING_HEAT,
			"blistering heat",
			null
		);
		register(
			BLOODSUCKER,
			"bloodsucker",
			null
		);
		register(
			BOAST,
			"boast",
			null
		);
		register(
			BONEHEADED,
			"boneheaded",
			null
		);
		register(
			BRUTAL_WRATH,
			"brutal wrath",
			null
		);
		register(
			BRUTE_FORCE,
			"brute force",
			null
		);
		register(
			BUG_FEVER,
			"bug_fever",
			null
		);
		register(
			BULLY,
			"bully",
			null
		);
		register(
			BURGLAR,
			"burglar",
			null
		);
		register(
			BURGLAR,
			"burgler",
			null
		);
		register(
			BURNING_RAGE,
			"burning_rage",
			null
		);
		register(
			BURSTING_SEAMS,
			"bursting_seams",
			null
		);
		register(
			CADDIE,
			"caddie",
			null
		);
		register(
			CAPTIVATING,
			"captivating",
			null
		);
		register(
			CAROL,
			"carol",
			null
		);
		register(
			CHARGED_ARC,
			"charged arc",
			null
		);
		register(
			CHILL,
			"chill",
			null
		);
		register(
			CHILLING_PASSION,
			"chilling_passion",
			null
		);
		register(
			CIRCADIAN,
			"circadian",
			null
		);
		register(
			CLINGY,
			"clingy",
			null
		);
		register(
			CLUTCH,
			"clutch",
			null
		);
		register(
			COMBATIVE,
			"combative",
			null
		);
		register(
			COMBUSTIBLE,
			"combustible",
			null
		);
		register(
			COMMUNICATION,
			"communication",
			null
		);
		register(
			COMPLIANT,
			"compliant",
			null
		);
		register(
			CONFIDENCE,
			"confidence",
			null
		);
		register(
			CONSPIRE,
			"conspire",
			null
		);
		register(
			COSMIC_PRESSURE,
			"cosmic_pressure",
			null
		);
		register(
			COURSING_VENOM,
			"coursing venom",
			null
		);
		register(
			DAUNTLESS,
			"dauntless",
			null
		);
		register(
			DAWN,
			"dawn",
			null
		);
		register(
			DEEP_FROSTBITE,
			"deep_frostbite",
			null
		);
		register(
			DEFENSIVE_PRIORITY,
			"defensive priority",
			null
		);
		register(
			DEMANDING,
			"demanding",
			null
		);
		register(
			DEPTHS_BORNE,
			"depths_borne",
			null
		);
		register(
			DESIGNATED_CHOMPERS,
			"designated chompers",
			null
		);
		register(
			DEVIOUS,
			"devious",
			null
		);
		register(
			DISENCHANT,
			"disenchant",
			null
		);
		register(
			DO_OR_DIE,
			"do or die",
			null
		);
		register(
			DOUBLE_STRIKE,
			"double_strike",
			null
		);
		register(
            DRAINAGE,
            "drainage",
			new TriggerRule(
                new Event(HEALTH_DRAIN_EVENT, USER), 
                new NoCondition(),
                new MultiplyValueEffect(Value.MOVE_HEALTH_DRAIN_MODIFIER, new Constant(1.5)) 
            )
        );
		register(
			DRIVEN,
			"driven",
			null
		);
		register(
			DUSK,
			"dusk",
			null
		);
		register(
			EAGER,
			"eager",
			null
		);
		register(
			EFFULGENT,
			"effulgent",
			null
		);
		register(
			ELUSIVE,
			"elusive",
			null
		);
		register(
			ENCHANT,
			"enchant",
			null
		);
		register(
			ENCHANTED_COAT,
			"enchanted coat",
			null
		);
		register(
			EXPERTISE,
			"expertise",
			null
		);
		register(
			FACADE,
			"facade",
			null
		);
		register(
			FANNING_FLAME,
			"fanning_flame",
			null
		);
		register(
			FESTIVE_SPIRIT,
			"festive spirit",
			null
		);
		register(
			FINESSE,
			"finesse",
			null
		);
		register(
			FLUTTER,
			"flutter",
			null
		);
		register(
			FOG_SUMMON,
			"fog summon",
			null
		);
		register(
			FORESIGHT,
			"foresight",
			null
		);
		register(
			FORGE,
			"forge",
			null
		);
		register(
			FORTISSIMO,
			"fortissimo",
			null
		);
		register(
			FRAIL_ARMOUR,
			"frail armour",
			null
		);
		register(
			FRENZY,
			"frenzy",
			null
		);
		register(
			GLIDE,
			"glide",
			null
		);
		register(
			GLOOMY,
			"gloomy",
			null
		);
		register(
			GLUTTON,
			"glutton",
			null
		);
		register(
			GORGE,
			"gorge",
			null
		);
		register(
			GUARDIAN,
			"guardian",
			null
		);
		register(
			GUMMY,
			"gummy",
			null
		);
		register(
			GURU,
			"guru",
			null
		);
		register(
			HAG,
			"hag",
			null
		);
		register(
			HANDY,
			"handy",
			null
		);
		register(
			HARD_CANDY,
			"hard candy",
			null
		);
		register(
			HARMONIZE,
			"harmonize",
			null
		);
		register(
			HASTY,
			"hasty",
			null
		);
		register(
			HEAT_SUMMON,
			"heat summon",
			null
		);
		register(
			HEAVY_FISTS,
			"heavy_fists",
			null
		);
		register(
			HERD_BEHAVIOR,
			"herd_behavior",
			null
		);
		register(
			HIGH_EXPLOSIVE,
			"high_explosive",
			null
		);
		register(
			HOTFOOT,
			"hotfoot",
			null
		);
		register(
			HOVER,
			"hover",
			null
		);
		register(
			HYDRATE,
			"hydrate",
			null
		);
		register(
			HYDRO_VORTEX,
			"hydro vortex",
			null
		);
		register(
			IDIOSYNCRATIC,
			"idiosyncratic",
			null
		);
		register(
			IGNORANT,
			"ignorant",
			null
		);
		register(
			ILLUMINATE,
			"illuminate",
			null
		);
		register(
			IMMUNIZED,
			"immunized",
			null
		);
		register(
			IMPOSE,
			"impose",
			null
		);
		register(
			INCANDESCENT,
			"incandescent",
			null
		);
		register(
			INFERNO,
			"inferno",
			null
		);
		register(
			INSULATED,
			"insulated",
			null
		);
		register(
			INTENSIFY,
			"intensify",
			null
		);
		register(
			INTERN,
			"intern",
			null
		);
		register(
			LAND_BORNE,
			"land_borne",
			null
		);
		register(
			LAZY,
			"lazy",
			null
		);
		register(
			LIFE_FORCE,
			"life force",
			null
		);
		register(
			LIGHTNING_ROD,
			"lightning rod",
			null
		);
		register(
			LUCK_OF_THE_SEA,
			"luck_of_the_sea",
			null
		);
		register(
			LUCKY,
			"lucky",
			null
		);
		register(
			MADCAP,
			"madcap",
			null
		);
		register(
			MALWARE,
			"malware",
			null
		);
		register(
			MARKSMAN,
			"marksman",
			null
		);
		register(
			MASK_SWAP,
			"mask_swap",
			null
		);
		register(
			MASTER,
			"master",
			null
		);
		register(
			MEAN_SPIRITED,
			"mean_spirited",
			null
		);
		register(
			MEDIC,
			"medic",
			null
		);
		register(
			MESMERIZING,
			"mesmerizing",
			null
		);
		register(
			METALLIC,
			"metallic",
			null
		);
		register(
			METAMORPHOSIS,
			"metamorphosis",
			null
		);
		register(
			MIMIC,
			"mimic",
			null
		);
		register(
			MOTIVATIONAL,
			"motivational",
			null
		);
		register(
			MYCOTIC,
			"mycotic",
			null
		);
		register(
			MYSTERIOUS_CLOAK,
			"mysterious cloak",
			null
		);
		register(
			MYSTERIOUS_TOXINS,
			"mysterious_toxins",
			null
		);
		register(
			MYSTERY_TOXINS,
			"mystery toxins",
			null
		);
		register(
			MYSTIC_TONE,
			"mystic_tone",
			null
		);
		register(
			NEUTRALIZE,
			"neutralize",
			null
		);
		register(
			NIGHT_LIGHT,
			"night_light",
			null
		);
		register(
			NIGHTMARISH,
			"nightmarish",
			null
		);
		register(
			NOXIOUS_WEEDS,
			"noxious weeds",
			null
		);
		register(
			OBSIDIAN_HEART,
			"obsidian_heart",
			null
		);
		register(
			ODD_HUSK,
			"odd_husk",
			null
		);
		register(
			ODD_HUST,
			"odd hust",
			null
		);
		register(
			ONE_OF_MANY,
			"one_of_many",
			null
		);
		register(
			OVERCHARGED,
			"overcharged",
			null
		);
		register(
			OVERCLOCK,
			"overclock",
			null
		);
		register(
			OVERSHADOW,
			"overshadow",
			null
		);
		register(
			OXIDIZE,
			"oxidize",
			null
		);
		register(
			PARTING_GIFT,
			"parting gift",
			null
		);
		register(
			PARTNERSHIP,
			"partnership",
			null
		);
		register(
			PERSISTENCE,
			"persistence",
			null
		);
		register(
			PETRIFYING,
			"petrifying",
			null
		);
		register(
			PINCER_TRAP,
			"pincer_trap",
			null
		);
		register(
			PITCH_BLACK,
			"pitch black",
			null
		);
		register(
			PLAYFUL,
			"playful",
			null
		);
		register(
			PLUVIAL,
			"pluvial",
			null
		);
		register(
			POWER_CLAW,
			"power claw",
			null
		);
		register(
			POWER_JAW,
			"power jaw",
			null
		);
		register(
			POWER_LEGS,
			"power legs",
			null
		);
		register(
			POWER_NAPPER,
			"power napper",
			null
		);
		register(
			PREMONITION,
			"premonition",
			null
		);
		register(
			PRISMATIC,
			"prismatic",
			null
		);
		register(
			PROTECTIVE_SHELL,
			"protective shell",
			null
		);
		register(
			PROWLER,
			"prowler",
			null
		);
		register(
			PUNCTURE,
			"puncture",
			null
		);
		register(
			PYR_PRO,
			"pyr_pro",
			null
		);
		register(
			PYRO,
			"pyro",
			null
		);
		register(
			QUICK_RECOVERY,
			"quick recovery",
			null
		);
		register(
			RADIANCE,
			"radiance",
			null
		);
		register(
			RAGING_FIRE,
			"raging fire",
			null
		);
		register(
			RAIL_GUN,
			"rail_gun",
			null
		);
		register(
			RAIN_RUSH,
			"rain rush",
			null
		);
		register(
			RAIN_SUMMON,
			"rain summon",
			null
		);
		register(
			RAVENOUS,
			"ravenous",
			null
		);
		register(
			RAZOR_SHARP,
			"razor sharp",
			null
		);
		register(
			REAPER,
			"reaper",
			null
		);
		register(
			RECHARGEABLE,
			"rechargeable",
			null
		);
		register(
			RECURRENT,
			"recurrent",
			null
		);
		register(
			REFLECTIVE,
			"reflective",
			null
		);
		register(
			REGIFT,
			"regift",
			null
		);
		register(
			REGURGITATE,
			"regurgitate",
			null
		);
		register(
			REIGN,
			"reign",
			null
		);
		register(
			REMORSELESS,
			"remorseless",
			null
		);
		register(
			REPLICATE,
			"replicate",
			null
		);
		register(
			REPUGNANT,
			"repugnant",
			null
		);
		register(
			RESENTFUL,
			"resentful",
			null
		);
		register(
			RESILIENCE,
			"resilience",
			null
		);
		register(
			REV_UP,
			"rev up",
			null
		);
		register(
			REVERBERATE,
			"reverberate",
			null
		);
		register(
			ROYAL_DECREE,
			"royal_decree",
			null
		);
		register(
			RUSH_HOUR,
			"rush hour",
			null
		);
		register(
			SAFETY_POT,
			"safety pot",
			null
		);
		register(
			SALVAGE,
			"salvage",
			null
		);
		register(
			SCORCHING_SKIN,
			"scorching skin",
			null
		);
		register(
			SEIZE,
			"seize",
			null
		);
		register(
			SENDOFF,
			"sendoff",
			null
		);
		register(
			SHAKEDOWN,
			"shakedown",
			null
		);
		register(
			SHARP_CLAWS,
			"sharp claws",
			null
		);
		register(
			SHARP_EDGES,
			"sharp edges",
			null
		);
		register(
			SHARP_FOCUS,
			"sharp_focus",
			null
		);
		register(
			SKY_BORNE,
			"sky_borne",
			null
		);
		register(
			SLICK_SHELL,
			"slick_shell",
			null
		);
		register(
			SLIMY,
			"slimy",
			null
		);
		register(
			SLY,
			"sly",
			null
		);
		register(
			SOB,
			"sob",
			null
		);
		register(
			SOUL_SIPHON,
			"soul siphon",
			null
		);
		register(
			SPECIALIZATION,
			"specialization",
			null
		);
		register(
			SPINE_BREAK,
			"spine break",
			null
		);
		register(
			SPONGE,
			"sponge",
			null
		);
		register(
			STAUNCH,
			"staunch",
			null
		);
		register(
			SUGAR_RUSH,
			"sugar rush",
			null
		);
		register(
			SURROGATE,
			"surrogate",
			null
		);
		register(
			SWAMPY,
			"swampy",
			null
		);
		register(
			SWEET_AROMA,
			"sweet_aroma",
			null
		);
		register(
			SWEET_TOUCH,
			"sweet touch",
			null
		);
		register(
			TANK,
			"tank",
			null
		);
		register(
			TEMPER,
			"temper",
			null
		);
		register(
			TERRIFYING,
			"terrifying",
			null
		);
		register(
			TERRITORIAL,
			"territorial",
			null
		);
		register(
			THIRD_DEGREE_BURN,
			"third_degree_burn",
			null
		);
		register(
			THRIVING_PACE,
			"thriving pace",
			null
		);
		register(
			THUNDER_SUMMON,
			"thunder summon",
			null
		);
		register(
			TONE_DEAF,
			"tone deaf",
			null
		);
		register(
			TOTAL_ECLIPSE,
			"total_eclipse",
			null
		);
		register(
			TOXIC_FILTER,
			"toxic filter",
			null
		);
		register(
			TOXIC_SAC,
			"toxic sac",
			null
		);
		register(
			TOXIC_SPINES,
			"toxic spines",
			null
		);
		register(
			TOXIC_TOUCH,
			"toxic_touch",
			null
		);
		register(
			TRADER,
			"trader",
			null
		);
		register(
			TRASH_ARMOUR,
			"trash armour",
			null
		);
		register(
			TRIUMPH,
			"triumph",
			null
		);
		register(
			TUMULTUOUS,
			"tumultuous",
			null
		);
		register(
			TURBULENT,
			"turbulent",
			null
		);
		register(
			TWO_FACE,
			"two_face",
			null
		);
		register(
			UNGRACIOUS_HOST,
			"ungracious host",
			null
		);
		register(
			UPPER_HAND,
			"upper_hand",
			null
		);
		register(
			VENGEANCE,
			"vengeance",
			null
		);
		register(
			VENOMOUS,
			"venomous",
			null
		);
		register(
			VICIOUS,
			"vicious",
			null
		);
		register(
			VIGILANT,
			"vigilant",
			null
		);
		register(
			VIGOROUS,
			"vigorous",
			null
		);
		register(
			VIRTUOSO,
			"virtuoso",
			null
		);
		register(
			VISCID,
			"viscid",
			null
		);
		register(
			VIVID_SIGHT,
			"vivid sight",
			null
		);
		register(
			VOLCANIC,
			"volcanic",
			null
		);
		register(
			WATCHER,
			"watcher",
			null
		);
		register(
			WEBBING,
			"webbing",
			null
		);
		register(
			WHOLESOME,
			"wholesome",
			null
		);
		register(
			WILDFIRE,
			"wildfire",
			null
		);
		register(
			WIND_SUMMON,
			"wind summon",
			null
		);
		register(
			WISE,
			"wise",
			null
		);
		register(
			WOODSMAN,
			"woodman",
			null
		);
    }
}
