package github.snomfish.domain.move;

import java.util.HashMap;
import java.util.Map;

public class MoveRegistry {


    private static final Map<MoveId, Move> registry = new HashMap<>();


    private MoveRegistry() {}


    public static Move get(MoveId id) {
		return registry.get(id);
    } 


    static {/*
		registry.put(MoveId.ACCELERATE, new Move(
			
		));
		registry.put(MoveId.AIM, new Move(
			
		));
		registry.put(MoveId.AIR_BLADE, new Move(
			
		));
		registry.put(MoveId.AIR_SHOT, new Move(
			
		));
		registry.put(MoveId.AIR_WHIP, new Move(
			
		));
		registry.put(MoveId.AQUA_JAWS, new Move(
			
		));
		registry.put(MoveId.AUGMENT, new Move(
			
		));
		registry.put(MoveId.BAFFLE, new Move(
			
		));
		registry.put(MoveId.BAG_OF_TRICKS, new Move(
			
		));
		registry.put(MoveId.BAIT, new Move(
			
		));
		registry.put(MoveId.BAMBOOZLE, new Move(
			
		));
		registry.put(MoveId.BANANA_SPLIT, new Move(
			
		));
		registry.put(MoveId.BANE_OF_HASTE, new Move(
			
		));
		registry.put(MoveId.BANEFUL_BASH, new Move(
			
		));
		registry.put(MoveId.BARBS, new Move(
			
		));
		registry.put(MoveId.BARTER, new Move(
			
		));
		registry.put(MoveId.BASH, new Move(
			
		));
		registry.put(MoveId.BATTERING_RAM, new Move(
			
		));
		registry.put(MoveId.BATTLE_CHIME, new Move(
			
		));
		registry.put(MoveId.BATTLE_HORN, new Move(
			
		));
		registry.put(MoveId.BEQUEATH, new Move(
			
		));
		registry.put(MoveId.BITTER_COLD, new Move(
			
		));
		registry.put(MoveId.BLAZE_CHOMP, new Move(
			
		));
		registry.put(MoveId.BLAZE_OF_GLORY, new Move(
			
		));
		registry.put(MoveId.BLAZE_RUSH, new Move(
			
		));
		registry.put(MoveId.BLINDSPOT_BATTER, new Move(
			
		));
		registry.put(MoveId.BLUDGEON, new Move(
			
		));
		registry.put(MoveId.BODY_CRASH, new Move(
			
		));
		registry.put(MoveId.BODY_SLAM, new Move(
			
		));
		registry.put(MoveId.BOILING_PRESS, new Move(
			
		));
		registry.put(MoveId.BOOM_BASH, new Move(
			
		));
		registry.put(MoveId.BOULDER_BLAST, new Move(
			
		));
		registry.put(MoveId.BRACE, new Move(
			
		));
		registry.put(MoveId.BRAIN_EXERCISE, new Move(
			
		));
		registry.put(MoveId.BRAINWASH, new Move(
			
		));
		registry.put(MoveId.BRAWN_BOOST, new Move(
			
		));
		registry.put(MoveId.BRIAR_BLOCK, new Move(
			
		));
		registry.put(MoveId.BUCK_UP, new Move(
			
		));
		registry.put(MoveId.BUG_BITE, new Move(
			
		));
		registry.put(MoveId.BULK_UP, new Move(
			
		));
		registry.put(MoveId.BURN_UP, new Move(
			
		));
		registry.put(MoveId.BURROW, new Move(
			
		));
		registry.put(MoveId.BUSH_WHACK, new Move(
			
		));
		registry.put(MoveId.CANISTER_BURST, new Move(
			
		));
		registry.put(MoveId.CEREBRAL_SLASH, new Move(
			
		));
		registry.put(MoveId.CHARADE, new Move(
			
		));
		registry.put(MoveId.CHARRED_CLAWS, new Move(
			
		));
		registry.put(MoveId.CHASE_DOWN, new Move(
			
		));
		registry.put(MoveId.CHILLY_CHOMP, new Move(
			
		));
		registry.put(MoveId.CHOMP, new Move(
			
		));
		registry.put(MoveId.CHOP, new Move(
			
		));
		registry.put(MoveId.CLAMP, new Move(
			
		));
		registry.put(MoveId.CLAY_SLAP, new Move(
			
		));
		registry.put(MoveId.CLIMATE_CANNON, new Move(
			
		));
		registry.put(MoveId.COCONUT_BOMB, new Move(
			
		));
		registry.put(MoveId.CONFOUND, new Move(
			
		));
		registry.put(MoveId.CORRODE, new Move(
			
		));
		registry.put(MoveId.COUNTER_POISE, new Move(
			
		));
		registry.put(MoveId.CYCLONE_SLAM, new Move(
			
		));
		registry.put(MoveId.DARK_SURGE, new Move(
			
		));
		registry.put(MoveId.DAWDLE, new Move(
			
		));
		registry.put(MoveId.DAYDREAM, new Move(
			
		));
		registry.put(MoveId.DAZZLING_BARRAGE, new Move(
			
		));
		registry.put(MoveId.DEEP_FREEZE, new Move(
			
		));
		registry.put(MoveId.DEJA_VU, new Move(
			
		));
		registry.put(MoveId.DINO_CLAWS, new Move(
			
		));
		registry.put(MoveId.DISCORDANCE, new Move(
			
		));
		registry.put(MoveId.DISPERSE, new Move(
			
		));
		registry.put(MoveId.DISSIPATE, new Move(
			
		));
		registry.put(MoveId.DISSONANT_CHORD, new Move(
			
		));
		registry.put(MoveId.DIVE_BOMB, new Move(
			
		));
		registry.put(MoveId.DODGE, new Move(
			
		));
		registry.put(MoveId.DOUBLE_WHACK, new Move(
			
		));
		registry.put(MoveId.DOWNPOUR, new Move(
			
		));
		registry.put(MoveId.DRACO_BEAM, new Move(
			
		));
		registry.put(MoveId.DRIVING_FORCE, new Move(
			
		));
		registry.put(MoveId.DRUDGE, new Move(
			
		));
		registry.put(MoveId.DÉJÀ_VU, new Move(
			
		));
		registry.put(MoveId.EAR_SLAP, new Move(
			
		));
		registry.put(MoveId.EARTHQUAKE, new Move(
			
		));
		registry.put(MoveId.EERIE_STARE, new Move(
			
		));
		registry.put(MoveId.ELECTRO_PUNCH, new Move(
			
		));
		registry.put(MoveId.EMPATHIZE, new Move(
			
		));
		registry.put(MoveId.EMULATE, new Move(
			
		));
		registry.put(MoveId.ENAMOR, new Move(
			
		));
		registry.put(MoveId.ENERGIZE, new Move(
			
		));
		registry.put(MoveId.ENERGY_BREAK, new Move(
			
		));
		registry.put(MoveId.ENERGY_SURGE, new Move(
			
		));
		registry.put(MoveId.EXAMINE, new Move(
			
		));
		registry.put(MoveId.FADE_AWAY, new Move(
			
		));
		registry.put(MoveId.FAR_ENCHANTMENT, new Move(
			
		));
		registry.put(MoveId.FINAL_RUSE, new Move(
			
		));
		registry.put(MoveId.FIRE_BREATH, new Move(
			
		));
		registry.put(MoveId.FIRE_SLAM, new Move(
			
		));
		registry.put(MoveId.FLABBERGAST, new Move(
			
		));
		registry.put(MoveId.FLAMING_KICK, new Move(
			
		));
		registry.put(MoveId.FLARE_BLAST, new Move(
			
		));
		registry.put(MoveId.FLASH, new Move(
			
		));
		registry.put(MoveId.FLASH_N_FLEE, new Move(
			
		));
		registry.put(MoveId.FLURRY, new Move(
			
		));
		registry.put(MoveId.FROST_BEAM, new Move(
			
		));
		registry.put(MoveId.FROST_BREATH, new Move(
			
		));
		registry.put(MoveId.FROST_PUNCH, new Move(
			
		));
		registry.put(MoveId.FUNGUS_CURSE, new Move(
			
		));
		registry.put(MoveId.GAIN_DRAIN, new Move(
			
		));
		registry.put(MoveId.GALE_FORCE, new Move(
			
		));
		registry.put(MoveId.GAMMA_PULSE, new Move(
			
		));
		registry.put(MoveId.GLOOMINOUS_ROAR, new Move(
			
		));
		registry.put(MoveId.GNARLY_GASH, new Move(
			
		));
		registry.put(MoveId.GNAW, new Move(
			
		));
		registry.put(MoveId.GROWL, new Move(
			
		));
		registry.put(MoveId.GUST, new Move(
			
		));
		registry.put(MoveId.HARMONIOUS_CHORD, new Move(
			
		));
		registry.put(MoveId.HARROW, new Move(
			
		));
		registry.put(MoveId.HARVEST, new Move(
			
		));
		registry.put(MoveId.HASTEN, new Move(
			
		));
		registry.put(MoveId.HAZY_SHROUD, new Move(
			
		));
		registry.put(MoveId.HEADBUTT, new Move(
			
		));
		registry.put(MoveId.HEALTH_GIFT, new Move(
			
		));
		registry.put(MoveId.HEART_BREAK, new Move(
			
		));
		registry.put(MoveId.HEAT_WAVE, new Move(
			
		));
		registry.put(MoveId.HEAVY_BASH, new Move(
			
		));
		registry.put(MoveId.HIGH_PITCH_SCREECH, new Move(
			
		));
		registry.put(MoveId.HOP_KICK, new Move(
			
		));
		registry.put(MoveId.HULLABALOO, new Move(
			
		));
		registry.put(MoveId.HYDRO_BLAST, new Move(
			
		));
		registry.put(MoveId.HYDRO_SLASH, new Move(
			
		));
		registry.put(MoveId.HYPNOTIZE, new Move(
			
		));
		registry.put(MoveId.ICE_HAMMER, new Move(
			
		));
		registry.put(MoveId.ICICLE_TRAP, new Move(
			
		));
		registry.put(MoveId.ILL_WILL, new Move(
			
		));
		registry.put(MoveId.IMPERSONATE, new Move(
			
		));
		registry.put(MoveId.JET_STREAM, new Move(
			
		));
		registry.put(MoveId.JOLT, new Move(
			
		));
		registry.put(MoveId.JUDGEMENT, new Move(
			
		));
		registry.put(MoveId.JUMP_START, new Move(
			
		));
		registry.put(MoveId.LANDSILDE, new Move(
			
		));
		registry.put(MoveId.LANDSLDE, new Move(
			
		));
		registry.put(MoveId.LANDSLIDE, new Move(
			
		));
		registry.put(MoveId.LANDSLLIDE, new Move(
			
		));
		registry.put(MoveId.LEAF_BARRAGE, new Move(
			
		));
		registry.put(MoveId.LIFE_DRAIN, new Move(
			
		));
		registry.put(MoveId.LIGHT_DASH, new Move(
			
		));
		registry.put(MoveId.LIGHTSPEED_RAY, new Move(
			
		));
		registry.put(MoveId.LUNGE, new Move(
			
		));
		registry.put(MoveId.LUSTER_LOOT, new Move(
			
		));
		registry.put(MoveId.MAGMA_BLOCK, new Move(
			
		));
		registry.put(MoveId.MAGNIFY, new Move(
			
		));
		registry.put(MoveId.MAROON, new Move(
			
		));
		registry.put(MoveId.MEGA_CHOMP, new Move(
			
		));
		registry.put(MoveId.MEGA_KICK, new Move(
			
		));
		registry.put(MoveId.MEGA_PUNCH, new Move(
			
		));
		registry.put(MoveId.MEMORIZE, new Move(
			
		));
		registry.put(MoveId.METAL_BLAST, new Move(
			
		));
		registry.put(MoveId.METAL_SHRIEK, new Move(
			
		));
		registry.put(MoveId.METAL_SWIPES, new Move(
			
		));
		registry.put(MoveId.MIND_DRAIN, new Move(
			
		));
		registry.put(MoveId.MINDS_EYE, new Move(
			
		));
		registry.put(MoveId.MUCK_BLAST, new Move(
			
		));
		registry.put(MoveId.MUCK_BUCK, new Move(
			
		));
		registry.put(MoveId.MUD_SPATTER, new Move(
			
		));
		registry.put(MoveId.MUD_SPLATTER, new Move(
			
		));
		registry.put(MoveId.MUDSLIDE, new Move(
			
		));
		registry.put(MoveId.NAB, new Move(
			
		));
		registry.put(MoveId.NATURES_FORCE, new Move(
			
		));
		registry.put(MoveId.NEAR_ENCHANTMENT, new Move(
			
		));
		registry.put(MoveId.NOSEDIVE, new Move(
			
		));
		registry.put(MoveId.NOVA_BLAST, new Move(
			
		));
		registry.put(MoveId.OIL_LEAK, new Move(
			
		));
		registry.put(MoveId.OUTBURST, new Move(
			
		));
		registry.put(MoveId.PAINT_SHOWER, new Move(
			
		));
		registry.put(MoveId.PARASITIZE, new Move(
			
		));
		registry.put(MoveId.PEACE_OF_MIND, new Move(
			
		));
		registry.put(MoveId.PEBBLE_TOSS, new Move(
			
		));
		registry.put(MoveId.PECK, new Move(
			
		));
		registry.put(MoveId.PEP_JAB, new Move(
			
		));
		registry.put(MoveId.PEPPER_BURST, new Move(
			
		));
		registry.put(MoveId.PERCH, new Move(
			
		));
		registry.put(MoveId.PESTER, new Move(
			
		));
		registry.put(MoveId.PESTILENCE, new Move(
			
		));
		registry.put(MoveId.PETAL_PUMMEL, new Move(
			
		));
		registry.put(MoveId.PHANTOM_BLAST, new Move(
			
		));
		registry.put(MoveId.PHANTOM_SLASH, new Move(
			
		));
		registry.put(MoveId.PHEROBLAST, new Move(
			
		));
		registry.put(MoveId.PHOTOSYNTHESIS, new Move(
			
		));
		registry.put(MoveId.PIERCING_DRIVE, new Move(
			
		));
		registry.put(MoveId.PILFER, new Move(
			
		));
		registry.put(MoveId.PINE_SHOT, new Move(
			
		));
		registry.put(MoveId.PLUNGE, new Move(
			
		));
		registry.put(MoveId.POISON, new Move(
			
		));
		registry.put(MoveId.POISON_BARBS, new Move(
			
		));
		registry.put(MoveId.POUNCE, new Move(
			
		));
		registry.put(MoveId.POWER_FOCUS, new Move(
			
		));
		registry.put(MoveId.POWER_SIPHON, new Move(
			
		));
		registry.put(MoveId.PRECISION_BOLT, new Move(
			
		));
		registry.put(MoveId.PREEMPTIVE_STRIKE, new Move(
			
		));
		registry.put(MoveId.PRIMAL_SLASH, new Move(
			
		));
		registry.put(MoveId.PROVOKE, new Move(
			
		));
		registry.put(MoveId.PSYCHAL_CHOMP, new Move(
			
		));
		registry.put(MoveId.PSYCHO_BLAST, new Move(
			
		));
		registry.put(MoveId.PYROKINESIS, new Move(
			
		));
		registry.put(MoveId.QUICK_POUNCE, new Move(
			
		));
		registry.put(MoveId.QUICK_PUNCH, new Move(
			
		));
		registry.put(MoveId.QUICKSAND, new Move(
			
		));
		registry.put(MoveId.RADIANT_RUSH, new Move(
			
		));
		registry.put(MoveId.RAGING_FLAME, new Move(
			
		));
		registry.put(MoveId.RAGING_HOWL, new Move(
			
		));
		registry.put(MoveId.RAGING_TACKLE, new Move(
			
		));
		registry.put(MoveId.RANT, new Move(
			
		));
		registry.put(MoveId.RECYCLE, new Move(
			
		));
		registry.put(MoveId.REFLECTION_BURST, new Move(
			
		));
		registry.put(MoveId.REJUVENATE, new Move(
			
		));
		registry.put(MoveId.REPEATING_TUNE, new Move(
			
		));
		registry.put(MoveId.RESONATE, new Move(
			
		));
		registry.put(MoveId.RETURN_TO_SENDER, new Move(
			
		));
		registry.put(MoveId.ROCK_SLIDE, new Move(
			
		));
		registry.put(MoveId.ROGUE_ASSAULT, new Move(
			
		));
		registry.put(MoveId.ROUGH_UP, new Move(
			
		));
		registry.put(MoveId.SAFEGUARD, new Move(
			
		));
		registry.put(MoveId.SAP_PLANT, new Move(
			
		));
		registry.put(MoveId.SCAPEGOAT, new Move(
			
		));
		registry.put(MoveId.SCAVENGE, new Move(
			
		));
		registry.put(MoveId.SHADOW_SPRINT, new Move(
			
		));
		registry.put(MoveId.SHADOWBOX, new Move(
			
		));
		registry.put(MoveId.SHARPEN, new Move(
			
		));
		registry.put(MoveId.SHINE, new Move(
			
		));
		registry.put(MoveId.SHREWD_BLAST, new Move(
			
		));
		registry.put(MoveId.SHRIEK, new Move(
			
		));
		registry.put(MoveId.SINGE, new Move(
			
		));
		registry.put(MoveId.SLAP_DOWN, new Move(
			
		));
		registry.put(MoveId.SLASH, new Move(
			
		));
		registry.put(MoveId.SLEET_SHOT, new Move(
			
		));
		registry.put(MoveId.SLIME, new Move(
			
		));
		registry.put(MoveId.SLUMBER, new Move(
			
		));
		registry.put(MoveId.SNORE_SPORES, new Move(
			
		));
		registry.put(MoveId.SNOWDOZER, new Move(
			
		));
		registry.put(MoveId.SOFT_WATER, new Move(
			
		));
		registry.put(MoveId.SOUL_STORM, new Move(
			
		));
		registry.put(MoveId.SPARE, new Move(
			
		));
		registry.put(MoveId.SPECTAL_BURST, new Move(
			
		));
		registry.put(MoveId.SPECTRAL_BURST, new Move(
			
		));
		registry.put(MoveId.SPIKE_RUSH, new Move(
			
		));
		registry.put(MoveId.SPIT, new Move(
			
		));
		registry.put(MoveId.SPITE, new Move(
			
		));
		registry.put(MoveId.SPLITTING_HEADACHE, new Move(
			
		));
		registry.put(MoveId.SPRAY, new Move(
			
		));
		registry.put(MoveId.SQUARE_ONE, new Move(
			
		));
		registry.put(MoveId.SQUAWK, new Move(
			
		));
		registry.put(MoveId.STAMPEDE, new Move(
			
		));
		registry.put(MoveId.STARE, new Move(
			
		));
		registry.put(MoveId.STARTLE, new Move(
			
		));
		registry.put(MoveId.STATIC_SHOCK, new Move(
			
		));
		registry.put(MoveId.STATIC_SPORES, new Move(
			
		));
		registry.put(MoveId.STEEL_CRUSHER, new Move(
			
		));
		registry.put(MoveId.STING, new Move(
			
		));
		registry.put(MoveId.STRATAGEM, new Move(
			
		));
		registry.put(MoveId.STRETCH, new Move(
			
		));
		registry.put(MoveId.STRIKE, new Move(
			
		));
		registry.put(MoveId.STUN, new Move(
			
		));
		registry.put(MoveId.STUPEFY, new Move(
			
		));
		registry.put(MoveId.SUMMON, new Move(
			
		));
		registry.put(MoveId.SWAT, new Move(
			
		));
		registry.put(MoveId.SWIPE, new Move(
			
		));
		registry.put(MoveId.TAIL_SLAP, new Move(
			
		));
		registry.put(MoveId.TAKE_ROOT, new Move(
			
		));
		registry.put(MoveId.TALENT_TRADE, new Move(
			
		));
		registry.put(MoveId.TAMP, new Move(
			
		));
		registry.put(MoveId.TASE, new Move(
			
		));
		registry.put(MoveId.TEAMWORK, new Move(
			
		));
		registry.put(MoveId.TEAR_DOWN, new Move(
			
		));
		registry.put(MoveId.TEASE, new Move(
			
		));
		registry.put(MoveId.THISTLE_SLASH, new Move(
			
		));
		registry.put(MoveId.THUNDER_BLAST, new Move(
			
		));
		registry.put(MoveId.THUNDER_CHOMP, new Move(
			
		));
		registry.put(MoveId.THUNDER_CLAP, new Move(
			
		));
		registry.put(MoveId.THUNDER_CRASH, new Move(
			
		));
		registry.put(MoveId.THUNDER_SLAM, new Move(
			
		));
		registry.put(MoveId.THUNDER_STRIKE, new Move(
			
		));
		registry.put(MoveId.THUNDERSTORM, new Move(
			
		));
		registry.put(MoveId.THUNDERSTRIKE, new Move(
			
		));
		registry.put(MoveId.TIMBER_THRASH, new Move(
			
		));
		registry.put(MoveId.TONE_BARRIER, new Move(
			
		));
		registry.put(MoveId.TOXIC, new Move(
			
		));
		registry.put(MoveId.TOXIC_SPORES, new Move(
			
		));
		registry.put(MoveId.TOXIC_TAIL, new Move(
			
		));
		registry.put(MoveId.TRIBUTE, new Move(
			
		));
		registry.put(MoveId.TRICKY_TACTICS, new Move(
			
		));
		registry.put(MoveId.TSUNAMI, new Move(
			
		));
		registry.put(MoveId.TUNE_UP, new Move(
			
		));
		registry.put(MoveId.TYPHOON, new Move(
			
		));
		registry.put(MoveId.UNDERMINE, new Move(
			
		));
		registry.put(MoveId.VENOM_CHOMP, new Move(
			
		));
		registry.put(MoveId.VENOM_SLASH, new Move(
			
		));
		registry.put(MoveId.VICE_JAWS, new Move(
			
		));
		registry.put(MoveId.VINE_LASH, new Move(
			
		));
		registry.put(MoveId.VITAL_JAB, new Move(
			
		));
		registry.put(MoveId.WATER_BOMB, new Move(
			
		));
		registry.put(MoveId.WAVE_WRECKER, new Move(
			
		));
		registry.put(MoveId.WEB_SHOT, new Move(
			
		));
		registry.put(MoveId.WING_SLAP, new Move(
			
		));
		registry.put(MoveId.WONDER_HERB, new Move(
			
		));
		registry.put(MoveId.ZIP_ZAP, new Move(

        ));*/
    }
}
