package github.snomfish.domain.move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.domain.mechanics.MechanicId;
import github.snomfish.domain.move.structures.MoveEffect;
import github.snomfish.domain.stats.StatId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.functionality.Schematics;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.condition.AccuracyCheck;
import github.snomfish.functionality.condition.And;
import github.snomfish.functionality.condition.Comparator;
import github.snomfish.functionality.condition.Conditions;
import github.snomfish.functionality.condition.Equals;
import github.snomfish.functionality.condition.RandomCondition;
import github.snomfish.functionality.effect.ConditionalEffect;
import github.snomfish.functionality.effect.EffectSequence;
import github.snomfish.functionality.effect.Effects;
import github.snomfish.functionality.effect.effects.ApplyMechanic;
import github.snomfish.functionality.effect.effects.ApplyStatChanges;
import github.snomfish.functionality.effect.effects.DealFormulaDamage;
import github.snomfish.functionality.effect.effects.EnergyDrainEffect;
import github.snomfish.functionality.effect.effects.HealthDrainEffect;
import github.snomfish.functionality.effect.effects.NoEffect;
import github.snomfish.functionality.effect.effects.RecoilEffect;
import github.snomfish.functionality.effect.effects.SetType;
import github.snomfish.functionality.effect.effects.SetWeather;
import github.snomfish.functionality.number.ConditionalNumber;
import github.snomfish.functionality.number.Constant;
import github.snomfish.functionality.number.ValueConstant;

import static github.snomfish.domain.move.CategoryId.*;
import static github.snomfish.domain.move.MoveId.*;
import static github.snomfish.domain.move.MoveTagId.*;
import static github.snomfish.domain.type.TypeId.*;

public class MoveRegistry {


    private static final Map<MoveId, Move> registry = new HashMap<>();


    private MoveRegistry() {}


	public static Map<MoveId, Move> registry() {return registry;}
    public static Move get(MoveId id) {return registry.get(id);}


	private static void register(Move move) {
		if (registry.containsKey(move.id())) throw new RuntimeException("MoveRegistry attempted to register duplicate entry \"" + move.id().name() + "\"");
		registry.put(move.id(), move);
	}


    static {
		
		register(Move.build()
			.id(NO_MOVE)
			.name("No Move")
		);
		register(Move.build()
			.id(ACCELERATE)
			.name("Accerlerate")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingUser()
					.speed(new Constant(2))
			))
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_AIR)
			.name("adaptive_assault_air")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(
					new DealFormulaDamage(),
					Effects.flinchChance(new Constant(0.2))
				)
			))
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_ANCIENT)
			.name("adaptive_assault_ancient")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
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
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_BRAWLER)
			.name("adaptive_assault_brawler")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.critChance(1)
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_BUG)
			.name("adaptive_assault_bug")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
					// TODO apply stinger mechanic
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_FIRE)
			.name("adaptive_assault_fire")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_ICE)
			.name("adaptive_assault_ice")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.FROSTBITE)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_SIMPLE)
			.name("adaptive_assault_simple")
			.type(SIMPLE)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_SPIRIT)
			.name("adaptive_assault_spirit")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
						Effects.dazeChance(new Constant(0.2))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_TOXIC)
			.name("adaptive_assault_toxic")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BAD_POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(ADAPTIVE_ASSAULT_WATER)
			.name("adaptive_assault_water")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
					// TODO add clears all status ailments from users party effect
				))
			))
		);
		register(Move.build()
			.id(AGGRAVATE)
			.name("aggravate")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(AIM)
			.name("Aim")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(10))
			// TODO: apply aim mechanic that makes a move skip accuracy check then removes itself
		);
		register(Move.build()
			.id(AIR_BLADE)
			.name("Air Blade")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.critChance(1)
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SLASHING)
		);
		register(Move.build()
			.id(AIR_SHOT)
			.name("Air Shot")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(AIR_WHIP)
			.name("Air Whip")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(
					new DealFormulaDamage(),
					Effects.flinchChance(new Constant(0.3))
				)
			))
		);
		register(Move.build()
			.id(AIRSTRIKE)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(ALL_OR_NOTHING)
			.name("All or Nothing")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(-3))
			.energyCost(new Constant(60))
			.basePower(new Constant(150))
			.onTurnStart(new MoveEffect(
				ApplyMechanic.toUser(MechanicId.ALL_OR_NOTHING_FOCUSING)
			))
			.condition(new And(
				new AccuracyCheck(new Constant(100)),
				new Equals<>(Value.USER().HAS_MECHANIC(MechanicId.ALL_OR_NOTHING_FOCUSING), true)
			))
			.onHit(new MoveEffect(
				new DealFormulaDamage()
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ANCIENT_ROAR)
			.name("ancient_roar")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingTarget()
					.mattack(new Constant(-1))
					.rattack(new Constant(-1))
			))
			// TODO add pivoting effect and make it reliant on the stat drops being successful
			.tag(PIVOT)
			.tag(SOUND)
		);
		// TODO add aura burst
		register(Move.build()
			.id(APPARITION)
			.name("apparition")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(55))
			.basePower(new Constant(90))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
					// TODO make it two turn
				))
			))
		);
		register(Move.build()
			.id(AQUA_JAWS)
			.name("Aqua Jaws")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(38))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ARMOUR_DOWN)
			.name("Armour Down")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingUser()
					.speed(new Constant(2))	
			))
		);
		register(Move.build()
			.id(AUGMENT)
			.name("Augment")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(AURA_BURST)
			.name("Aura Burst")
			.type(BRAWLER)
			.category(RANGED)
		);
		register(Move.build()
			.id(BAFFLE)
			.name("Baffle")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.tag(PIVOT)
		);
		register(Move.build()
			.id(BAG_OF_TRICKS)
			.name("Bag of Tricks")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(BAIT)
			.name("Bait")
			.type(SIMPLE)
			.category(STATUS)
			.priority(new Constant(2))
			.energyCost(new Constant(30))
		);
		register(Move.build()
			.id(BAMBOOZLE)
			.name("Bamboozle")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BANANA_SLAM)
			.name("banana_slam")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(95))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BANANA_SPLIT)
			.name("Banana Split")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(100))
		);
		register(Move.build()
			.id(BANE_OF_HASTE)
			.name("Bane of Haste")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(50))
			.priority(new Constant(-7))
		);
		register(Move.build()
			.id(BANE_OF_WEILDING)
			.name("Bane of Weilding")
			.type(ANCIENT)
			.category(STATUS)
		);
		register(Move.build()
			.id(BANEFUL_BASH)
			.name("Baneful Bash")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
		);
		// TODO add baneful gnash
		register(Move.build()
			.id(BARB_TRAP) // MAN I HAVE NO IDEA, MAYBE HAVE THIS APPLY A ONE TURN MECHANIC ON THE USER????
			.name("barb_trap")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10% of max energy
		);
		register(Move.build()
			.id(BARBS)
			.name("Barbs")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(20))
		);
		register(Move.build()
			.id(BARTER)
			.name("Barter")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(50))
		);
		register(Move.build()
			.id(BASH)
			.name("Bash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BATTERING_RAM)
			.name("Battering Ram")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.attackingStat(StatId.MDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BATTLE_CHIME)
			.name("Battle Chime")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(50))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// remove half of the user's health
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(20))
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(BATTLE_HORN)
			.name("Battle Horn")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.mdefense(new Constant(1))
					// heal the status effects off the party
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(BEASTS_SLUMBER)
			.name("beasts_slumber")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(65))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// applys beast slumber mechanic
				))
			))
		);
		register(Move.build()
			.id(BEQUEATH)
			.name("Bequeath")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(50))
			.priority(new Constant(-6))
		);
		register(Move.build()
			.id(BINDING_OATH)
			.name("binding oath")
		);
		register(Move.build()
			.id(BITTER_COLD)
			.name("Bitter Cold")
			.type(ICE)
			.category(STATUS)
			.energyCost(new Constant(35))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.FROSTBITE)
			))
		);
		register(Move.build()
			.id(BLAZE_CHOMP)
			.name("Blaze Chomp")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BLAZE_OF_GLORY)
			.name("Blaze of Glory")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						RecoilEffect.onUser(new Constant(1/3))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(BLAZE_PUNCH)
			.name("blaze_punch")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BLAZE_RUSH)
			.name("Blaze Rush")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.speed(new Constant(1))	
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BLINDSPOT_BATTER)
			.name("Blindspot Batter")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(38))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		// TODO add bloodmeal
		register(Move.build()
			.id(BLOOD_DRAIN)
			.name("blood_drain")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(20))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						// apply blood drain mechanic
					))
				))
			))
			.tag(CONTACT)
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(BLOODMEAL)
			.name("bloodmeal")
		);
		register(Move.build()
			.id(BLUDGEON)
			.name("Bludgeon")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(BLUSTER_BURST)
			.name("bluster burst")
		);
		register(Move.build()
			.id(BODY_CRASH)
			.name("Body Crash")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.attackingStat(StatId.MDEFENSE)
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BODY_SLAM)
			.name("Body Slam")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BOILING_PRESS)
			.name("Boiling Press")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BONE_CRUNCH)
			.name("bone_crunch")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(46))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(BOOM_BASH)
			.name("Boom Bash")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(KICKING)
		);
		register(Move.build()
			.id(BOULDER_BLAST)
			.name("Boulder Blast")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(25))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(BRACE)
			.name("Brace")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingTarget()
						.mdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(BRAIN_EXERCISE)
			.name("Brain Exercise")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.mdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(BRAIN_FREEZE)
			.name("brain_freeze")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(44))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(BRAINWASH)
			.name("Brainwash")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(24))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
		);
		register(Move.build()
			.id(BRAWN_BOOST)
			.name("Brawn Boost")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(2))
				))
			))
		);
		register(Move.build()
			.id(BRIAR_BLOCK)
			.name("Briar Block")
			.type(PLANT)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10%
			.priority(new Constant(4))
		);
		register(Move.build()
			.id(BUCK_UP)
			.name("Buck Up")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(20))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.critChance(new Constant(2))		
				))
			))
		);
		register(Move.build()
			.id(BUG_BITE)
			.name("Bug Bite")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(CONTACT)
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(BULK_UP)
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
		);
		// TODO add burn true
		register(Move.build()
			.id(BURN_UP)
			.name("Burn Up")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
					// clear hazards
				))
			))
		);
		register(Move.build()
			.id(BURN_TRUE)
			.name("burn true")
		);
		register(Move.build()
			.id(BURROW)
			.name("Burrow")
			.type(EARTH)
			.category(STATUS)
			.energyCost(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(BUSH_WHACK)
			.name("Bush Whack")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.3))
					))
				))
			))
		);
		register(Move.build()
			.id(CALAMITOUS_CRASH)
			.name("calamitous crash")
		);
		register(Move.build()
			.id(CANISTER_BURST)
			.name("Canister Burst")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(95)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		// TODO add calamitous crash
		register(Move.build()
			.id(CAPRIOLE)
			.name("capriole")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(52))
			.basePower(new Constant(105))
			.condition(new AccuracyCheck(new Constant(85)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(KICKING)
		);
		register(Move.build()
			.id(CARNIVOROUS_SNAP)
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
			.tag(CONTACT)
			.tag(HEALTH_DRAIN)
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(CEREBRAL_SLASH)
			.name("Cerebral Slash")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.critChance(1)
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SLASHING)
		);
		register(Move.build()
			.id(CHARADE)
			.name("Charade")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(CHARRED_CLAWS)
			.name("Charred Claws")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(CHASE_DOWN)
			.name("Chase Down")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CHILLY_CHOMP)
			.name("Chilly Chomp")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE),
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CHOMP)
			.name("Chomp")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CHOP)
			.name("Chop")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.3))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CLAMP)
			.name("Clamp")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(22))
			.basePower(new Constant(45))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CLAMP_CLAW)
			.name("clamp_claw")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(CLAWBER)
			.name("clawber")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/3))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(CLAY_SLAP)
			.name("Clay Slap")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(KICKING)
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(CLEAN_UP)
			.name("clean_up")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(50))
		);
		// this may not work as I am attempting to set the move values before selective a move
		register(Move.build()
			.id(CLIMATE_CANNON)
			.name("Climate Cannon")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new ConditionalEffect(Conditions.clearWeather(), 		SetType.move(SIMPLE)),
					new ConditionalEffect(Conditions.fogActive(), 			SetType.move(SPIRIT)),
					new ConditionalEffect(Conditions.heatwaveActive(), 		SetType.move(FIRE)),
					new ConditionalEffect(Conditions.rainActive(), 			SetType.move(WATER)),
					new ConditionalEffect(Conditions.strongWindsActive(), 	SetType.move(AIR)),
					new ConditionalEffect(Conditions.thunderstormActive(), 	SetType.move(ELECTRIC)),
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(COCONUT_BOMB)
			.name("Coconut Bomb")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						RecoilEffect.onUser(new Constant(1/4))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(COMPLETE_DEMOLITION)
			.name("complete_demolition")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(150))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CONCLUSION)
			.name("conclusion")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(40))
			.tag(SOUND)
		);
		register(Move.build()
			.id(CONFOUND)
			.name("Confound")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(18))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
		);
		register(Move.build()
			.id(CORRODE)
			.name("Corrode")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(24))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(COUNTER_ASSAULT)
			.name("counter assault")
		);
		register(Move.build()
			.id(COUNTER_POISE)
			.name("Counter Poise")
			.type(BUG)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				/*new ConditionalEffect(
					new Equals<>()
					new DealFormulaDamage()
				)*/
				new NoEffect()
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(COUNTER_STANCE)
			.name("counter_stance")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				/*new ConditionalEffect(
					new Equals<>()
					new DealFormulaDamage()
				)*/
				new NoEffect()
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CYCLONE_SLAM)
			.name("Cyclone Slam")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(CYCLONE_VORTEX)
			.name("cyclone vortex")
		);
		register(Move.build()
			.id(DARK_SURGE)
			.name("Dark Surge")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
		);
		register(Move.build()
			.id(DART_BURST)
			.name("dart_burst")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(15))
			.condition(new AccuracyCheck(new Constant(85)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(DAWDLE)
			.name("Dawdle")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(10))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(DAYDREAM)
			.name("Daydream")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(0))
		);
		register(Move.build()
			.id(DAZZLING_BARRAGE)
			.name("Dazzling Barrage")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(25))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DEEP_FREEZE)
			.name("Deep Freeze")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(DEJA_VU)
			.name("Deja Vu")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(30))
		);
		register(Move.build()
			.id(DINO_CLAWS)
			.name("Dino Claws")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(24))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.3))
					))
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(DISCIPLINE)
			.name("discipline")
		);
		register(Move.build()
			.id(DISCORDANCE)
			.name("Discordance")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(DISPERSE)
			.name("Disperse")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.accuracy(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(DISSIPATE)
			.name("Dissipate")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(45))
			.priority(new Constant(-6))
			.tag(PIVOT)
		);
		register(Move.build()
			.id(DISSONANT_CHORD)
			.name("Dissonant Chord")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(DIVE_BOMB)
			.name("Dive Bomb")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DODGE)
			.name("Dodge")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10%
			.priority(new Constant(4))
		);
		register(Move.build()
			.id(DOUBLE_BEAT)
			.name("double_beat")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DOUBLE_STING) // NEEDS TO BE MADE INTO A BARRAGE
			.name("double_sting")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(28))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DOUBLE_SWIPE)
			.name("Double Swipe")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DOUBLE_WHACK)
			.name("Double Whack")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(34))
			.basePower(new Constant(35))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DOUSE)
			.name("douse")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(DOWNPOUR)
			.name("Dounpour")
			.type(WATER)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				SetWeather.userSetting(
					WeatherId.RAIN,
					new Constant(5)
				)
			))
		);
		register(Move.build()
			.id(DRACO_BEAM)
			.name("Draco Beam")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(130))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(DRAGON_HORN)
			.name("Dragon Horn")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(80)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DRIVING_FORCE)
			.name("Driving Force")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(-6))
			.energyCost(new Constant(40))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(DRUDGE)
			.name("Drudge")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.speed(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(DUAL_FROSTBLADE)
			.name("dual frostblade")
		);
		register(Move.build()
			.id(EAR_SLAP)
			.name("Ear Slap")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(18))
			.basePower(new Constant(15))
			.condition(new AccuracyCheck(new Constant(85)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(EARTHQUAKE)
			.name("Earthquake")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(EERIE_STARE)
			.name("Eerie Stare")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(EGG_LOB)
			.name("egg_lob")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(46))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(EGG_ONSLAUGHT)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(EGG_STORM)
			.name("egg_storm")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(52))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
		);
		register(Move.build()
			.id(ELECTRO_BLAST)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(ELECTRO_PUNCH)
			.name("Electro Punch")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS),
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
			.tag(PUNCHING)
		);
		register(Move.build()
			.id(ELECTROBURST)
			.name("electroburst")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(54))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS),
						Schematics.applyStatusToTarget(0.1 / 0.9, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(ELEMENTAL_BURST)
			.name("elemental_burst")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
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
		);
		register(Move.build()
			.id(EMPATHIZE)
			.name("Empathize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(EMULATE)
			.name("Emulate")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(ENAMOR)
			.name("Enamour")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(ENERGIZE)
			.name("Energize")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(ENERGY_BREAK)
			.name("Energy Break")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(54))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new EnergyDrainEffect(new Constant(1/2))
					))
				))
			))
		);
		register(Move.build()
			.id(ENERGY_SURGE)
			.name("Energy Surge")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(ERUPTION)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(EXAMINE)
			.name("Examine")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(15))
		);
		register(Move.build()
			.id(EXPERT_ONSLAUGHT)
			.name("expert_onslaught")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
				SetType.move(Value.USER().PRIMARY_TYPE()),
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(EXPLOSION)
			.name("explosion")
		);
		register(Move.build()
			.id(FADE_AWAY)
			.name("Fade Away")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(PIVOT)
		);
		register(Move.build()
			.id(FAR_ENCHANTMENT)
			.name("Far Enchantment")
			.type(ANCIENT)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(FIERY_FONDANT)
			.name("fiery_fondant")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.mdefense(new Constant(1)),
						RecoilEffect.onUser(new Constant(1/4))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(FINAL_RUSE)
			.name("Final Ruse")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(FIRE_BREATH)
			.name("Fire Breath")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(FIRE_SLAM)
			.name("Fire Slam")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(FLABBERGAST)
			.name("Flabbergast")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(FLAMING_KICK)
			.name("Flaming Kick")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.tag(CONTACT)
			.tag(KICKING)
		);
		register(Move.build()
			.id(FLARE)
			.name("flare")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(FLARE_BLAST)
			.name("Flare Blast")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(FLASH)
			.name("Flash")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Effects.daze()
			))
		);
		register(Move.build()
			.id(FLASH_N_FLEE)
			.name("Flash n Flee")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(PIVOT)
		);
		register(Move.build()
			.id(FLASH_PULSE)
			.name("Flash Pulse")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(42))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.2))
					))
				))
			))
		);
		register(Move.build()
			.id(FLORA_BLAST)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(FLURRY)
			.name("Flurry")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(22))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
		);
		register(Move.build()
			.id(FOCUS_BEAM)
			.name("Focus Beam")
		);
		register(Move.build()
			.id(FORGED_CLAWS)
			.name("Forged Claws")
		);
		register(Move.build()
			.id(FOUL_CHANT)
			.name("foul_chant")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(70)))
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
		);
		register(Move.build()
			.id(FOUNTAIN_BEAM)
			.name("Fountain Beam")
		);
		register(Move.build()
			.id(FRIGHTFUL_SUPRISE)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(FRIGID_FRENZY)
			.name("Frigid Frenzy")
		);
		register(Move.build()
			.id(FROST_BEAM)
			.name("Frost Beam")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE)
					))
				))
			))
		);
		register(Move.build()
			.id(FROST_BLAST)
			.name("Frost Blast")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.FROSTBITE)
					))
				))
			))
		);
		register(Move.build()
			.id(FROST_BREATH)
			.name("Frost Breath")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(38))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(FROST_PUNCH)
			.name("Frost Punch")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.FROSTBITE),
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
			.tag(PUNCHING)
		);
		register(Move.build()
			.id(FULGER_FANGS)
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
			.tag(CONTACT)
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(FUNGUS_CURSE)
			.name("Fungus Curse")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(FUSION_BARRAGE)
			.name("Fusion Barrage")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(0))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					SetType.move(ELECTRIC),
					new DealFormulaDamage(),
					SetType.move(FIRE),
					new DealFormulaDamage(),
					SetType.move(WATER),
					new DealFormulaDamage(),
					SetType.move(DARK),
					new DealFormulaDamage()
				))
			))
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(GAIN_DRAIN)
			.name("Gain Drain")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(30))
		);
		register(Move.build()
			.id(GALE_FORCE)
			.name("Gale Force")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				SetWeather.userSetting(
					WeatherId.STRONG_WINDS,
					new Constant(5)
				)
			))
		);
		register(Move.build()
			.id(GAMMA_PULSE)
			.name("Gamma Pulse")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(GEYSER)
			.name("geyser")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(GHOSTLY_HOWL)
			.name("ghostly_howl")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(38))
			.basePower(new Constant(75))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(GIVE_UP)
			.name("Give Up")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(0))
			.basePower(new Constant(1))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(GLOOM_FANGS)
			.name("gloom_fangs")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(GLOOMINOUS_FANGS)
			.name("gloominous_fangs")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(46))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(GLOOMINOUS_ROAR)
			.name("Gloominous Roar")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(46))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(GLOW_BEAM)
			.name("Glow Beam")
		);
		register(Move.build()
			.id(GNARLY_GASH)
			.name("Gnarly Gash")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(GNAW)
			.name("Gnaw")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(GOBBLE_GOOP)
			.name("gobble_goop")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(GOOP_SPOUT)
			.name("goop_spout")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(35))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.POISON)
			))
		);
		register(Move.build()
			.id(GRAFFITI)
			.name("graffiti")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(GRAPPLE_GRIP)
			.name("Grapple Grip")
		);
		register(Move.build()
			.id(GROWL)
			.name("Growl")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(10))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(GULP)
			.name("gulp")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(GUST)
			.name("Gust")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// clear hazards
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(HARMONIOUS_CHORD)
			.name("Harmonious Chord")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.SLEEP)
					))
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(HARROW)
			.name("Harrow")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HARVEST)
			.name("Harvest")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(72))
		);
		register(Move.build()
			.id(HASTEN)
			.name("Hasten")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.speed(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(HAZY_SHROUD)
			.name("Hazy Shroud")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				SetWeather.userSetting(
					WeatherId.FOG,
					new Constant(5)
				)
			))
		);
		register(Move.build()
			.id(HEADBUTT)
			.name("Headbutt")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(25))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HEALTH_GIFT)
			.name("Health Gift")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(HEART_BREAK)
			.name("Heart Break")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(25))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.5))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HEAT_WAVE)
			.name("Heat Wave")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				SetWeather.userSetting(
					WeatherId.HEATWAVE,
					new Constant(5)
				)
			))
		);
		register(Move.build()
			.id(HEAVY_BASH)
			.name("Heavy Bash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HEEDLESS_RUSH) // TODO: give this perfect accuracy and bypass dodge
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
			.tag(CONTACT)
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(HELLSTORM)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(HERBAL_REMEDY)
			.name("herbal_remedy")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(70))
		);
		register(Move.build()
			.id(HIGH_PITCH_SCREECH)
			.name("High Pitch Screech")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(HOLY_SLASH)
			.name("holy_slash")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SLASHING)
		);
		register(Move.build()
			.id(HOODWINK)
			.name("hoodwink")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(35))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				Effects.daze()
			))
		);
		register(Move.build()
			.id(HOOK_WAVE)
			.name("Hook Wave")
		);
		register(Move.build()
			.id(HOP_KICK)
			.name("Hop Kick")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(KICKING)
		);
		register(Move.build()
			.id(HORN_DASHI)
			.name("horn_dashi")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(-6))
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HULLABALOO)
			.name("Hullabaloo")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(HYDRO_BLAST)
			.name("Hydro Blast")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(HYDRO_SLASH)
			.name("Hydro Slash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(HYPER_STING)
			.name("hyper_sting")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(HYPNOTIZE)
			.name("Hypnotize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(40))
			.condition(new AccuracyCheck(new Constant(90)))
		);
		register(Move.build()
			.id(ICE_HAMMER)
			.name("Ice Hammer")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(ICICLE_FANGS)
			.name("icicle_fangs")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ICICLE_TRAP)
			.name("Icicle Trap")
			.type(ICE)
			.category(STATUS)
			.energyCost(new Constant(45))
		);
		register(Move.build()
			.id(ICICLE_TREMOR)
			.name("icicle_tremor")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(ILL_WILL)
			.name("Ill Will")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(IMPERSONATE)
			.name("Impersonate")
			.type(SPIRIT)
			.category(STATUS)
			.priority(new Constant(1))
			.energyCost(new Constant(45))
			.priority(new Constant(1))
		);
		register(Move.build()
			.id(INCINERATE)
			.name("incinerate")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(130))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(JET_STREAM)
			.name("Jet Stream")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(JOLT)
			.name("Jolt")
			.type(ELECTRIC)
			.category(RANGED)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(JUDGEMENT)
			.name("Judgement")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
		);
		register(Move.build()
			.id(JUMP_START)
			.name("Jump Start")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(40))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
					// lose 1/4 hp
				))
			))
		);
		register(Move.build()
			.id(JUNGLEJUTSU)
			.name("junglejutsu")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(KINDLED_RAGE)
			.name("kindled_rage")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(LANDSLIDE)
			.name("Landslide")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(LASH_OUT)
			.name("Lash Out")
		);
		register(Move.build()
			.id(LAST_LAUGH)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(LAVA_SLASH)
			.name("lava_slash")
			.type(FIRE)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.BURN)
					))
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(LEAF_BARRAGE)
			.name("Leaf Barrage")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(LIFE_DRAIN)
			.name("Life Drain")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(48))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(LIGHT_DASH)
			.name("Light Dash")
			.type(LIGHT)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(LIGHTSPEED_RAY)
			.name("Lightspeed Ray")
			.type(LIGHT)
			.category(RANGED)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(LUMINOUS_ROAR)
			.name("luminous_roar")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(36))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(LUNGE)
			.name("Lunge")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(LUSTER_LOOT)
			.name("Luster Loot")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(MAGMA_BLOCK)
			.name("Magma Block")
			.type(FIRE)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10%
			.priority(new Constant(4))
		);
		register(Move.build()
			.id(MAGMA_BURST)
			.name("magma_burst")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(MAGNIFY)
			.name("Magnify")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(2))
				))
			))
		);
		register(Move.build()
			.id(MANDIBLE_CRUSHER)
			.name("Mandible Crusher")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(54))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(0.8)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(MAROON)
			.name("Maroon")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new EnergyDrainEffect(new Constant(1/2))
					))
				))
			))
		);
		register(Move.build()
			.id(MARSH_WAVE)
			.name("marsh_wave")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(MASS_IMPACT)
			.name("Mass Impact")
		);
		register(Move.build()
			.id(MEGA_CHOMP)
			.name("Mega Chomp")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(MEGA_KICK)
			.name("Mega Kick")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(90)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(KICKING)
		);
		register(Move.build()
			.id(MEGA_PUNCH)
			.name("Mega Punch")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
			.tag(PUNCHING)
		);
		register(Move.build()
			.id(MELTDOWN)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(MEMORIZE)
			.name("Memorize")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(METAL_BLAST)
			.name("Metal Blast")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(42))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(METAL_SHRIEK)
			.name("Metal Shriek")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(METAL_SWIPES)
			.name("Metal Swipes")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(20))
			.condition(new AccuracyCheck(new Constant(95)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(METEOR_CRASH)
			.name("meteor_crash")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(METEOR_SMASH)
			.name("meteor_smash")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(MIND_DRAIN)
			.name("Mind Drain")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(48))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(MINDS_EYE)
			.name("Minds Eye")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(MIRACLE_BEAM)
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
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(MOLT)
			.name("molt")
			.type(BUG)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.speed(new Constant(2))
				))
			))
		);
		register(Move.build()
			.id(MUCK_BLAST)
			.name("Muck Blast")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(MUCK_BUCK)
			.name("Muck Buck")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/4))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(MUD_SPATTER)
			.name("Mud Spatter")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(30))
			.basePower(new Constant(55))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(MUDSLIDE)
			.name("Mudslide")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						ApplyStatChanges.userChangingUser()
							.accuracy(new Constant(1))
					))
				))
			))
		);
		register(Move.build()
			.id(MYSTIC_BREEZE)
			.name("mystic_breeze")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(30))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
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
		);
		register(Move.build()
			.id(NAB)
			.name("Nab")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(NATURES_FORCE)
			.name("Natures Force")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(NATURES_RAGE)
			.name("natures_rage")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(NEAR_ENCHANTMENT)
			.name("Near Enchantment")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(NOSEDIVE)
			.name("Nosedive")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/4))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(NOVA_BLAST)
			.name("Nova Blast")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(52))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(OIL_LEAK)
			.name("Oil Leak")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(10))
		);
		register(Move.build()
			.id(OPPRESS)
			.name("oppress")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(45))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(OUT_OF_ORDER)
			.name("out_of_order")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(150))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(OUTBURST)
			.name("Outburst")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(125))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PAINT_SHOWER)
			.name("Paint Shower")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PARASITIZE)
			.name("Parasitize")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(PARASOMNIA)
			.name("Parasomnia")
		);
		register(Move.build()
			.id(PEACE_OF_MIND)
			.name("Peace of Mind")
			.type(MIND)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(PEBBLE_TOSS)
			.name("Pebble Toss")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(15))
			.basePower(new Constant(30))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PECK)
			.name("Peck")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(35))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PEP_JAB)
			.name("Pep Jab")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PEPPER_BURST)
			.name("Pepper Burst")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(35))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PEPPERMINT_BREATH)
			.name("peppermint_breath")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN),
						Schematics.applyStatusToTarget(0.1 / 0.9, StatusId.FROSTBITE)
					))
				))
			))
		);
		register(Move.build()
			.id(PERCH)
			.name("Perch")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(72))
		);
		register(Move.build()
			.id(PESTER)
			.name("Pester")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(32))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PESTILENCE)
			.name("Pestilence")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PETAL_PUMMEL)
			.name("Petal Pummel")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PHANTOM_BLAST)
			.name("Phantom Blast")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PHANTOM_SLASH)
			.name("Phantom Slash")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(PHEROBLAST)
			.name("Pheroblast")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PHOENIX_FLAME)
			.name("phoenix_flame")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(0)) // 0 is the display but it takes all of your energy
		);
		register(Move.build()
			.id(PHOTOSYNTHESIS)
			.name("Photosynthesis")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(72))
		);
		register(Move.build()
			.id(PIERCING_DRIVE)
			.name("Piercing Drive")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PILE_BUNKER)
			.name("Pile Bunker")
		);
		register(Move.build()
			.id(PILFER)
			.name("Pilfer")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(32))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PINE_SHOT)
			.name("Pine Shot")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(25))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PLUNGE)
			.name("Plunge")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(POISON)
			.name("Poison")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.BAD_POISON)
			))
		);
		register(Move.build()
			.id(POISON_BARBS)
			.name("Poison Barbs")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(POUNCE)
			.name("Pounce")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(POWER_DRAW)
			.name("power_draw")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(POWER_FOCUS)
			.name("Power Focus")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.rattack(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(POWER_SIPHON)
			.name("Power Siphon")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(32))
			.basePower(new Constant(20))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						// apply power siphon mechanic
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(POWER_SURGE)
			.name("power_surge")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(PRECISION_BOLT)
			.name("Precision Bolt")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PREEMPTIVE_STRIKE)
			.name("Preemptive Strike")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(3))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinch()
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PRIMAL_SLASH)
			.name("Primal Slash")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(PROVOKE)
			.name("Provoke")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(PSYCHAL_CHOMP)
			.name("Psychal Chomp")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(BITING)
		);
		register(Move.build()
			.id(PSYCHAL_SLAP)
			.name("psychal_slap")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.defendingStat(StatId.RDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(PSYCHO_BLAST)
			.name("Psycho Blast")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(PUNCH)
			.name("Punch")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(20))
			.basePower(new Constant(45))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(PYROKINESIS)
			.name("Pyrokinesis")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(38))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(QUICK_POUNCE)
			.name("Quick Pounce")
			.type(SIMPLE)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(QUICK_PUNCH)
			.name("Quick Punch")
			.type(BRAWLER)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(PUNCHING)
		);
		register(Move.build()
			.id(QUICKSAND)
			.name("Quicksand")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(RADIANT_FORECAST)
			.name("radiant_forecast")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(44))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(RADIANT_RUSH)
			.name("Radiant Rush")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(RAGING_FLAME)
			.name("Raging Flame")
			.type(FIRE)
			.category(STATUS)
			.energyCost(new Constant(35))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.BURN)
			))
		);
		register(Move.build()
			.id(RAGING_HOWL)
			.name("Raging Howl")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/3))
					))
				))
			))
			.tag(RECOIL)
			.tag(SOUND)
		);
		register(Move.build()
			.id(RAGING_TACKLE)
			.name("Raging Tackle")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/3))
					))
				))
			))
			.tag(RECOIL)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(RAINBOW_BLAST)
			.name("Rainbow Blast")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(RANCOROUS_ROLLOUT)
			.name("Rancorous Rollout")
		);
		register(Move.build()
			.id(RANT)
			.name("Rant")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(32))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(RAPID_FIRE)
			.name("rapid_fire")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(32))
			.basePower(new Constant(20))
			.condition(new AccuracyCheck(new Constant(95)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(RAZOR_SLASH)
			.name("razor_slash")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(RECHARGE)
			.name("Recharge")
		);
		register(Move.build()
			.id(RECYCLE)
			.name("Recycle")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(REFLECTION_BURST)
			.name("Reflection Burst")
			.type(METAL)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(REJUVENATE)
			.name("Rejuvenate")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(72))
		);
		register(Move.build()
			.id(REPEATING_TUNE)
			.name("Repeating Tune")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(25))
			.basePower(new Constant(25))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(SOUND)
		);
		register(Move.build()
			.id(RESONATE)
			.name("Resonate")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
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
			.tag(SOUND)
		);
		register(Move.build()
			.id(RETURN_TO_SENDER)
			.name("Return to Sender")
			.type(MIND)
			.category(STATUS)
			.priority(new Constant(-1))
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(REVIVAL)
			.name("revival")
			.type(SPIRIT)
			.category(STATUS)
			.energyCost(new Constant(72))
		);
		register(Move.build()
			.id(REVOLUTION)
			.name("revolution")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ROCK_SLIDE)
			.name("Rock Slide")
			.type(EARTH)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(ROCKET_JAB)
			.name("Rocket Jab")
		);
		register(Move.build()
			.id(ROGUE_ASSAULT)
			.name("Rogue Assault")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ROUGH_UP)
			.name("Rough Up")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new ConditionalNumber(
				Comparator.greaterThan()
					.left(new ValueConstant(Value.USER().HEIGHT()))
					.right(new ValueConstant(Value.TARGET().HEIGHT())),
				new Constant(70 * 1.25),
				new Constant(70)
			))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(RUFFLED_FEATHERS)
			.name("ruffled_feathers")
			.type(AIR)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10% of max energy
		);
		register(Move.build()
			.id(RUTHLESS_FEAST)
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
			.tag(CONTACT)
			.tag(SOUL_MOVE)
		);
		register(Move.build()
			.id(SAFEGUARD)
			.name("Safeguard")
			.type(SIMPLE)
			.category(STATUS)
			.priority(new Constant(3))
			.energyCost(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SAP_PLANT)
			.name("Sap Plant")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(30))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					// apply sap plant mechanic
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(SAVAGE_GREED)
			.name("savage_greed")
			.type(ANCIENT)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SAVAGE_HORN)
			.name("savage_horn")
			.type(BUG)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(85)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SCAPEGOAT)
			.name("Scapegoat")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(20))
		);
		register(Move.build()
			.id(SCAVENGE)
			.name("Scavenge")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(32))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SEA_SONG)
			.name("sea_song")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(PIVOT)
			.tag(SOUND)
		);
		register(Move.build()
			.id(SEARING_STEEL)
			.name("searing_steel")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(48))
			.basePower(new Constant(85))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SECOND_WIND)
			.name("second_wind")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(0))
		);
		register(Move.build()
			.id(SERENE_GIFT)
			.name("Serene Gift")
		);
		register(Move.build()
			.id(SHADOW_LURK)
			.name("shadow_lurk")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(55))
			.basePower(new Constant(100))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHADOW_PULSE)
			.name("shadow_pulse")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHADOW_SPRINT)
			.name("Shadow Sprint")
			.type(SPIRIT)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SHADOWBOX)
			.name("Shadowbox")
			.type(BRAWLER)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(SHARD_STORM)
			.name("Shard Storm")
		);
		register(Move.build()
			.id(SHARPEN)
			.name("Sharpen")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mattack(new Constant(1))
						.accuracy(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(SHATTER)
			.name("shatter")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SHELL_BASH)
			.name("shell_bash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(56))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHELL_SHRAPNEL)
			.name("shell_shrapnel")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(56))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHINE)
			.name("Shine")
			.type(LIGHT)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHREWD_BLAST)
			.name("SHrewd Blast")
			.type(ANCIENT)
			.category(RANGED)
			.energyCost(new Constant(42))
			.basePower(new Constant(70))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SHRIEK)
			.name("Shriek")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(15))
			.condition(new AccuracyCheck(new Constant(100)))
			.tag(SOUND)
		);
		register(Move.build()
			.id(SINGE)
			.name("Singe")
			.type(FIRE)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BURN)
					))
				))
			))
		);
		register(Move.build()
			.id(SINISTER_SHACKLES)
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
		);
		register(Move.build()
			.id(SKILLED_SWIPE)
			.name("Skilled Swipe")
		);
		register(Move.build()
			.id(SKULK)
			.name("Skulk")
		);
		register(Move.build()
			.id(SLAP_DOWN)
			.name("Slap Down")
			.type(DARK)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(45))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(SLASH)
			.name("Slash")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(SLEET_SHOT)
			.name("Sleet Shot")
			.type(ICE)
			.category(RANGED)
			.energyCost(new Constant(28))
			.basePower(new Constant(55))
			.condition(new AccuracyCheck(new Constant(95)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SLIME)
			.name("Slime")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(28))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(SLUMBER)
			.name("Slumber")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(50))
		);
		register(Move.build()
			.id(SNORE_SPORES)
			.name("Snore Spores")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(40))
			.condition(new AccuracyCheck(new Constant(90)))
			.tag(POWDER)
		);
		register(Move.build()
			.id(SNOT_SHOT)
			.name("Snot Shot")
		);
		register(Move.build()
			.id(SNOWDOZER)
			.name("Snowdozer")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(24))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SOFT_WATER)
			.name("Soft Water")
			.type(WATER)
			.category(STATUS)
			.energyCost(new Constant(50))
		);
		register(Move.build()
			.id(SOLAR_PUNCH)
			.name("solar_punch")
			.type(LIGHT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(100))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SOUL_DRAIN)
			.name("soul_drain")
			.type(DARK)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						new HealthDrainEffect(new Constant(1/2))
					))
				))
			))
			.tag(HEALTH_DRAIN)
		);
		register(Move.build()
			.id(SOUL_HARVEST)
			.name("soul_harvest")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SOUL_STORM)
			.name("Soul Storm")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SPARE)
			.name("Spare")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SPECTRAL_BURST)
			.name("Spectral Burst")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SPIKE_RUSH)
			.name("Spike Rush")
			.type(ICE)
			.category(MELEE)
			.priority(new Constant(1))
			.energyCost(new Constant(25))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SPIT)
			.name("Spit")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(25))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SPIT_OUT)
			.name("spit_out")
			.type(SIMPLE)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SPITE)
			.name("Spite")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(40))
			.condition(new AccuracyCheck(new Constant(90)))
		);
		register(Move.build()
			.id(SPLITTING_HEADACHE)
			.name("Splitting Headache")
			.type(MIND)
			.category(RANGED)
			.priority(new Constant(1))
			.energyCost(new Constant(40))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SPRAY)
			.name("Spray")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(SQUARE_ONE)
			.name("Square One")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(30))
		);
		register(Move.build()
			.id(SQUAWK)
			.name("Squawk")
			.type(AIR)
			.category(STATUS)
			.energyCost(new Constant(10))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				ApplyStatChanges.userChangingTarget()
					.mdefense(new Constant(-1))	
			))
		);
		register(Move.build()
			.id(STALK)
			.name("Stalk")
		);
		register(Move.build()
			.id(STAMPEDE)
			.name("Stampede")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(STARE)
			.name("Stare")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(10))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(STARTLE)
			.name("Startle")
			.type(SPIRIT)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.3))
					))
				))
			))
		);
		register(Move.build()
			.id(STATIC_SHOCK)
			.name("Static Shock")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(20))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(STATIC_SPORES)
			.name("Static Spores")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
			))
			.tag(POWDER)
		);
		register(Move.build()
			.id(STEALTH_ASSAULT)
			.name("Stealth Assault")
		);
		register(Move.build()
			.id(STEEL_CRUSHER)
			.name("Steel Crusher")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(90)))
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
			.tag(CONTACT)
		);
		register(Move.build()
			.id(STEEL_GRIT)
			.name("Steel Grit")
		);
		register(Move.build()
			.id(STEEL_TRAP)
			.name("steel_trap")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(STING)
			.name("Sting")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(10))
			.basePower(new Constant(15))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(STORM_SUMMON)
			.name("storm_summon")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(46))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.5, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(STRATAGEM)
			.name("Stratagem")
			.type(MIND)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.attackingStat(StatId.RDEFENSE)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(STRATEGIC_SURGE)
			.name("Strategic Surge")
		);
		register(Move.build()
			.id(STRETCH)
			.name("Stretch")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.speed(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(STRIKE)
			.name("Strike")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(15))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(STUN)
			.name("Stun")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(35))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
			))
		);
		register(Move.build()
			.id(STUPEFY)
			.name("Stupefy")
			.type(SPIRIT)
			.category(RANGED)
			.energyCost(new Constant(25))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.3, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(SUBMERGE)
			.name("submerge")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(30))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SUMMON)
			.name("Summon")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(25))
		);
		register(Move.build()
			.id(SWAT)
			.name("Swat")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(32))
			.basePower(new Constant(45))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(SWIFTEN)
			.name("swiften")
			.type(LIGHT)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(SWIPE)
			.name("Swipe")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(22))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(TAIL_SLAP)
			.name("Tail Slap")
			.type(SIMPLE)
			.category(MELEE)
			.energyCost(new Constant(10))
			.basePower(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(TAKE_ROOT)
			.name("Take Root")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(TALENT_TRADE)
			.name("Talent Trade")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(40))
		);
		register(Move.build()
			.id(TAMP)
			.name("Tamp")
			.type(EARTH)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.mdefense(new Constant(1))
						.rdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(TASE)
			.name("Tase")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(20))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(1, StatusId.PARALYSIS)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(TEAMWORK)
			.name("Teamwork")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(20))
		);
		register(Move.build()
			.id(TEAR_DOWN)
			.name("Tear Down")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(35))
		);
		register(Move.build()
			.id(TEASE)
			.name("Tease")
			.type(DARK)
			.category(STATUS)
			.energyCost(new Constant(20))
			.condition(new AccuracyCheck(new Constant(100)))
		);
		register(Move.build()
			.id(TELEKINETIC_SWEEP)
			.name("telekinetic_sweep")
			.type(MIND)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.2))
					))
				))
			))
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(TEMPEST)
			.name("tempest")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(40))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(THISTLE_SLASH)
			.name("Thistle Slash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(36))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(THORN_SLAM)
			.name("thorn_slam")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(THUNDER_BLAST)
			.name("Thunder Blast")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(THUNDER_CHOMP)
			.name("Thunder Chomp")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS),
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(THUNDER_CLAP)
			.name("Thunder Clap")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.3))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(THUNDER_CRASH)
			.name("Thunder Crash")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/3))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(THUNDER_SLAM)
			.name("Thunder Slam")
			.type(ELECTRIC)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(65))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(THUNDERSTORM)
			.name("ThunderStorm")
			.type(ELECTRIC)
			.category(STATUS)
			.energyCost(new Constant(25))
			.onHit(new MoveEffect(
				SetWeather.userSetting(
					WeatherId.THUNDERSTORM,
					new Constant(5)
				)
			))
		);
		register(Move.build()
			.id(THUNDERSTRIKE)
			.name("Thunderstrike")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.PARALYSIS)
					))
				))
			))
		);
		register(Move.build()
			.id(TIMBER_SWEEP)
			.name("Timber Sweep")
		);
		register(Move.build()
			.id(TIMBER_THRASH)
			.name("Timber Thrash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						RecoilEffect.onUser(new Constant(1/4))
					))
				))
			))
			.tag(CONTACT)
			.tag(RECOIL)
		);
		register(Move.build()
			.id(TONE_BARRIER)
			.name("Tone Barrier")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(Schematics.tenPercentOfUserMaxEnergy()) // 10% max energy
			.priority(new Constant(4))
			.tag(SOUND)
		);
		register(Move.build()
			.id(TORRENTIAL_SLASH)
			.name("torrential_slash")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.flinchChance(new Constant(0.2))
					))
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(TOXIBLAST)
			.name("toxiblast")
			.type(TOXIC)
			.category(RANGED)
			.energyCost(new Constant(36))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.2, StatusId.POISON)
					))
				))
			))
		);
		register(Move.build()
			.id(TOXIC_SPORES)
			.name("Toxic Spores")
			.type(TOXIC)
			.category(STATUS)
			.energyCost(new Constant(25))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				Schematics.applyStatusToTarget(1, StatusId.POISON)
			))
			.tag(POWDER)
		);
		register(Move.build()
			.id(TOXIC_TAIL)
			.name("Toxic Tail")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(30))
			.basePower(new Constant(50))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(TRIBUTE)
			.name("Tribute")
			.type(SIMPLE)
			.category(STATUS)
			.energyCost(new Constant(50))
		);
		register(Move.build()
			.id(TRICK_SHOT)
			.name("trick_shot")
			.type(SPIRIT)
			.category(RANGED)
			.priority(new Constant(1))
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(TRICKY_TACTICS)
			.name("Tricky Tactics")
			.type(DARK)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(95))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(TRIP_ROOT)
			.name("trip_root")
			.type(PLANT)
			.category(RANGED)
			.energyCost(new Constant(30))
			.basePower(new Constant(0)) // TODO
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(TSUNAMI)
			.name("Tsunami")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(TUNE_UP)
			.name("Tune Up")
			.type(METAL)
			.category(STATUS)
			.energyCost(new Constant(15))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					ApplyStatChanges.userChangingUser()
						.rdefense(new Constant(1))
				))
			))
		);
		register(Move.build()
			.id(TWIRLY_WHIRL)
			.name("twirly_whirl")
			.type(BUG)
			.category(RANGED)
			.energyCost(new Constant(40))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
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
		);
		register(Move.build()
			.id(TYPHOON)
			.name("Typhoon")
			.type(AIR)
			.category(RANGED)
			.energyCost(new Constant(60))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(UNDERMINE)
			.name("Undermine")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(VENOM_CHOMP)
			.name("Venom Chomp")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(35))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.BAD_POISON),
						Effects.flinchChance(new Constant(0.1))
					))
				))
			))
			.tag(BITING)
			.tag(CONTACT)
		);
		register(Move.build()
			.id(VENOM_SLASH)
			.name("Venom Slash")
			.type(TOXIC)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.critChance(1)
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Schematics.applyStatusToTarget(0.1, StatusId.POISON)
					))
				))
			))
			.tag(CONTACT)
			.tag(SLASHING)
		);
		register(Move.build()
			.id(VICE_JAWS)
			.name("Vice Jaws")
			.type(METAL)
			.category(MELEE)
			.energyCost(new Constant(40))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(VICIOUS_SNAP)
			.name("vicious_snap")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(45))
			.basePower(new Constant(75))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(VINE_LASH)
			.name("Vine Lash")
			.type(PLANT)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(VITAL_JAB)
			.name("Vital Slash")
			.type(BRAWLER)
			.category(MELEE)
			.energyCost(new Constant(50))
			.basePower(new Constant(80))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(VITAL_SURGE)
			.name("vital_surge")
			.type(BRAWLER)
			.category(RANGED)
			.energyCost(new Constant(55))
			.basePower(new Constant(110))
			.condition(new AccuracyCheck(new Constant(80)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(WATER_BOMB)
			.name("Water Bomb")
			.type(WATER)
			.category(RANGED)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.2))
					))
				))
			))
		);
		register(Move.build()
			.id(WAVE_WRECKER)
			.name("Wave Wrecker")
			.type(WATER)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(90))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage(),
					Schematics.secondaryEffects(List.of(
						Effects.dazeChance(new Constant(0.1))
					))
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(WEB_SHOT)
			.name("Web Shot")
			.type(BUG)
			.category(STATUS)
			.energyCost(new Constant(10))
			.condition(new AccuracyCheck(new Constant(90)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(WING_SLAP)
			.name("Wing Slap")
			.type(AIR)
			.category(MELEE)
			.energyCost(new Constant(26))
			.basePower(new Constant(60))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
			.tag(SLAPPING)
		);
		register(Move.build()
			.id(WINTERS_FURY)
			.name("winters_fury")
			.type(ICE)
			.category(MELEE)
			.energyCost(new Constant(55))
			.basePower(new Constant(30))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(WONDER_HERB)
			.name("Wonder Herb")
			.type(PLANT)
			.category(STATUS)
			.energyCost(new Constant(35))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
		);
		register(Move.build()
			.id(YETIS_WRATH)
			.name("yetis_wrath")
			.type(EARTH)
			.category(MELEE)
			.energyCost(new Constant(60))
			.basePower(new Constant(120))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(CONTACT)
		);
		register(Move.build()
			.id(ZIP_ZAP)
			.name("Zip Zap")
			.type(ELECTRIC)
			.category(RANGED)
			.energyCost(new Constant(50))
			.basePower(new Constant(70))
			.condition(new AccuracyCheck(new Constant(100)))
			.onHit(new MoveEffect(
				new EffectSequence(List.of(
					new DealFormulaDamage()
				))
			))
			.tag(PIVOT)
		);
    }
}
