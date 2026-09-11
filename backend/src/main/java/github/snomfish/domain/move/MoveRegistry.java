package github.snomfish.domain.move;

import java.util.HashMap;
import java.util.Map;

import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.condition.ICondition;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.INumber;

import static github.snomfish.domain.move.CategoryId.*;
import static github.snomfish.domain.type.TypeId.*;

public class MoveRegistry {


    private static final Map<MoveId, Move> registry = new HashMap<>();


    private MoveRegistry() {}


    public static Move get(MoveId id) {
		return registry.get(id);
    } 


	private static void register(Move move) {
		registry.put(id, move);
	}


    static {
		register(
			MoveId.ACCELERATE,
			"Accerlerate",
			null,
			SIMPLE,
			STATUS,
			new Constant(25),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AIM,
			"Aim",
			null,
			SIMPLE,
			STATUS,
			new Constant(10),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AIR_BLADE,
			"Air Blade",
			null,
			AIR,
			RANGED,
			new Constant(40),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AIR_SHOT,
			"Air Shot",
			null,
			AIR,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AIR_WHIP,
			"Air Whip",
			null,
			AIR,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AQUA_JAWS,
			"Aqua Jaws",
			null,
			WATER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.AUGMENT,
			"Augment",
			null,
			ANCIENT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BAFFLE,
			"Baffle",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BAG_OF_TRICKS,
			"Bag of Tricks",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BAIT,
			"Bait",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(2),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BAMBOOZLE,
			"Bamboozle",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BANANA_SPLIT,
			"Banana Split",
			null,
			SIMPLE,
			STATUS,
			new Constant(100),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BANE_OF_HASTE,
			"Bane of Haste",
			null,
			ANCIENT,
			STATUS,
			new Constant(0),
			new Constant(-7),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BANEFUL_BASH,
			"Baneful Bash",
			null,
			TOXIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BARBS,
			"Barbs",
			null,
			METAL,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BARTER,
			"Barter",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BASH,
			"Bash",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BATTERING_RAM,
			"Battering Ram",
			null,
			BUG,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BATTLE_CHIME,
			"Battle Chime",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BATTLE_HORN,
			"Battle Horn",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BEQUEATH,
			"Bequeath",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(-6),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BITTER_COLD,
			"Bitter Cold",
			null,
			ICE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BLAZE_CHOMP,
			"Blaze Chomp",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BLAZE_OF_GLORY,
			"Blaze of Glory",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BLAZE_RUSH,
			"Blaze Rush",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BLINDSPOT_BATTER,
			"Blindspot Batter",
			null,
			LIGHT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BLUDGEON,
			"Bludgeon",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BODY_CRASH,
			"Body Crash",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BODY_SLAM,
			"Body Slam",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BOILING_PRESS,
			"Boiling Press",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BOOM_BASH,
			"Boom Bash",
			null,
			BRAWLER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BOULDER_BLAST,
			"Boulder Blast",
			null,
			BRAWLER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BRACE,
			"Brace",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BRAIN_EXERCISE,
			"Brain Exercise",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BRAINWASH,
			"Brainwash",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BRAWN_BOOST,
			"Brawn Boost",
			null,
			BRAWLER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BRIAR_BLOCK,
			"Briar Block",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(4),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BUCK_UP,
			"Buck Up",
			null,
			BRAWLER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BUG_BITE,
			"Bug Bite",
			null,
			BUG,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BULK_UP,
			"Bulk Up",
			null,
			BRAWLER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BURN_UP,
			"Burn Up",
			null,
			FIRE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BURROW,
			"Burrow",
			null,
			EARTH,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.BUSH_WHACK,
			"Bush Whack",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CANISTER_BURST,
			"Canister Burst",
			null,
			TOXIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CEREBRAL_SLASH,
			"Cerebral Slash",
			null,
			MIND,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHARADE,
			"Charade",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHARRED_CLAWS,
			"Charred Claws",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHASE_DOWN,
			"Chase Down",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0), // THIS NEED TO BE CHANGED THIS IS VERY IMPORTANT TO THE EFFECTS OF CHASE DOWWWWWWWWWWWN
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHILLY_CHOMP,
			"Chilly Chomp",
			null,
			ICE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHOMP,
			"Chomp",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CHOP,
			"Chop",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CLAMP,
			"Clamp",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CLAY_SLAP,
			"Clay Slap",
			null,
			EARTH,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CLIMATE_CANNON,
			"Climate Cannon",
			null,
			SIMPLE, // ALSO NEEDS TO BE CHANGED
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.COCONUT_BOMB,
			"Coconut Bomb",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CONFOUND,
			"Confound",
			null,
			ANCIENT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CORRODE,
			"Corrode",
			null,
			TOXIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.COUNTER_POISE,
			"Counter Poise",
			null,
			BUG,
			MELEE,
			new Constant(0),
			new Constant(0), // SUCKER PUNCHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
			null,
			null,
			null,
			null
		);
		register(
			MoveId.CYCLONE_SLAM,
			"Cyclone Slam",
			null,
			AIR,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DARK_SURGE,
			"Dark Surge",
			null,
			DARK,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DAWDLE,
			"Dawdle",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DAYDREAM,
			"Daydream",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DAZZLING_BARRAGE,
			"Dazzling Barrage",
			null,
			LIGHT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DEEP_FREEZE,
			"Deep Freeze",
			null,
			ICE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DEJA_VU,
			"Deja Vu",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DINO_CLAWS,
			"Dino Claws",
			null,
			ANCIENT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DISCORDANCE,
			"Discordance",
			null,
			BUG,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DISPERSE,
			"Disperse",
			null,
			LIGHT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DISSIPATE,
			"Dissipate",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(-6),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DISSONANT_CHORD,
			"Dissonant Chord",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DIVE_BOMB,
			"Dive Bomb",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DODGE,
			"Dodge",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(4),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DOUBLE_WHACK,
			"Double Whack",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DOWNPOUR,
			"Dounpour",
			null,
			WATER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DRACO_BEAM,
			"Draco Beam",
			null,
			ANCIENT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DRIVING_FORCE,
			"Driving Force",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(-6),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.DRUDGE,
			"Drudge",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EAR_SLAP,
			"Ear Slap",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EARTHQUAKE,
			"Earthquake",
			null,
			EARTH,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EERIE_STARE,
			"Eerie Stare",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ELECTRO_PUNCH,
			"Electro Punch",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EMPATHIZE,
			"Empathize",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EMULATE,
			"Emulate",
			null,
			SPIRIT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ENAMOR,
			"Enamour",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ENERGIZE,
			"Energize",
			null,
			ELECTRIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ENERGY_BREAK,
			"Energy Break",
			null,
			ANCIENT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ENERGY_SURGE,
			"Energy Surge",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.EXAMINE,
			"Examine",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FADE_AWAY,
			"Fade Away",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FAR_ENCHANTMENT,
			"Far Enchantment",
			null,
			ANCIENT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FINAL_RUSE,
			"Final Ruse",
			null,
			SPIRIT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FIRE_BREATH,
			"Fire Breath",
			null,
			FIRE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FIRE_SLAM,
			"Fire Slam",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLABBERGAST,
			"Flabbergast",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLAMING_KICK,
			"Flaming Kick",
			null,
			FIRE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLARE_BLAST,
			"Flare Blast",
			null,
			FIRE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLASH,
			"Flash",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLASH_N_FLEE,
			"Flash n Flee",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FLURRY,
			"Flurry",
			null,
			ICE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FROST_BEAM,
			"Frost Beam",
			null,
			ICE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FROST_BREATH,
			"Frost Breath",
			null,
			ICE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FROST_PUNCH,
			"Frost Punch",
			null,
			ICE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.FUNGUS_CURSE,
			"Fungus Curse",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GAIN_DRAIN,
			"Gain Drain",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GALE_FORCE,
			"Gale Force",
			null,
			AIR,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GAMMA_PULSE,
			"Gamma Pulse",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GLOOMINOUS_ROAR,
			"Gloominous Roar",
			null,
			SIMPLE, /// GLOOOOOOOOOOOOOOOOOOOOOOOM
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GNARLY_GASH,
			"Gnarly Gash",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GNAW,
			"Gnaw",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GROWL,
			"Growl",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.GUST,
			"Gust",
			null,
			AIR,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HARMONIOUS_CHORD,
			"Harmonious Chord",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HARROW,
			"Harrow",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HARVEST,
			"Harvest",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HASTEN,
			"Hasten",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HAZY_SHROUD,
			"Hazy Shroud",
			null,
			SPIRIT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HEADBUTT,
			"Headbutt",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HEALTH_GIFT,
			"Health Gift",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HEART_BREAK,
			"Heart Break",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HEAT_WAVE,
			"Heat Wave",
			null,
			FIRE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HEAVY_BASH,
			"Heavy Bash",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HIGH_PITCH_SCREECH,
			"High Pitch Screech",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HOP_KICK,
			"Hop Kick",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HULLABALOO,
			"Hullabaloo",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HYDRO_BLAST,
			"Hydro Blast",
			null,
			WATER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HYDRO_SLASH,
			"Hydro Slash",
			null,
			WATER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.HYPNOTIZE,
			"Hypnotize",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ICE_HAMMER,
			"Ice Hammer",
			null,
			ICE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ICICLE_TRAP,
			"Icicle Trap",
			null,
			ICE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ILL_WILL,
			"Ill Will",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.IMPERSONATE,
			"Impersonate",
			null,
			SPIRIT,
			STATUS,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.JET_STREAM,
			"Jet Stream",
			null,
			AIR,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.JOLT,
			"Jolt",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.JUDGEMENT,
			"Judgement",
			null,
			MIND,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.JUMP_START,
			"Jump Start",
			null,
			ELECTRIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LANDSLIDE,
			"Landslide",
			null,
			EARTH,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LEAF_BARRAGE,
			"Leaf Barrage",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LIFE_DRAIN,
			"Life Drain",
			null,
			PLANT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LIGHT_DASH,
			"Light Dash",
			null,
			LIGHT,
			MELEE,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LIGHTSPEED_RAY,
			"Lightspeed Ray",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LUNGE,
			"Lunge",
			null,
			BUG,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.LUSTER_LOOT,
			"Luster Loot",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MAGMA_BLOCK,
			"Magma Block",
			null,
			FIRE,
			STATUS,
			new Constant(0),
			new Constant(4),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MAGNIFY,
			"Magnify",
			null,
			LIGHT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MAROON,
			"Maroon",
			null,
			WATER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MEGA_CHOMP,
			"Mega Chomp",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MEGA_KICK,
			"Mega Kick",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MEGA_PUNCH,
			"Mega Punch",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MEMORIZE,
			"Memorize",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.METAL_BLAST,
			"Metal Blast",
			null,
			METAL,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.METAL_SHRIEK,
			"Metal Shriek",
			null,
			METAL,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.METAL_SWIPES,
			"Metal Swipes",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MIND_DRAIN,
			"Mind Drain",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MINDS_EYE,
			"Minds Eye",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MUCK_BLAST,
			"Muck Blast",
			null,
			TOXIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MUCK_BUCK,
			"Muck Buck",
			null,
			EARTH,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MUD_SPATTER,
			"Mud Spatter",
			null,
			EARTH,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.MUDSLIDE,
			"Mudslide",
			null,
			EARTH,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.NAB,
			"Nab",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.NATURES_FORCE,
			"Natures Force",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.NEAR_ENCHANTMENT,
			"Near Enchantment",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.NOSEDIVE,
			"Nosedive",
			null,
			AIR,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.NOVA_BLAST,
			"Nova Blast",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.OIL_LEAK,
			"Oil Leak",
			null,
			TOXIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.OUTBURST,
			"Outburst",
			null,
			ANCIENT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PAINT_SHOWER,
			"Paint Shower",
			null,
			TOXIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PARASITIZE,
			"Parasitize",
			null,
			BUG,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PEACE_OF_MIND,
			"Peace of Mind",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PEBBLE_TOSS,
			"Pebble Toss",
			null,
			EARTH,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PECK,
			"Peck",
			null,
			AIR,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PEP_JAB,
			"Pep Jab",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PEPPER_BURST,
			"Pepper Burst",
			null,
			FIRE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PERCH,
			"Perch",
			null,
			AIR,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PESTER,
			"Pester",
			null,
			BUG,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PESTILENCE,
			"Pestilence",
			null,
			BUG,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PETAL_PUMMEL,
			"Petal Pummel",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PHANTOM_BLAST,
			"Phantom Blast",
			null,
			SPIRIT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PHANTOM_SLASH,
			"Phantom Slash",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PHEROBLAST,
			"Pheroblast",
			null,
			BUG,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PHOTOSYNTHESIS,
			"Photosynthesis",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PIERCING_DRIVE,
			"Piercing Drive",
			null,
			WATER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PILFER,
			"Pilfer",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PINE_SHOT,
			"Pine Shot",
			null,
			PLANT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PLUNGE,
			"Plunge",
			null,
			WATER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.POISON,
			"Poison",
			null,
			TOXIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.POISON_BARBS,
			"Poison Barbs",
			null,
			TOXIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.POUNCE,
			"Pounce",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.POWER_FOCUS,
			"Power Focus",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.POWER_SIPHON,
			"Power Siphon",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PRECISION_BOLT,
			"Precision Bolt",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PREEMPTIVE_STRIKE,
			"Preemptive Strike",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PRIMAL_SLASH,
			"Primal Slash",
			null,
			ANCIENT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PROVOKE,
			"Provoke",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PSYCHAL_CHOMP,
			"Psychal Chomp",
			null,
			MIND,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PSYCHO_BLAST,
			"Psycho Blast",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.PYROKINESIS,
			"Pyrokinesis",
			null,
			FIRE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.QUICK_POUNCE,
			"Quick Pounce",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.QUICK_PUNCH,
			"Quick Punch",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.QUICKSAND,
			"Quicksand",
			null,
			EARTH,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RADIANT_RUSH,
			"Radiant Rush",
			null,
			LIGHT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RAGING_FLAME,
			"Raging Flame",
			null,
			FIRE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RAGING_HOWL,
			"Raging Howl",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RAGING_TACKLE,
			"Raging Tackle",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RANT,
			"Rant",
			null,
			DARK,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RECYCLE,
			"Recycle",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.REFLECTION_BURST,
			"Reflection Burst",
			null,
			METAL,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.REJUVENATE,
			"Rejuvenate",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.REPEATING_TUNE,
			"Repeating Tune",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RESONATE,
			"Resonate",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.RETURN_TO_SENDER,
			"Return to Sender",
			null,
			MIND,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ROCK_SLIDE,
			"Rock Slide",
			null,
			EARTH,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ROGUE_ASSAULT,
			"Rogue Assault",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ROUGH_UP,
			"Rough Up",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SAFEGUARD,
			"Safeguard",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(3),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SAP_PLANT,
			"Sap Plant",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SCAPEGOAT,
			"Scapegoat",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SCAVENGE,
			"Scavenge",
			null,
			AIR,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHADOW_SPRINT,
			"Shadow Sprint",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHADOWBOX,
			"Shadowbox",
			null,
			BRAWLER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHARPEN,
			"Sharpen",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHINE,
			"Shine",
			null,
			LIGHT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHREWD_BLAST,
			"SHrewd Blast",
			null,
			ANCIENT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SHRIEK,
			"Shriek",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SINGE,
			"Singe",
			null,
			FIRE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SLAP_DOWN,
			"Slap Down",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SLASH,
			"Slash",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SLEET_SHOT,
			"Sleet Shot",
			null,
			ICE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SLIME,
			"Slime",
			null,
			TOXIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SLUMBER,
			"Slumber",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SNORE_SPORES,
			"Snore Spores",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SNOWDOZER,
			"Snowdozer",
			null,
			ICE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SOFT_WATER,
			"Soft Water",
			null,
			WATER,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SOUL_STORM,
			"Soul Storm",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPARE,
			"Spare",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPECTRAL_BURST,
			"Spectral Burst",
			null,
			SPIRIT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPIKE_RUSH,
			"Spike Rush",
			null,
			ICE,
			MELEE,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPIT,
			"Spit",
			null,
			SIMPLE,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPITE,
			"Spite",
			null,
			SPIRIT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPLITTING_HEADACHE,
			"Splitting Headache",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(1),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SPRAY,
			"Spray",
			null,
			WATER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SQUARE_ONE,
			"Square One",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SQUAWK,
			"Squawk",
			null,
			AIR,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STAMPEDE,
			"Stampede",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STARE,
			"Stare",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STARTLE,
			"Startle",
			null,
			SPIRIT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STATIC_SHOCK,
			"Static Shock",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STATIC_SPORES,
			"Static Spores",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STEEL_CRUSHER,
			"Steel Crusher",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STING,
			"Sting",
			null,
			TOXIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STRATAGEM,
			"Stratagem",
			null,
			MIND,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STRETCH,
			"Stretch",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STRIKE,
			"Strike",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STUN,
			"Stun",
			null,
			ELECTRIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.STUPEFY,
			"Stupefy",
			null,
			SPIRIT,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SUMMON,
			"Summon",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SWAT,
			"Swat",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.SWIPE,
			"Swipe",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TAIL_SLAP,
			"Tail Slap",
			null,
			SIMPLE,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TAKE_ROOT,
			"Take Root",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TALENT_TRADE,
			"Talent Trade",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TAMP,
			"Tamp",
			null,
			EARTH,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TASE,
			"Tase",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TEAMWORK,
			"Teamwork",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TEAR_DOWN,
			"Tear Down",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TEASE,
			"Tease",
			null,
			DARK,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THISTLE_SLASH,
			"Thistle Slash",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDER_BLAST,
			"Thunder Blast",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDER_CHOMP,
			"Thunder Chomp",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDER_CLAP,
			"Thunder Clap",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDER_CRASH,
			"Thunder Crash",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDER_SLAM,
			"Thunder Slam",
			null,
			ELECTRIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDERSTORM,
			"ThunderStorm",
			null,
			ELECTRIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.THUNDERSTRIKE,
			"Thunderstrike",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TIMBER_THRASH,
			"Timber Thrash",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TONE_BARRIER,
			"Tone Barrier",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(4),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TOXIC_SPORES,
			"Toxic Spores",
			null,
			TOXIC,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TOXIC_TAIL,
			"Toxic Tail",
			null,
			TOXIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TRIBUTE,
			"Tribute",
			null,
			SIMPLE,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TRICKY_TACTICS,
			"Tricky Tactics",
			null,
			DARK,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TSUNAMI,
			"Tsunami",
			null,
			WATER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TUNE_UP,
			"Tune Up",
			null,
			METAL,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.TYPHOON,
			"Typhoon",
			null,
			AIR,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.UNDERMINE,
			"Undermine",
			null,
			EARTH,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.VENOM_CHOMP,
			"Venom Chomp",
			null,
			TOXIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.VENOM_SLASH,
			"Venom Slash",
			null,
			TOXIC,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.VICE_JAWS,
			"Vice Jaws",
			null,
			METAL,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.VINE_LASH,
			"Vine Lash",
			null,
			PLANT,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.VITAL_JAB,
			"Vital Slash",
			null,
			BRAWLER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.WATER_BOMB,
			"Water Bomb",
			null,
			WATER,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.WAVE_WRECKER,
			"Wave Wrecker",
			null,
			WATER,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.WEB_SHOT,
			"Web Shot",
			null,
			BUG,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.WING_SLAP,
			"Wing Slap",
			null,
			AIR,
			MELEE,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.WONDER_HERB,
			"Wonder Herb",
			null,
			PLANT,
			STATUS,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
		);
		register(
			MoveId.ZIP_ZAP,
			"Zip Zap",
			null,
			ELECTRIC,
			RANGED,
			new Constant(0),
			new Constant(0),
			null,
			null,
			null,
			null
        );
    }
}
