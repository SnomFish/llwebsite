package github.snomfish.domain.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static github.snomfish.domain.ability.AbilityId.*;


public class AbilityRegistry {
    

    private static final Map<AbilityId, Ability> registry = new HashMap();


    private AbilityRegistry() {}


    public Ability get(AbilityId id) {
        return registry.get(id);
    }


    private static void register(
        AbilityId id,
        String name
    ) {
        Ability ability = new Ability(
            id, 
            name,
            List.of()
        );
        registry.put(id, ability);
    }


    static {
        register(
	        ABILITY_THIEF,
            "ability thief"
        );
        register(
            ADORABLE,
            "adorable"
        );
        register(
            AGGRESSIVE,
            "aggressive"
        );
        register(
            AMBUSH,
            "ambush"
        );
        register(
            ANALYZE,
            "analyze"
        );
        register(
            ANOMALY,
            "anomaly"
        );
        register(
            APPLIED_FRUSTRATIONS,
            "applied frustrations"
        );
        register(
            APPREHENSION,
            "apprehension"
        );
        register(
            AQUA_BODY,
            "aqua body"
        );
        register(
            ASSERTIVE,
            "assertive"
        );
        register(
            AWAKENING,
            "awakening"
        );
        register(
            BANEFUL,
            "baneful"
        );
        register(
            BERSERK,
            "berserk"
        );
        register(
            BITTER_TOUCH,
            "bitter touch"
        );
        register(
            BLISTERING_HEAT,
            "blistering heat"
        );
        register(
            BOAST,
            "boast"
        );
        register(
            BRUTAL_WRATH,
            "brutal wrath"
        );
        register(
            BRUTE_FORCE,
            "brute force"
        );
        register(
            BULLY,
            "bully"
        );
        register(
            BURGLER,
            "burgler"
        );
        register(
            CADDIE,
            "caddie"
        );
        register(
            CAPTIVATING,
            "captivating"
        );
        register(
            CAROL,
            "carol"
        );
        register(
            CHARGED_ARC,
            "charged arc"
        );
        register(
            CHILL,
            "chill"
        );
        register(
            CLINGY,
            "clingy"
        );
        register(
            CLUTCH,
            "clutch"
        );
        register(
            COMBATIVE,
            "combative"
        );
        register(
            COMBUSTIBLE,
            "combustible"
        );
        register(
            COMMUNICATION,
            "communication"
        );
        register(
            COMPLIANT,
            "compliant"
        );
        register(
            CONFIDENCE,
            "confidence"
        );
        register(
            CONSPIRE,
            "conspire"
        );
        register(
            COURSING_VENOM,
            "coursing venom"
        );
        register(
            DEFENSIVE_PRIORITY,
            "defensive priority"
        );
        register(
            DEMANDING,
            "demanding"
        );
        register(
            DESIGNATED_CHOMPERS,
            "designated chompers"
        );
        register(
            DEVIOUS,
            "devious"
        );
        register(
            DISENCHANT,
            "disenchant"
        );
        register(
            DO_OR_DIE,
            "do or die"
        );
        register(
            DRAINAGE,
            "drainage"
        );
        register(
            DRIVEN,
            "driven"
        );
        register(
            ELUSIVE,
            "elusive"
        );
        register(
            ENCHANTED_COAT,
            "enchanted coat"
        );
        register(
            EXPERTISE,
            "expertise"
        );
        register(
            FESTIVE_SPIRIT,
            "festive spirit"
        );
        register(
            FINESSE,
            "finesse"
        );
        register(
            FOG_SUMMON,
            "fog summon"
        );
        register(
            FORTISSIMO,
            "fortissimo"
        );
        register(
            FRAIL_ARMOUR,
            "frail armour"
        );
        register(
            FRENZY,
            "frenzy"
        );
        register(
            GLIDE,
            "glide"
        );
        register(
            GLUTTON,
            "glutton"
        );
        register(
            GUARDIAN,
            "guardian"
        );
        register(
            GUMMY,
            "gummy"
        );
        register(
            HANDY,
            "handy"
        );
        register(
            HARD_CANDY,
            "hard candy"
        );
        register(
            HASTY,
            "hasty"
        );
        register(
            HEAT_SUMMON,
            "heat summon"
        );
        register(
            HOTFOOT,
            "hotfoot"
        );
        register(
            HOVER,
            "hover"
        );
        register(
            HYDRATE,
            "hydrate"
        );
        register(
            HYDRO_VORTEX,
            "hydro vortex"
        );
        register(
            IDIOSYNCRATIC,
            "idiosyncratic"
        );
        register(
            IGNORANT,
            "ignorant"
        );
        register(
            IMMUNIZED,
            "immunized"
        );
        register(
            INCANDESCENT,
            "incandescent"
        );
        register(
            INSULATED,
            "insulated"
        );
        register(
            INTERN,
            "intern"
        );
        register(
            LAZY,
            "lazy"
        );
        register(
            LIFE_FORCE,
            "life force"
        );
        register(
            LIGHTNING_ROD,
            "lightning rod"
        );
        register(
            LUCKY,
            "lucky"
        );
        register(
            MADCAP,
            "madcap"
        );
        register(
            MALWARE,
            "malware"
        );
        register(
            MARKSMAN,
            "marksman"
        );
        register(
            MESMERIZING,
            "mesmerizing"
        );
        register(
            METAMORPHOSIS,
            "metamorphosis"
        );
        register(
            MIMIC,
            "mimic"
        );
        register(
            MOTIVATIONAL,
            "motivational"
        );
        register(
            MYSTERIOUS_CLOAK,
            "mysterious cloak"
        );
        register(
            MYSTERY_TOXINS,
            "mystery toxins"
        );
        register(
            NEUTRALIZE,
            "neutralize"
        );
        register(
            NOXIOUS_WEEDS,
            "noxious weeds"
        );
        register(
            ODD_HUST,
            "odd hust"
        );
        register(
            OVERCHARGED,
            "overcharged"
        );
        register(
            OVERCLOCK,
            "overclock"
        );
        register(
            OXIDIZE,
            "oxidize"
        );
        register(
            PARTING_GIFT,
            "parting gift"
        );
        register(
            PETRIFYING,
            "petrifying"
        );
        register(
            PITCH_BLACK,
            "pitch black"
        );
        register(
            PLAYFUL,
            "playful"
        );
        register(
            PLUVIAL,
            "pluvial"
        );
        register(
            POWER_CLAW,
            "power claw"
        );
        register(
            POWER_JAW,
            "power jaw"
        );
        register(
            POWER_LEGS,
            "power legs"
        );
        register(
            POWER_NAPPER,
            "power napper"
        );
        register(
            PREMONITION,
            "premonition"
        );
        register(
            PRISMATIC,
            "prismatic"  
        );
        register(
            PROTECTIVE_SHELL,
            "protective shell"
        );
        register(
            PROWLER,
            "prowler"
        );
        register(
            PUNCTURE,
            "puncture"
        );
        register(
            PYRO,
            "pyro"
        );
        register(
            QUICK_RECOVERY,
            "quick recovery"
        );
        register(
            RADIANCE,
            "radiance"
        );
        register(
            RAGING_FIRE,
            "raging fire"
        );
        register(
            RAIN_RUSH,
            "rain rush"
        );
        register(
            RAIN_SUMMON,
            "rain summon"
        );
        register(
            RAVENOUS,
            "ravenous"
        );
        register(
            RAZOR_SHARP,
            "razor sharp"
        );
        register(
            RECURRENT,
            "recurrent"
        );
        register(
            REFLECTIVE,
            "reflective"
        );
        register(
            REGIFT,
            "regift"
        );
        register(
            REGURGITATE,
            "regurgitate"
        );
        register(
            REIGN,
            "reign"
        );
        register(
            REPLICATE,
            "replicate"
        );
        register(
            REPUGNANT,
            "repugnant"
        );
        register(
            RESENTFUL,
            "resentful"
        );
        register(
            RESILIENCE,
            "resilience"
        );
        register(
            REV_UP,
            "rev up"
        );
        register(
            RUSH_HOUR,
            "rush hour"
        );
        register(
            SAFETY_POT,
            "safety pot"
        );
        register(
            SALVAGE,
            "salvage"
        );
        register(
            SCORCHING_SKIN,
            "scorching skin"
        );
        register(
            SEIZE,
            "seize"
        );
        register(
            SENDOFF,
            "sendoff"
        );
        register(
            SHAKEDOWN,
            "shakedown"
        );
        register(
            SHARP_CLAWS,
            "sharp claws"
        );
        register(
            SHARP_EDGES,
            "sharp edges"
        );
        register(
            SLIMY,
            "slimy"
        );
        register(
            SLY,
            "sly"
        );
        register(
            SOB,
            "sob"
        );
        register(
            SOUL_SIPHON,
            "soul siphon"
        );
        register(
            SPECIALIZATION,
            "specialization"
        );
        register(
            SPINE_BREAK,
            "spine break"
        );
        register(
            SPONGE,
            "sponge"
        );
        register(
            STAUNCH,
            "staunch"
        );
        register(
            SUGAR_RUSH,
            "sugar rush"
        );
        register(
            SURROGATE,
            "surrogate"
        );
        register(
            SWAMPY,
            "swampy"
        );
        register(
            SWEET_TOUCH,
            "sweet touch"
        );
        register(
            TEMPER,
            "temper"
        );
        register(
            TERRIFYING,
            "terrifying"
        );
        register(
            TERRITORIAL,
            "territorial"
        );
        register(
            THRIVING_PACE,
            "thriving pace"
        );
        register(
            THUNDER_SUMMON,
            "thunder summon"
        );
        register(
            TONE_DEAF,
            "tone deaf"
        );
        register(
            TOXIC_FILTER,
            "toxic filter"
        );
        register(
            TOXIC_SAC,
            "toxic sac"
        );
        register(
            TOXIC_SPINES,
            "toxic spines"
        );
        register(
            TRADER,
            "trader"
        );
        register(
            TRASH_ARMOUR,
            "trash armour"
        );
        register(
            TRIUMPH,
            "triumph"
        );
        register(
            TUMULTUOUS,
            "tumultuous"
        );
        register(
            UNGRACIOUS_HOST,
            "ungracious host"
        );
        register(
            VENGEANCE,
            "vengeance"
        );
        register(
            VENOMOUS,
            "venomous"
        );
        register(
            VICIOUS,
            "vicious"
        );
        register(
            VIGILANT,
            "vigilant"
        );
        register(
            VIGOROUS,
            "vigorous"
        );
        register(
            VIRTUOSO,
            "virtuoso"
        );
        register(
            VISCID,
            "viscid"
        );
        register(
            VIVID_SIGHT,
            "vivid sight"
        );
        register(
            VOLCANIC,
            "volcanic"
        );
        register(
            WATCHER,
            "watcher"
        );
        register(
            WEBBING,
            "webbing"
        );
        register(
            WHOLESOME,
            "wholesome"
        );
        register(
            WILDFIRE,
            "wildfire"
        );
        register(
            WIND_SUMMON,
            "wind summon"
        );
        register(
            WISE,
            "wise"
        );
        register(
            WOODSMAN,
            "woodman"
        );
    }
}
