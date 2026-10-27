package github.snomfish.domain.move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.move.structures.MoveEffect;
import github.snomfish.domain.stats.StatId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.functionality.Constants;
import github.snomfish.functionality.Schematics;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.conditions.GreaterThan;
import github.snomfish.functionality.condition.conditions.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.DealFormulaDamage;
import github.snomfish.functionality.effect.effects.EnergyDrainEffect;
import github.snomfish.functionality.effect.effects.HealthDrainEffect;
import github.snomfish.functionality.number.ConditionalNumber;
import github.snomfish.functionality.number.Constant;

import static github.snomfish.domain.move.CategoryId.*;
import static github.snomfish.domain.move.MoveId.*;
import static github.snomfish.domain.move.tags.TagId.*;
import static github.snomfish.domain.type.TypeId.*;

public class MoveRegistry {


    private static final Map<MoveId, Move> registry = new HashMap<>();


    private MoveRegistry() {}


    public static Move get(MoveId id) {
		return registry.get(id);
    }


	private static void register(Move move) {
		registry.put(move.id(), move);
	}


    static {
		
		register(MoveBuilder.newMove(ACCELERATE)
			.name("Accerlerate")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingUser()
					.speed(new Constant(2))
			))
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_AIR)
			.name("adaptive_assault_air")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_ANCIENT)
			.name("adaptive_assault_ancient")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new ConditionalEffect(
							new RandomCondition(new Constant(0.2)),
							ApplyStatChanges.userChangingUser()
								.mattack(new Constant(1))
						)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_BRAWLER)
			.name("adaptive_assault_brawler")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_BUG)
			.name("adaptive_assault_bug")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_FIRE)
			.name("adaptive_assault_fire")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_ICE)
			.name("adaptive_assault_ice")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.FROSTBITE)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_SIMPLE)
			.name("adaptive_assault_simple")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_SPIRIT)
			.name("adaptive_assault_spirit")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_TOXIC)
			.name("adaptive_assault_toxic")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BAD_POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ADAPTIVE_ASSAULT_WATER)
			.name("adaptive_assault_water")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(AGGRAVATE)
			.name("aggravate")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(AIM)
			.name("Aim")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(10))
			.build()
		);
		register(MoveBuilder.newMove(AIR_BLADE)
			.name("Air Blade")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(AIR_SHOT)
			.name("Air Shot")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(AIR_WHIP)
			.name("Air Whip")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(AIRSTRIKE)
			.name("Airstrike")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(125))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(ANCIENT_ROAR)
			.name("ancient_roar")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.addTag(PIVOT)
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(APPARITION)
			.name("apparition")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(AQUA_JAWS)
			.name("Aqua Jaws")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ARMOUR_DOWN)
			.name("armour_down")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingUser()
					.speed(new Constant(2))	
			))
			.build()
		);
		register(MoveBuilder.newMove(AUGMENT)
			.name("Augment")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(BAFFLE)
			.name("Baffle")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.addTag(PIVOT)
			.build()
		);
		register(MoveBuilder.newMove(BAG_OF_TRICKS)
			.name("Bag of Tricks")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(BAIT)
			.name("Bait")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(2))
			.build()
		);
		register(MoveBuilder.newMove(BAMBOOZLE)
			.name("Bamboozle")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BANANA_SLAM)
			.name("banana_slam")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BANANA_SPLIT)
			.name("Banana Split")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(100))
			.build()
		);
		register(MoveBuilder.newMove(BANE_OF_HASTE)
			.name("Bane of Haste")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(-7))
			.build()
		);
		register(MoveBuilder.newMove(BANEFUL_BASH)
			.name("Baneful Bash")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BARB_TRAP) // MAN I HAVE NO IDEA, MAYBE HAVE THIS APPLY A ONE TURN MECHANIC ON THE USER????
			.name("barb_trap")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(BARBS)
			.name("Barbs")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(BARTER)
			.name("Barter")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(BASH)
			.name("Bash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BATTERING_RAM)
			.name("Battering Ram")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.overrideAttackingStat(StatId.MDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BATTLE_CHIME)
			.name("Battle Chime")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// remove half of the user's health
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(20))
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(BATTLE_HORN)
			.name("Battle Horn")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.mdefense(new Constant(1))
					// heal the status effects off the party
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(BEASTS_SLUMBER)
			.name("beasts_slumber")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// applys beast slumber mechanic
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BEQUEATH)
			.name("Bequeath")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(-6))
			.build()
		);
		register(MoveBuilder.newMove(BITTER_COLD)
			.name("Bitter Cold")
			.type(ICE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.FROSTBITE)
			))
			.build()
		);
		register(MoveBuilder.newMove(BLAZE_CHOMP)
			.name("Blaze Chomp")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BLAZE_OF_GLORY)
			.name("Blaze of Glory")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BLAZE_PUNCH)
			.name("blaze_punch")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BLAZE_RUSH)
			.name("Blaze Rush")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.speed(new Constant(1))	
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BLINDSPOT_BATTER)
			.name("Blindspot Batter")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BLOOD_DRAIN)
			.name("blood_drain")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						// apply blood drain mechanic
					))
				))
			))
			.addTag(CONTACT)
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(BLUDGEON)
			.name("Bludgeon")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BODY_CRASH)
			.name("Body Crash")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.overrideAttackingStat(StatId.MDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BODY_SLAM)
			.name("Body Slam")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BOILING_PRESS)
			.name("Boiling Press")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BONE_CRUNCH)
			.name("bone_crunch")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(BOOM_BASH)
			.name("Boom Bash")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(KICKING)
			.build()
		);
		register(MoveBuilder.newMove(BOULDER_BLAST)
			.name("Boulder Blast")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRACE)
			.name("Brace")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingTarget()
						.mdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRAIN_EXERCISE)
			.name("Brain Exercise")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.mdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRAIN_FREEZE)
			.name("brain_freeze")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRAINWASH)
			.name("Brainwash")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRAWN_BOOST)
			.name("Brawn Boost")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(2))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BRIAR_BLOCK)
			.name("Briar Block")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(4))
			.build()
		);
		register(MoveBuilder.newMove(BUCK_UP)
			.name("Buck Up")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.critChance(new Constant(2))		
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BUG_BITE)
			.name("Bug Bite")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(CONTACT)
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(BULK_UP)
			.name("Bulk Up")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.mdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BURN_UP)
			.name("Burn Up")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
					// clear hazards
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BURROW)
			.name("Burrow")
			.type(EARTH)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(BUSH_WHACK)
			.name("Bush Whack")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(CANISTER_BURST)
			.name("Canister Burst")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(CAPRIOLE)
			.name("capriole")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(105))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(KICKING)
			.build()
		);
		register(MoveBuilder.newMove(CARNIVOROUS_SNAP)
			.name("Carnivorous Snap")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(CONTACT)
			.addTag(HEALTH_DRAIN)
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(CEREBRAL_SLASH)
			.name("Cerebral Slash")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(CHARADE)
			.name("Charade")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(CHARRED_CLAWS)
			.name("Charred Claws")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(CHASE_DOWN)
			.name("Chase Down")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CHILLY_CHOMP)
			.name("Chilly Chomp")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CHOMP)
			.name("Chomp")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CHOP)
			.name("Chop")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CLAMP)
			.name("Clamp")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(45))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CLAMP_CLAW)
			.name("clamp_claw")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(CLAWBER)
			.name("clawber")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(CLAY_SLAP)
			.name("Clay Slap")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(KICKING)
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(CLEAN_UP)
			.name("clean_up")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(CLIMATE_CANNON)
			.name("Climate Cannon")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(COCONUT_BOMB)
			.name("Coconut Bomb")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(COMPLETE_DEMOLITION)
			.name("complete_demolition")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(150))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CONCLUSION)
			.name("conclusion")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(CONFOUND)
			.name("Confound")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(CORRODE)
			.name("Corrode")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(COUNTER_POISE)
			.name("Counter Poise")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(COUNTER_STANCE)
			.name("counter_stance")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(CYCLONE_SLAM)
			.name("Cyclone Slam")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DARK_SURGE)
			.name("Dark Surge")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DART_BURST)
			.name("dart_burst")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DAWDLE)
			.name("Dawdle")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(DAYDREAM)
			.name("Daydream")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(DAZZLING_BARRAGE)
			.name("Dazzling Barrage")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DEEP_FREEZE)
			.name("Deep Freeze")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DEJA_VU)
			.name("Deja Vu")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(DINO_CLAWS)
			.name("Dino Claws")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(DISCORDANCE)
			.name("Discordance")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(DISPERSE)
			.name("Disperse")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.accuracy(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DISSIPATE)
			.name("Dissipate")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(-6))
			.addTag(PIVOT)
			.build()
		);
		register(MoveBuilder.newMove(DISSONANT_CHORD)
			.name("Dissonant Chord")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(DIVE_BOMB)
			.name("Dive Bomb")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DODGE)
			.name("Dodge")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(4))
			.build()
		);
		register(MoveBuilder.newMove(DOUBLE_BEAT)
			.name("double_beat")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DOUBLE_STING) // NEEDS TO BE MADE INTO A BARRAGE
			.name("double_sting")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DOUBLE_WHACK)
			.name("Double Whack")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DOUSE)
			.name("douse")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DOWNPOUR)
			.name("Dounpour")
			.type(WATER)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(DRACO_BEAM)
			.name("Draco Beam")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(130))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(DRIVING_FORCE)
			.name("Driving Force")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.priority(new Constant(-6))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(DRUDGE)
			.name("Drudge")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.speed(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(EAR_SLAP)
			.name("Ear Slap")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(EARTHQUAKE)
			.name("Earthquake")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(EERIE_STARE)
			.name("Eerie Stare")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(EGG_LOB)
			.name("egg_lob")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(EGG_ONSLAUGHT)
			.name("Egg Onslaught")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(125))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(EGG_STORM)
			.name("egg_storm")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ELECTRO_BLAST)
			.name("Elctro Blast")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(125))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(ELECTRO_PUNCH)
			.name("Electro Punch")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(PUNCHING)
			.build()
		);
		register(MoveBuilder.newMove(ELECTROBURST)
			.name("electroburst")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS),
						Schematics.applyStatusToTarget(0.1 / 0.9, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ELEMENTAL_BURST)
			.name("elemental_burst")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS),
						Schematics.applyStatusToTarget(0.1 / 0.9, StatusId.BURN),
						Schematics.applyStatusToTarget(0.1 / 0.8, StatusId.FROSTBITE)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(EMPATHIZE)
			.name("Empathize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(EMULATE)
			.name("Emulate")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(ENAMOR)
			.name("Enamour")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(ENERGIZE)
			.name("Energize")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ENERGY_BREAK)
			.name("Energy Break")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new EnergyDrainEffect(new Constant(1/2))
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ENERGY_SURGE)
			.name("Energy Surge")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ERUPTION)
			.name("Eruption")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(125))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(EXAMINE)
			.name("Examine")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		//RDUHGURSNCGURYEISNGCFXYERSIXNFGRYSNXFVREUXIFGYURGAFXYURNSGXYFUNERGSYUXFINGREYUSNFXGURYENOAFUEAWGYFBEGWIUAVOFGEBCGFYURESBIC
		register(MoveBuilder.newMove(EXPERT_ONSLAUGHT)
			.name("expert_onslaught")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FADE_AWAY)
			.name("Fade Away")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(PIVOT)
			.build()
		);
		register(MoveBuilder.newMove(FAR_ENCHANTMENT)
			.name("Far Enchantment")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(FIERY_FONDANT)
			.name("fiery_fondant")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.mdefense(new Constant(1))	
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(FINAL_RUSE)
			.name("Final Ruse")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(FIRE_BREATH)
			.name("Fire Breath")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FIRE_SLAM)
			.name("Fire Slam")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(FLABBERGAST)
			.name("Flabbergast")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FLAMING_KICK)
			.name("Flaming Kick")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(KICKING)
			.build()
		);
		register(MoveBuilder.newMove(FLARE)
			.name("flare")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FLARE_BLAST)
			.name("Flare Blast")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FLASH)
			.name("Flash")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(FLASH_N_FLEE)
			.name("Flash n Flee")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(PIVOT)
			.build()
		);
		register(MoveBuilder.newMove(FLASH_PULSE)
			.name("flash_pulse")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FLORA_BLAST)
			.name("Flora Blast")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(FLURRY)
			.name("Flurry")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FOUL_CHANT)
			.name("foul_chant")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.06, StatusId.POISON),
						Schematics.applyStatusToTarget(0.06 / 0.9, StatusId.PARALYSIS),
						Schematics.applyStatusToTarget(0.06 / 0.8, StatusId.BURN),
						Schematics.applyStatusToTarget(0.06 / 0.7, StatusId.FROSTBITE),
						Schematics.applyStatusToTarget(0.06 / 0.6, StatusId.SLEEP)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FRIGHTFUL_SUPRISE)
			.name("Frightful Suprise")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						// TODO apply bane of haste at the end of the turn
					))
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(FROST_BEAM)
			.name("Frost Beam")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FROST_BLAST)
			.name("frost_blast")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.FROSTBITE)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FROST_BREATH)
			.name("Frost Breath")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(FROST_PUNCH)
			.name("Frost Punch")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(PUNCHING)
			.build()
		);
		register(MoveBuilder.newMove(FULGER_FANGS)
			.name("Fulgur Fangs")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(FUNGUS_CURSE)
			.name("Fungus Curse")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(GAIN_DRAIN)
			.name("Gain Drain")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(GALE_FORCE)
			.name("Gale Force")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(GAMMA_PULSE)
			.name("Gamma Pulse")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GEYSER)
			.name("geyser")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GHOSTLY_HOWL)
			.name("ghostly_howl")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(GIVE_UP)
			.name("Give Up")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(1))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GLOOM_FANGS)
			.name("gloom_fangs")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(GLOOMINOUS_FANGS)
			.name("gloominous_fangs")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(GLOOMINOUS_ROAR)
			.name("Gloominous Roar")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(GNARLY_GASH)
			.name("Gnarly Gash")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(GNAW)
			.name("Gnaw")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(GOBBLE_GOOP)
			.name("gobble_goop")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GOOP_SPOUT)
			.name("goop_spout")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.POISON)
			))
			.build()
		);
		register(MoveBuilder.newMove(GRAFFITI)
			.name("graffiti")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GROWL)
			.name("Growl")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(GULP)
			.name("gulp")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(GUST)
			.name("Gust")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// clear hazards
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(HARMONIOUS_CHORD)
			.name("Harmonious Chord")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.SLEEP)
					))
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(HARROW)
			.name("Harrow")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HARVEST)
			.name("Harvest")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HASTEN)
			.name("Hasten")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.speed(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(HAZE_SHROUD)
			.name("haze_shroud")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HAZY_SHROUD)
			.name("Hazy Shroud")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HEADBUTT)
			.name("Headbutt")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HEALTH_GIFT)
			.name("Health Gift")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HEART_BREAK)
			.name("Heart Break")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HEAT_WAVE)
			.name("Heat Wave")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HEAVY_BASH)
			.name("Heavy Bash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HEEDLESS_RUSH) // TODO: give this perfect accuracy and bypass dodge
			.name("Heedless Rush")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(HELLSTORM)
			.name("Hellstorm")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(

					))
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(HERBAL_REMEDY)
			.name("herbal_remedy")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HIGH_PITCH_SCREECH)
			.name("High Pitch Screech")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(HOLY_SLASH)
			.name("holy_slash")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(HOODWINK)
			.name("hoodwink")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(HOP_KICK)
			.name("Hop Kick")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(KICKING)
			.build()
		);
		register(MoveBuilder.newMove(HORN_DASHI)
			.name("horn_dashi")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HULLABALOO)
			.name("Hullabaloo")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(HYDRO_BLAST)
			.name("Hydro Blast")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(HYDRO_SLASH)
			.name("Hydro Slash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(HYPER_STING)
			.name("hyper_sting")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(HYPNOTIZE)
			.name("Hypnotize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.SLEEP)
			))
			.build()
		);
		register(MoveBuilder.newMove(ICE_HAMMER)
			.name("Ice Hammer")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ICICLE_FANGS)
			.name("icicle_fangs")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ICICLE_TRAP)
			.name("Icicle Trap")
			.type(ICE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(ICICLE_TREMOR)
			.name("icicle_tremor")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ILL_WILL)
			.name("Ill Will")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(IMPERSONATE)
			.name("Impersonate")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.build()
		);
		register(MoveBuilder.newMove(INCINERATE)
			.name("incinerate")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(130))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(JET_STREAM)
			.name("Jet Stream")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(JOLT)
			.name("Jolt")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(JUDGEMENT)
			.name("Judgement")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(JUMP_START)
			.name("Jump Start")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
					// lose 1/4 hp
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(JUNGLEJUTSU)
			.name("junglejutsu")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(KINDLED_RAGE)
			.name("kindled_rage")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(LANDSLIDE)
			.name("Landslide")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(LAST_LAUGH)
			.name("Last Laugh")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(LAVA_SLASH)
			.name("lava_slash")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(LEAF_BARRAGE)
			.name("Leaf Barrage")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(LIFE_DRAIN)
			.name("Life Drain")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(LIGHT_DASH)
			.name("Light Dash")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(LIGHTSPEED_RAY)
			.name("Lightspeed Ray")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(LUMINOUS_ROAR)
			.name("luminous_roar")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(LUNGE)
			.name("Lunge")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(LUSTER_LOOT)
			.name("Luster Loot")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(MAGMA_BLOCK)
			.name("Magma Block")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(4))
			.build()
		);
		register(MoveBuilder.newMove(MAGMA_BURST)
			.name("magma_burst")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MAGNIFY)
			.name("Magnify")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(2))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MAROON)
			.name("Maroon")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new EnergyDrainEffect(new Constant(1/2))
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MARSH_WAVE)
			.name("marsh_wave")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MEGA_CHOMP)
			.name("Mega Chomp")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(MEGA_KICK)
			.name("Mega Kick")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(KICKING)
			.build()
		);
		register(MoveBuilder.newMove(MEGA_PUNCH)
			.name("Mega Punch")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(PUNCHING)
			.build()
		);
		register(MoveBuilder.newMove(MELTDOWN)
			.name("Meltdown")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(MEMORIZE)
			.name("Memorize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(METAL_BLAST)
			.name("Metal Blast")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(METAL_SHRIEK)
			.name("Metal Shriek")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(METAL_SWIPES)
			.name("Metal Swipes")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(METEOR_CRASH)
			.name("meteor_crash")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(METEOR_SMASH)
			.name("meteor_smash")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MIND_DRAIN)
			.name("Mind Drain")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(MINDS_EYE)
			.name("Minds Eye")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MIRACLE_BEAM)
			.name("Miracle Beam")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(MOLT)
			.name("molt")
			.type(BUG)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.speed(new Constant(2))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MUCK_BLAST)
			.name("Muck Blast")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MUCK_BUCK)
			.name("Muck Buck")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(MUD_SPATTER)
			.name("Mud Spatter")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(55))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MUDSLIDE)
			.name("Mudslide")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.accuracy(new Constant(1))
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(MYSTIC_BREEZE)
			.name("mystic_breeze")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new ConditionalEffect(
							new RandomCondition(new Constant(0.2)),
							ApplyStatChanges.userChangingUser()
								.speed(new Constant(1))
						)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(NAB)
			.name("Nab")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(NATURES_FORCE)
			.name("Natures Force")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(NATURES_RAGE)
			.name("natures_rage")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(NEAR_ENCHANTMENT)
			.name("Near Enchantment")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(NOSEDIVE)
			.name("Nosedive")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(NOVA_BLAST)
			.name("Nova Blast")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(OIL_LEAK)
			.name("Oil Leak")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(OPPRESS)
			.name("oppress")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(OUT_OF_ORDER)
			.name("out_of_order")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(150))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(OUTBURST)
			.name("Outburst")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(125))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PAINT_SHOWER)
			.name("Paint Shower")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PARASITIZE)
			.name("Parasitize")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(PEACE_OF_MIND)
			.name("Peace of Mind")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PEBBLE_TOSS)
			.name("Pebble Toss")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PECK)
			.name("Peck")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PEP_JAB)
			.name("Pep Jab")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PEPPER_BURST)
			.name("Pepper Burst")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PEPPERMINT_BREATH)
			.name("peppermint_breath")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						Schematics.applyStatusToTarget(0.1 / 0.9, StatusId.FROSTBITE)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PERCH)
			.name("Perch")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(PESTER)
			.name("Pester")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PESTILENCE)
			.name("Pestilence")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PETAL_PUMMEL)
			.name("Petal Pummel")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PHANTOM_BLAST)
			.name("Phantom Blast")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PHANTOM_SLASH)
			.name("Phantom Slash")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(PHEROBLAST)
			.name("Pheroblast")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PHOENIX_FLAME)
			.name("phoenix_flame")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(PHOTOSYNTHESIS)
			.name("Photosynthesis")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(PIERCING_DRIVE)
			.name("Piercing Drive")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PILFER)
			.name("Pilfer")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PINE_SHOT)
			.name("Pine Shot")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PLUNGE)
			.name("Plunge")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(POISON)
			.name("Poison")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.BAD_POISON)
			))
			.build()
		);
		register(MoveBuilder.newMove(POISON_BARBS)
			.name("Poison Barbs")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(POUNCE)
			.name("Pounce")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(POWER_DRAW)
			.name("power_draw")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(POWER_FOCUS)
			.name("Power Focus")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.rattack(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(POWER_SIPHON)
			.name("Power Siphon")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						// apply power siphon mechanic
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(POWER_SURGE)
			.name("power_surge")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PRECISION_BOLT)
			.name("Precision Bolt")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PREEMPTIVE_STRIKE)
			.name("Preemptive Strike")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PRIMAL_SLASH)
			.name("Primal Slash")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(PROVOKE)
			.name("Provoke")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(PSYCHAL_CHOMP)
			.name("Psychal Chomp")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(BITING)
			.build()
		);
		register(MoveBuilder.newMove(PSYCHAL_SLAP)
			.name("psychal_slap")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.overrideDefendingStat(StatId.RDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(PSYCHO_BLAST)
			.name("Psycho Blast")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(PUNCH)
			.name("Punch")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(45))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(

					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(PYROKINESIS)
			.name("Pyrokinesis")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(QUICK_POUNCE)
			.name("Quick Pounce")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(QUICK_PUNCH)
			.name("Quick Punch")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(PUNCHING)
			.build()
		);
		register(MoveBuilder.newMove(QUICKSAND)
			.name("Quicksand")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(RADIANT_FORECAST)
			.name("radiant_forecast")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(RADIANT_RUSH)
			.name("Radiant Rush")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(RAGING_FLAME)
			.name("Raging Flame")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.BURN)
			))
			.build()
		);
		register(MoveBuilder.newMove(RAGING_HOWL)
			.name("Raging Howl")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(RAGING_TACKLE)
			.name("Raging Tackle")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(RAINBOW_BLAST)
			.name("Rainbow Blast")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(RANT)
			.name("Rant")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(RAPID_FIRE)
			.name("rapid_fire")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(RAZOR_SLASH)
			.name("razor_slash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(RECYCLE)
			.name("Recycle")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(REFLECTION_BURST)
			.name("Reflection Burst")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(REJUVENATE)
			.name("Rejuvenate")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(REPEATING_TUNE)
			.name("Repeating Tune")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(RESONATE)
			.name("Resonate")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new ConditionalEffect(
							new RandomCondition(new Constant(0.1)),
							ApplyStatChanges.userChangingUser()
								.rattack(new Constant(1))
						)
					))
				))
			))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(RETURN_TO_SENDER)
			.name("Return to Sender")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(REVIVAL)
			.name("revival")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(REVOLUTION)
			.name("revolution")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ROCK_SLIDE)
			.name("Rock Slide")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(ROGUE_ASSAULT)
			.name("Rogue Assault")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ROUGH_UP)
			.name("Rough Up")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new ConditionalNumber(
				new GreaterThan(Value.USER_HEIGHT, Value.TARGET_HEIGHT),
				new Constant(70 * 1.25),
				new Constant(70)
			))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(RUFFLED_FEATHERS)
			.name("ruffled_feathers")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(RUTHLESS_FEAST)
			.name("Ruthless Feast")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SOUL_BURST)
			.build()
		);
		register(MoveBuilder.newMove(SAFEGUARD)
			.name("Safeguard")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(3))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SAP_PLANT)
			.name("Sap Plant")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// apply sap plant mechanic
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(SAVAGE_GREED)
			.name("savage_greed")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SAVAGE_HORN)
			.name("savage_horn")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SCAPEGOAT)
			.name("Scapegoat")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SCAVENGE)
			.name("Scavenge")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SEA_SONG)
			.name("sea_song")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(PIVOT)
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(SEARING_STEEL)
			.name("searing_steel")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(85))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SECOND_WIND)
			.name("second_wind")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SHADOW_LURK)
			.name("shadow_lurk")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHADOW_PULSE)
			.name("shadow_pulse")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHADOW_SPRINT)
			.name("Shadow Sprint")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SHADOWBOX)
			.name("Shadowbox")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHARPEN)
			.name("Sharpen")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.accuracy(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHATTER)
			.name("shatter")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SHELL_BASH)
			.name("shell_bash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHELL_SHRAPNEL)
			.name("shell_shrapnel")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHINE)
			.name("Shine")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHREWD_BLAST)
			.name("SHrewd Blast")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SHRIEK)
			.name("Shriek")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(SINGE)
			.name("Singe")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SINISTER_SHACKLES)
			.name("Sinister Shackles")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SLAP_DOWN)
			.name("Slap Down")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(SLASH)
			.name("Slash")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(SLEET_SHOT)
			.name("Sleet Shot")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(55))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SLIME)
			.name("Slime")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SLUMBER)
			.name("Slumber")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SNORE_SPORES)
			.name("Snore Spores")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.SLEEP)
			))
			.addTag(POWDER)
			.build()
		);
		// snot shot
		register(MoveBuilder.newMove(SNOWDOZER)
			.name("Snowdozer")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SOFT_WATER)
			.name("Soft Water")
			.type(WATER)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SOLAR_PUNCH)
			.name("solar_punch")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SOUL_DRAIN)
			.name("soul_drain")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.addTag(HEALTH_DRAIN)
			.build()
		);
		register(MoveBuilder.newMove(SOUL_HARVEST)
			.name("soul_harvest")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SOUL_STORM)
			.name("Soul Storm")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SPARE)
			.name("Spare")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SPECTRAL_BURST)
			.name("Spectral Burst")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SPIKE_RUSH)
			.name("Spike Rush")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SPIT)
			.name("Spit")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SPIT_OUT)
			.name("spit_out")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SPITE)
			.name("Spite")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SPLITTING_HEADACHE)
			.name("Splitting Headache")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.priority(new Constant(1))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SPRAY)
			.name("Spray")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SQUARE_ONE)
			.name("Square One")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SQUAWK)
			.name("Squawk")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(STAMPEDE)
			.name("Stampede")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(STARE)
			.name("Stare")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(STARTLE)
			.name("Startle")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(STATIC_SHOCK)
			.name("Static Shock")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(STATIC_SPORES)
			.name("Static Spores")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
			))
			.addTag(POWDER)
			.build()
		);
		register(MoveBuilder.newMove(STEEL_CRUSHER)
			.name("Steel Crusher")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new ConditionalEffect(
							new RandomCondition(new Constant(0.1)),
							ApplyStatChanges.userChangingUser()
								.mattack(new Constant(1))
						)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(STEEL_TRAP)
			.name("steel_trap")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(STING)
			.name("Sting")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(STORM_SUMMON)
			.name("storm_summon")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(STRATAGEM)
			.name("Stratagem")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.overrideAttackingStat(StatId.RDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(STRETCH)
			.name("Stretch")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.speed(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(STRIKE)
			.name("Strike")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(STUN)
			.name("Stun")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
			))
			.build()
		);
		register(MoveBuilder.newMove(STUPEFY)
			.name("Stupefy")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(SUBMERGE)
			.name("submerge")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SUMMON)
			.name("Summon")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SWAT)
			.name("Swat")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(45))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(SWIFTEN)
			.name("swiften")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(SWIPE)
			.name("Swipe")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(TAIL_SLAP)
			.name("Tail Slap")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(TAKE_ROOT)
			.name("Take Root")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TALENT_TRADE)
			.name("Talent Trade")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TAMP)
			.name("Tamp")
			.type(EARTH)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mdefense(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TASE)
			.name("Tase")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(TEAMWORK)
			.name("Teamwork")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TEAR_DOWN)
			.name("Tear Down")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TEASE)
			.name("Tease")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TELEKINETIC_SWEEP)
			.name("telekinetic_sweep")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(TEMPEST)
			.name("tempest")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(THISTLE_SLASH)
			.name("Thistle Slash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(THORN_SLAM)
			.name("thorn_slam")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(THUNDER_BLAST)
			.name("Thunder Blast")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(THUNDER_CHOMP)
			.name("Thunder Chomp")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(THUNDER_CLAP)
			.name("Thunder Clap")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(THUNDER_CRASH)
			.name("Thunder Crash")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(THUNDER_SLAM)
			.name("Thunder Slam")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(THUNDERSTORM)
			.name("ThunderStorm")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(THUNDERSTRIKE)
			.name("Thunderstrike")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TIMBER_THRASH)
			.name("Timber Thrash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(TONE_BARRIER)
			.name("Tone Barrier")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.priority(new Constant(4))
			.addTag(SOUND)
			.build()
		);
		register(MoveBuilder.newMove(TORRENTIAL_SLASH)
			.name("torrential_slash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(TOXIBLAST)
			.name("toxiblast")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TOXIC_SPORES)
			.name("Toxic Spores")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.POISON)
			))
			.addTag(POWDER)
			.build()
		);
		register(MoveBuilder.newMove(TOXIC_TAIL)
			.name("Toxic Tail")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(TRIBUTE)
			.name("Tribute")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
			.build()
		);
		register(MoveBuilder.newMove(TRICK_SHOT)
			.name("trick_shot")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TRICKY_TACTICS)
			.name("Tricky Tactics")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TRIP_ROOT)
			.name("trip_root")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(0)) // TODO
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TSUNAMI)
			.name("Tsunami")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TUNE_UP)
			.name("Tune Up")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rdefense(new Constant(1))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TWIRLY_WHIRL)
			.name("twirly_whirl")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new ConditionalEffect(
							new RandomCondition(new Constant(0.5)),
							ApplyStatChanges.userChangingUser()
								.rattack(new Constant(1))
						)
					))
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(TYPHOON)
			.name("Typhoon")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(UNDERMINE)
			.name("Undermine")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(VENOM_CHOMP)
			.name("Venom Chomp")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BAD_POISON)
					))
				))
			))
			.addTag(BITING)
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(VENOM_SLASH)
			.name("Venom Slash")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.addTag(CONTACT)
			.addTag(SLASHING)
			.build()
		);
		register(MoveBuilder.newMove(VICE_JAWS)
			.name("Vice Jaws")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(VICIOUS_SNAP)
			.name("vicious_snap")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(VINE_LASH)
			.name("Vine Lash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(VITAL_JAB)
			.name("Vital Slash")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(80))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(VITAL_SURGE)
			.name("vital_surge")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(110))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(WATER_BOMB)
			.name("Water Bomb")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(WAVE_WRECKER)
			.name("Wave Wrecker")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(WEB_SHOT)
			.name("Web Shot")
			.type(BUG)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(WING_SLAP)
			.name("Wing Slap")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.addTag(SLAPPING)
			.build()
		);
		register(MoveBuilder.newMove(WINTERS_FURY)
			.name("winters_fury")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(WONDER_HERB)
			.name("Wonder Herb")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.build()
		);
		register(MoveBuilder.newMove(YETIS_WRATH)
			.name("yetis_wrath")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(0))
			.basePower(new Constant(120))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(CONTACT)
			.build()
		);
		register(MoveBuilder.newMove(ZIP_ZAP)
			.name("Zip Zap")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.addTag(PIVOT)
			.build()
		);
    }
}
