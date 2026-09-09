package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.ListContains;
import github.snomfish.functionality.condition.Or;
import github.snomfish.functionality.effect.effects.MultiplyValueEffect;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.functionality.event.EventId.*;
import static github.snomfish.functionality.Value.*;

public class AbilityRegistry {
    

    private static final Map<AbilityId, Ability> registry = new HashMap();


    private AbilityRegistry() {}


    public Ability get(AbilityId id) {
        return registry.get(id);
    }


    private static void register(
        AbilityId id,
        String name,
        AbilityRule rule
    ) {
        registry.put(id, new Ability(id, name, List.of(rule)));
    }


    static {
        register(
	        ABILITY_THIEF,
            "ability thief",
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
            APPLIED_FRUSTRATIONS,
            "applied frustrations",
			null
        );
        register(
            APPREHENSION,
            "apprehension",
			null
        );
        register(
            AQUA_BODY,
            "aqua body",
			null
        );
        register(
            ASSERTIVE,
            "assertive",
			null
        );
        register(
            AWAKENING,
            "awakening",
			new AbilityRule(
                List.of(BEFORE_MOVE_EVENT),
                new ListContains(Value.USER_TYPES, Value.MOVE_TYPE),
                new MultiplyValueEffect(MOVE_DAMAGE_MODIFIER, new Constant(1.2)) // assumes regular stab has been applied, this brings the stab boost from 1.25 to 1.5
            )
        );
        register(
            BANEFUL,
            "baneful",
			new AbilityRule(
                List.of(USER_DAMAGE_MODIFIER_EVENT), 
                new Or(List.of(
                    new Equals(Value.USER_STATUS_ID, StatusId.POISON),
                    new Equals(Value.USER_STATUS_ID, StatusId.BAD_POISON)
                )),
                new MultiplyValueEffect(MOVE_DAMAGE_MODIFIER, new Constant(1.2))
            )
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
            BOAST,
            "boast",
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
            BULLY,
            "bully",
			null
        );
        register(
            BURGLER,
            "burgler",
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
            COURSING_VENOM,
            "coursing venom",
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
            DRAINAGE,
            "drainage",
			null
        );
        register(
            DRIVEN,
            "driven",
			null
        );
        register(
            ELUSIVE,
            "elusive",
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
            FOG_SUMMON,
            "fog summon",
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
            GLUTTON,
            "glutton",
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
            IMMUNIZED,
            "immunized",
			null
        );
        register(
            INCANDESCENT,
            "incandescent",
			null
        );
        register(
            INSULATED,
            "insulated",
			null
        );
        register(
            INTERN,
            "intern",
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
            MESMERIZING,
            "mesmerizing",
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
            MYSTERIOUS_CLOAK,
            "mysterious cloak",
			null
        );
        register(
            MYSTERY_TOXINS,
            "mystery toxins",
			null
        );
        register(
            NEUTRALIZE,
            "neutralize",
			null
        );
        register(
            NOXIOUS_WEEDS,
            "noxious weeds",
			null
        );
        register(
            ODD_HUST,
            "odd hust",
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
            PETRIFYING,
            "petrifying",
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
            SWEET_TOUCH,
            "sweet touch",
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
            UNGRACIOUS_HOST,
            "ungracious host",
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
