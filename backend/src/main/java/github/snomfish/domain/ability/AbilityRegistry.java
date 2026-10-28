package github.snomfish.domain.ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.conditions.And;
import github.snomfish.functionality.condition.conditions.Equals;
import github.snomfish.functionality.condition.conditions.GreaterThan;
import github.snomfish.functionality.condition.conditions.ListContains;
import github.snomfish.functionality.condition.conditions.Or;
import github.snomfish.functionality.condition.conditions.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.AlterTypeChart;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
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
    

    private static final Map<AbilityId, Ability> registry = new HashMap<>();


    private AbilityRegistry() {}


    public static Ability get(AbilityId id) {
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
	private static void register(
		AbilityId id,
		String name,
		List<TriggerRule> rules
	) {
		registry.put(id, new Ability(id, name, rules));
	}
	private static void register(AbilityId id, String name) {}


    static {
		register(
			NO_ABILITY,
			"no ability"
		);
        register(
			ABILITY_THIEF,
			"ability thief"
		);
		register(
			ACE,
			"ace",
			new TriggerRule(
                List.of(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)), // right before a moves damage is calculated
                new ConditionalEffect(
					new ListContains(Value.USER_TYPES, Value.MOVE_TYPE), 
					new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(2.0))
				)
            )
		);
		register(
			ADAPTABLE,
			"adaptable"
		);
		register(
			ADORABLE,
			"adorable",
			new TriggerRule(
				new Event(USER_SWITCHED_IN_EVENT, USER),
				ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))
			)
		);
		register(
			AGGRESSIVE,
			"aggressive"
		);
		register(
			ALL_SEEING,
			"all_seeing"
		);
		register(
			AMBUSH,
			"ambush"
		);
		register(
			ANALYZE,
			"analyze",
			List.of(new TriggerRule(
				new Event(USER_SWITCHED_IN_EVENT, USER),
				new ConditionalEffect(
					new Or(
						new GreaterThan(Value.TARGET_BATTLE_MDEFENSE, Value.TARGET_BATTLE_RDEFENSE),
						new And(
							new Equals(Value.TARGET_BATTLE_MDEFENSE, Value.TARGET_BATTLE_RDEFENSE),
							new RandomCondition(new Constant(0.5))
						)
					), 
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1)), 
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
				)
			))
		);
		register( // TODO: contemplate this correct implementation and wonder if I could have done anything differentely to make it better
			ANOMALY,
			"anomaly",
			List.of(
				new TriggerRule( // when the loomian is hte user
					new Event(USER_SWITCHED_IN_EVENT, USER), 
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.USER_MATTACK_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_MDEFENSE_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_RATTACK_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_RDEFENSE_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_SPEED_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_ACCURACY_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.USER_EVASION_STAGE_MODIFIER, new Constant(-1))
					))
				),
				new TriggerRule( // when the loomian is the target
					new Event(TARGET_SWITCHED_IN_EVENT, TARGET), 
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.TARGET_MATTACK_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_MDEFENSE_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_RATTACK_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_RDEFENSE_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_SPEED_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_ACCURACY_STAGE_MODIFIER, new Constant(-1)),
						new MultiplyValueEffect(Value.TARGET_EVASION_STAGE_MODIFIER, new Constant(-1))
					))
				)
			)
		);
		register(
			APPETITE,
			"appetite"
		);
		register(
			APPLIED_FRUSTRATION,
			"applied_frustration",
			List.of(
				new TriggerRule(
					new Event(TARGET_LOWERED_USER_STATS_EVENT, USER),
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(2))	
				),
				new TriggerRule(
					new Event(USER_LOWERED_TARGET_STATS_EVENT, TARGET),
					ApplyStatChanges.targetChangingTarget()
						.rattack(new Constant(2))	
				)
			)
		);
		register(
			APPREHENSION,
			"apprehension"
		);
		register( // this has to use type modifier, you cannot alter the typechart for this as if the target has two types, that fire resistance will be applied twice
            AQUA_BODY,
            "aqua body",
			new TriggerRule(
                List.of(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET)), // before the targets move damage is calculated 
                new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.FIRE),
					new MultiplyValueEffect(Value.MOVE_TYPE_MODIFIER, new Constant(0.5))
				)
            )
        );
        register(
            ASSERTIVE,
            "assertive",
			new TriggerRule(
                List.of(new Event(BEFORE_MOVE_EVENT, USER)), // before anything about the move is actually used
                new AlterTypeChart(BRAWLER, SPIRIT, 1)
            )
        );
        register(
            AWAKENING,
            "awakening",
			new TriggerRule(
                List.of(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)), // right before a moves damage is calculated
                new SetValueEffect(Value.MOVE_STAB_MODIFIER, new Constant(1.5))
            )
        );
        register(
            BANEFUL,
            "baneful",
			new TriggerRule(
                List.of(new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER)), 
				new ConditionalEffect(
					new Or(
						new Equals(Value.USER_STATUS_ID, StatusId.POISON),
						new Equals(Value.USER_STATUS_ID, StatusId.BAD_POISON)
					),
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.2))
				)
            )
        );
		register(
			BATTLE_ARMOUR,
			"battle_armour",
			List.of(
				new TriggerRule(
					new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER), 
					new ConditionalEffect(
						new Equals(Value.MOVE_TYPE, TypeId.METAL),
						new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25))
					)
				)
				// TODO: battle armour protects the user from the secondary effects of moves
			)
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
		register( // Increases the damage of the user's health draining moves
			BLOODSUCKER,
			"bloodsucker",
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
				new ConditionalEffect(
					new Equals(Value.MOVE_HEALTH_DRAIN_TAG, true),
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25))
				)
			)
		);
		register(
			BOAST,
			"boast"
		);
		register(
			BONEHEADED,
			"boneheaded"
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
			BUG_FEVER,
			"bug_fever"
		);
		register(
			BULLY,
			"bully"
		);
		register(
			BURGLAR,
			"burglar"
		);
		register(
			BURGLAR,
			"burgler"
		);
		register(
			BURNING_RAGE,
			"burning_rage"
		);
		register(
			BURSTING_SEAMS,
			"bursting_seams"
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
			CHILLING_PASSION,
			"chilling_passion"
		);
		register(
			CIRCADIAN,
			"circadian"
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
			"combative",
			List.of(
				new TriggerRule(
					new Event(TARGET_LOWERED_USER_STATS_EVENT, USER),
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(2))
				),
				new TriggerRule(
					new Event(USER_LOWERED_TARGET_STATS_EVENT, TARGET),
					ApplyStatChanges.targetChangingTarget()
						.mattack(new Constant(2))	
				)
			)
		);
		register(
			COMBUSTIBLE,
			"combustible",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.FIRE),
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.COMBUSTIBLE)
					))
				)
			)
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
			COSMIC_PRESSURE,
			"cosmic_pressure"
		);
		register(
			COURSING_VENOM, // when hit by a toxic type move, absorb it to boost your own toxic type damage by 1.5x
			"coursing venom",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.TOXIC),
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.COURSING_VENOM)
					))
				)
			)
		);
		register(
			DAUNTLESS,
			"dauntless"
		);
		register(
			DAWN,
			"dawn"
		);
		register(
			DEEP_FROSTBITE,
			"deep_frostbite"
		);
		register(
			DEFENSIVE_PRIORITY,
			"defensive priority",
			List.of(
				new TriggerRule(
					new Event(USER_SWITCHED_IN_EVENT, USER),
					ApplyStatChanges.userChangingUser()
						.mdefense(new Constant(1))
						.rdefense(new Constant(1))
						.speed(new Constant(-2))	
				),
				new TriggerRule(
					new Event(TARGET_SWITCHED_IN_EVENT, TARGET),
					ApplyStatChanges.targetChangingTarget()
						.mdefense(new Constant(1))
						.rdefense(new Constant(1))
						.speed(new Constant(-2))	
				)
			)
		);
		register(
			DEMANDING,
			"demanding"
		);
		register(
			DEPTHS_BORNE,
			"depths_borne",
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.WATER),
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(1.25))
				)
			)
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
			DOUBLE_STRIKE,
			"double_strike"
		);
		register(
            DRAINAGE,
            "drainage",
			new TriggerRule(
                new Event(HEALTH_DRAIN_EVENT, USER), 
                new MultiplyValueEffect(Value.MOVE_HEALTH_DRAIN_MODIFIER, new Constant(1.5)) 
            )
        );
		register(
			DRIVEN,
			"driven"
		);
		register(
			DUSK,
			"dusk"
		);
		register(
			EAGER,
			"eager"
		);
		register(
			EFFULGENT,
			"effulgent"
		);
		register(
			ELUSIVE,
			"elusive"
		);
		register(
			ENCHANT,
			"enchant"
		);
		register(
			ENCHANTED_COAT,
			"enchanted coat",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new GreaterThan(Value.MOVE_TYPE_MODIFIER, new Constant(1)),
					new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.75))
				)
			)
		);
		register(
			EXPERTISE,
			"expertise"
		);
		register(
			FACADE,
			"facade"
		);
		register(
			FANNING_FLAME,
			"fanning_flame",
			List.of(
				new TriggerRule(
					new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
					new ConditionalEffect(
						new Equals(Value.MOVE_TYPE, TypeId.AIR),
						new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5))
					)
				)// TODO: add +1 ranged attack when hit
			)
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
			FLUTTER,
			"flutter"
		);
		register(
			FOG_SUMMON,
			"fog summon"
		);
		register(
			FORESIGHT,
			"foresight"
		);
		register(
			FORGE,
			"forge",
			List.of(
				new TriggerRule(
					new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
					new ConditionalEffect(
						new Equals(Value.MOVE_TYPE, TypeId.FIRE),
						new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0.5))
					)
				)
			)
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
			GLOOMY,
			"gloomy"
		);
		register(
			GLUTTON,
			"glutton"
		);
		register(
			GORGE,
			"gorge"
		);
		register( // doubles exclusive ability
			GUARDIAN,
			"guardian"
		);
		register( // immunity to bomb moves
			GUMMY,
			"gummy",
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_EXPLOSIVE_TAG, true),
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0))
				)
			)
		);
		register( // technician from pokemon
			GURU,
			"guru",
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_EXPLOSIVE_TAG, true),
					new MultiplyValueEffect(Value.MOVE_DAMAGE_MODIFIER, new Constant(0))
				)
			)
		);
		register(
			HAG,
			"hag"
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
			HARMONIZE,
			"harmonize"
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
			HEAVY_FISTS,
			"heavy_fists"
		);
		register(
			HERD_BEHAVIOR,
			"herd_behavior"
		);
		register(
			HIGH_EXPLOSIVE,
			"high_explosive"
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
			ILLUMINATE,
			"illuminate"
		);
		register(
			IMMUNIZED,
			"immunized"
		);
		register(
			IMPOSE,
			"impose"
		);
		register(
			INCANDESCENT,
			"incandescent"
		);
		register(
			INFERNO,
			"inferno"
		);
		register(
			INSULATED,
			"insulated"
		);
		register(
			INTENSIFY,
			"intensify"
		);
		register(
			INTERN,
			"intern"
		);
		register(
			LAND_BORNE,
			"land_borne"
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
			LUCK_OF_THE_SEA,
			"luck_of_the_sea",
			// also needs to increase crit chance
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
				new MultiplyValueEffect(Value.MOVE_CRIT_MODIFIER, new Constant(1.5)) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
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
			"marksman",
			new TriggerRule(
				new Event(PRE_DEAL_FORMULA_DAMAGE_EVENT, USER),
				new MultiplyValueEffect(Value.MOVE_CRIT_MODIFIER, new Constant(1.5)) // generic crit modifier = 1.5 and is always applied, so a 1.5 boost brings it to 2.25
			)
		);
		register(
			MASK_SWAP,
			"mask_swap"
		);
		register(
			MASTER,
			"master"
		);
		register(
			MEAN_SPIRITED,
			"mean_spirited"
		);
		register(
			MEDIC,
			"medic"
		);
		register(
			MESMERIZING,
			"mesmerizing"
		);
		register(
			METALLIC,
			"metallic"
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
			MYCOTIC,
			"mycotic"
		);
		register(
			MYSTERIOUS_CLOAK,
			"mysterious cloak"
		);
		register(
			MYSTERIOUS_TOXINS,
			"mysterious_toxins"
		);
		register(
			MYSTERY_TOXINS,
			"mystery toxins"
		);
		register(
			MYSTIC_TONE,
			"mystic_tone"
		);
		register(
			NEUTRALIZE,
			"neutralize"
		);
		register(
			NIGHT_LIGHT,
			"night_light"
		);
		register(
			NIGHTMARISH,
			"nightmarish"
		);
		register(
			NOXIOUS_WEEDS,
			"noxious weeds",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.PLANT),
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.NOXIOUS_WEEDS)
					))
				)
			)
		);
		register(
			OBSIDIAN_HEART,
			"obsidian_heart"
		);
		register(
			ODD_HUSK,
			"odd_husk"
		);
		register(
			ONE_OF_MANY,
			"one_of_many"
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
			OVERSHADOW,
			"overshadow"
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
			PARTNERSHIP,
			"partnership"
		);
		register(
			PERSISTENCE,
			"persistence"
		);
		register(
			PETRIFYING,
			"petrifying"
		);
		register(
			PINCER_TRAP,
			"pincer_trap"
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
			"prismatic",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.LIGHT),
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.PRISMATIC)
					))
				)
			)
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
			PYR_PRO,
			"pyr_pro"
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
			RAIL_GUN,
			"rail_gun"
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
			REAPER,
			"reaper"
		);
		register(
			RECHARGEABLE,
			"rechargeable"
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
			REMORSELESS,
			"remorseless"
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
			REVERBERATE,
			"reverberate"
		);
		register(
			ROYAL_DECREE,
			"royal_decree"
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
			SHARP_FOCUS,
			"sharp_focus"
		);
		register(
			SKY_BORNE,
			"sky_borne"
		);
		register(
			SLICK_SHELL,
			"slick_shell"
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
			SWEET_AROMA,
			"sweet_aroma"
		);
		register(
			SWEET_TOUCH,
			"sweet touch"
		);
		register(
			TANK,
			"tank"
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
			THIRD_DEGREE_BURN,
			"third_degree_burn"
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
			TOTAL_ECLIPSE,
			"total_eclipse"
		);
		register(
			TOXIC_FILTER,
			"toxic filter",
			new TriggerRule(
				new Event(PRE_DAMAGE_EVENT, TARGET),
				new ConditionalEffect(
					new Equals(Value.MOVE_TYPE, TypeId.TOXIC),
					new EffectSequence(List.of(
						new MultiplyValueEffect(Value.MOVE_DAMAGE, new Constant(0.0)),
						ApplyMechanic.toUser(MechanicId.TOXIC_FILTER)
					))
				)
			)
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
			TOXIC_TOUCH,
			"toxic_touch"
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
			TURBULENT,
			"turbulent"
		);
		register(
			TWO_FACE,
			"two_face"
		);
		register(
			UNGRACIOUS_HOST,
			"ungracious host"
		);
		register(
			UPPER_HAND,
			"upper_hand"
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
