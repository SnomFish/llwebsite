package github.snomfish.functionality;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.domain.ActiveLoomian;
import github.snomfish.domain.Loomian;
import github.snomfish.domain.Side;
import github.snomfish.domain.SideId;
import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.item.ItemId;
import github.snomfish.domain.mechanics.MechanicInstance;
import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.mechanics.ids.FieldMechanicId;
import github.snomfish.domain.mechanics.ids.LoomianMechanicId;
import github.snomfish.domain.mechanics.ids.SideMechanicId;
import github.snomfish.domain.modifiers.ids.LoomModifierId;
import github.snomfish.domain.modifiers.ids.MoveModifierId;
import github.snomfish.domain.modifiers.ids.SideModifierId;
import github.snomfish.domain.move.CategoryId;
import github.snomfish.domain.move.Move;
import github.snomfish.domain.species.Species;
import github.snomfish.domain.species.SpeciesId;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.domain.status.StatusId;
import github.snomfish.domain.tags.AbilityTagId;
import github.snomfish.domain.tags.ItemTagId;
import github.snomfish.domain.tags.MoveTagId;
import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeId;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherInstance;
import github.snomfish.functionality.action.Action;
import github.snomfish.functionality.action.IAction;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.context.ContextSideId;
import github.snomfish.functionality.number.INumber;

public class Value<T> {


	private final Class<T> type;
    private final Function<BattleContext, T> getter;
	private final BiConsumer<BattleContext, T> setter;


    public Value(
		Class<T> type,
		Function<BattleContext, T> getter,
		BiConsumer<BattleContext, T> setter
	) {
		this.type = type;
        this.getter = getter;
		this.setter = setter;
    }


	private <R> Value<R> update(
		Class<R> type,
		Function<T, R> newGetter,
		BiConsumer<T, R> newSetter
	) {
		return new Value<>(
			type,
			getter.andThen(newGetter),
			newSetter == null ? null : (c, v) -> newSetter.accept(getter.apply(c), v)
		);
	}


	public Class<T> type() {return type;}
	public T get(BattleContext context) {
		return (T) getter.apply(context);
	}
	public void set(BattleContext context, T value) {
		setter.accept(context, value);
	}


	@SuppressWarnings("unchecked")
	public static <R> R resolve(Object object, BattleContext context) {
		if (object instanceof Value<?> value) {
			return (R) value.get(context);
		}

		return (R) object;
	}
    

    public static Value<Side> USER() {return new Value<>(
		Side.class,
		c -> c.user(),
		null
	);}
	public static Value<Side> SELF() {return new Value<>( // same as USER()
		Side.class,
		c -> c.user(),
		null
	);}
    public static Value<Side> TARGET() {return new Value<>(
		Side.class,
		c -> c.target(),
		null
	);}
	public static Value<Side> OPP() {return new Value<>( // same as OPP()
		Side.class,
		c -> c.target(),
		null
	);}
	public static Value<Side> SIDE(ContextSideId sideId) {
		return (sideId == ContextSideId.USER) ? USER() : TARGET();
	}
    public static Value<WeatherInstance> WEATHER() {return new Value<>(
		WeatherInstance.class,
		c -> c.gamestate().weatherInstance(),
		null
	);}


	public static Value<List<SideId>> SPEED_ORDER() {return new Value<>(
		null,
		c -> c.gamestate().speedOrder(),
		(c, v) -> c.gamestate().setSpeedOrder(v)
	);}
	public static Value<SideId> SPEED_ORDER(int i) {return new Value<>(
		SideId.class,
		c -> c.gamestate().speedOrder().get(i),
		(c, v) -> c.gamestate().speedOrder().set(i, v)
	);}

	
	// WEATHER
	public static Value<WeatherId> WEATHER_ID() {return new Value<>(
		WeatherId.class,
		c -> c.gamestate().weatherInstance().weatherId(),
		(c, v) -> c.gamestate().weatherInstance().setWeatherId(v) 
	);}
	public static Value<Integer> WEATHER_LIFESPAN() {return new Value<>(
		Integer.class,
		c -> c.gamestate().weatherInstance().lifespan(),
		(c, v) -> c.gamestate().weatherInstance().setLifespan(v)
	);}


	// FIELD MECHANICS
	public static Value<Mechanics> FIELD_MECHANICS() {return new Value<>(
		Mechanics.class,
		c -> c.gamestate().mechanics(),
		(c, v) -> c.gamestate().setMechanics(v)
	);}
	public static Value<MechanicInstance> FIELD_MECHANIC(FieldMechanicId id) {return new Value<>(
		MechanicInstance.class,
		c -> 		c.gamestate().mechanics().get(id),
		(c, v) -> 	c.gamestate().mechanics().set(v)
	);}


	public static Value<Integer> TURN_NUMBER() {return new Value<>(
		Integer.class,
		c -> 		c.gamestate().turnNumber(),
		(c, v) -> 	c.gamestate().setTurnNumber(v)
	);}


	public Value<List<Loomian>> PARTY() {return update(
		null,
		c -> 		((Side) c).party(),
		null
	);}
    public Value<ActiveLoomian> LOOMIAN() {return update(
		ActiveLoomian.class,
		c -> 		((Side) c).activeLoomian(),
		null
	);}
	// SPECIES
	public Value<Species> SPECIES() {return update(
		Species.class,
		c -> 		((Side) c).activeLoomian().species(),
		null
	);}
    public Value<SpeciesId> SPECIES_ID() {return update(
		SpeciesId.class,
		c -> 		((Side) c).activeLoomian().speciesId(),
		(c, v) -> 	((Side) c).activeLoomian().setSpeciesId((SpeciesId) v)
	);}
	public Value<String> SPECIES_NAME() {return update(
		String.class,
		c -> 		((Side) c).activeLoomian().species().name(),
		null
	);}
	public Value<Double> WEIGHT() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().weight(),
		null
	);}
	public Value<Double> HEIGHT() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().height(),
		null
	);}
	public Value<Boolean> CAN_SPECIES_EVOLVE() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().species().canEvolve(),
		null
	);}	
	public Value<Boolean> CAN_SPECIES_SOUL_BURST() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().species().canSoulBurst(),
		null
	);}	
	// TYPES
    public Value<List<TypeId>> TYPES() {return update(
		null,
		c -> 		((Side) c).activeLoomian().types(),
		(c, v) -> 	((Side) c).activeLoomian().setTypes(v)
	);}
	public Value<TypeId> PRIMARY_TYPE() {return update(
		TypeId.class,
		c -> 		((Side) c).activeLoomian().primaryType(),
		(c, v) -> 	((Side) c).activeLoomian().setPrimaryType(v)
	);}
	public Value<TypeId> SECONDARY_TYPE() {return update(
		TypeId.class,
		c -> 		((Side) c).activeLoomian().secondaryType(),
		(c, v) -> 	((Side) c).activeLoomian().setSecondaryType(v)
	);}
	// ABILITIES
	public Value<AbilityId> ABILITY_ID() {return update(
		AbilityId.class,
		c -> 		((Side) c).activeLoomian().abilityInstance().abilityId(),
		(c, v) -> 	((Side) c).activeLoomian().abilityInstance().setAbilityId(v)
	);}
	public Value<Boolean> ABILITY_HAS_ACTIVATED_THIS_BATTLE() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().abilityInstance().hasTriggeredThisBattle(),
		(c, v) -> 	((Side) c).activeLoomian().abilityInstance().setTriggeredThisBattle(v)
	);}
	public Value<Boolean> ABILITY_HAS_ACTIVATED_THIS_SWAP() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().abilityInstance().hasTriggeredThisSwapIn(),
		(c, v) -> 	((Side) c).activeLoomian().abilityInstance().setTriggeredThisSwapIn(v)
	);}
	public Value<Boolean> ABILITY_TAG(AbilityTagId id) {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().abilityInstance().ability().tags().get(id),
		(c, v) -> 	((Side) c).activeLoomian().abilityInstance().ability().tags().set(id, v)
	);}
	public Value<AbilityId> ACTUAL_ABILITY_ID() {return update(
		AbilityId.class,
		c -> 		((Side) c).activeLoomian().actualAbilityInstance().abilityId(),
		(c, v) -> 	((Side) c).activeLoomian().actualAbilityInstance().setAbilityId(v)
	);}
	public Value<Boolean> ACTUAL_ABILITY_HAS_ACTIVATED_THIS_BATTLE() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().actualAbilityInstance().hasTriggeredThisBattle(),
		(c, v) -> 	((Side) c).activeLoomian().actualAbilityInstance().setTriggeredThisBattle(v)
	);}
	public Value<Boolean> ACTUAL_ABILITY_HAS_ACTIVATED_THIS_SWAP() {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().actualAbilityInstance().hasTriggeredThisSwapIn(),
		(c, v) -> 	((Side) c).activeLoomian().actualAbilityInstance().setTriggeredThisSwapIn(v)
	);}
	// ITEM
	public Value<ItemId> ITEM_ID() {return update(
		ItemId.class,
		c -> 		((Side) c).activeLoomian().itemId(),
		(c, v) -> 	((Side) c).activeLoomian().setItemId(v)
	);}
	public Value<Boolean> ITEM_TAG(ItemTagId id) {return update(
		Boolean.class,
		c -> 		((Side) c).activeLoomian().item().tags().get(id),
		(c, v) -> 	((Side) c).activeLoomian().item().tags().set(id, v)
	);}
	// STATUS
	public Value<StatusId> STATUS_ID() {return update(
		StatusId.class,
		c -> 		((Side) c).activeLoomian().statusId(),
		(c, v) -> 	((Side) c).activeLoomian().setStatusId(v)
	);}
	public Value<Integer> STATUS_TURNS() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statusTurns(),
		(c, v) -> 	((Side) c).activeLoomian().setStatusTurns(v)
	);}
	// BOOLEANS
	public Value<Boolean> HAS_ACTED_THIS_TURN() {return update(
		Boolean.class,
		c ->		((Side) c).activeLoomian().hasActedThisTurn(),
		(c, v) ->	((Side) c).activeLoomian().setActedThisTurn(v)
	);}
	public Value<Boolean> HAS_SWAPPED_IN_THIS_TURN() {return update(
		Boolean.class,
		c ->		((Side) c).activeLoomian().hasSwappedInThisTurn(),
		(c, v) ->	((Side) c).activeLoomian().setSwappedInThisTurn(v)
	);}
	// ACTION
	public Value<Action> ACTION() {return update(
		Action.class,
		c ->		((Side) c).activeLoomian().action(),
		(c, v) ->	((Side) c).activeLoomian().setAction(v)
	);}
	public Value<IAction> MAIN_ACTION() {return update(
		IAction.class,
		c ->		((Side) c).activeLoomian().action().mainAction(),
		(c, v) ->	((Side) c).activeLoomian().action().setMainAction(v)
	);}
	// PRIORITY
	public Value<Integer> PRIORITY() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().action().mainAction().priority(),
		null
	);}
	// MECHANICS
	public Value<Mechanics> SIDE_MECHANICS() {return update(
		Mechanics.class,
		c ->		((Side) c).mechanics(),
		(c, v) -> 	((Side) c).setMechanics(v)
	);}
	public Value<MechanicInstance> SIDE_MECHANIC(SideMechanicId id) {return update(
		MechanicInstance.class,
		c -> 		((Side) c).mechanics().get(id),
		(c, v) ->	((Side) c).mechanics().set(v)
	);}
	public Value<Double> SIDE_MODIFIER(SideModifierId id) {return update(
		Double.class,
		c -> 		((Side) c).modifiers().get(id),
		(c, v) ->	((Side) c).modifiers().set(id, v)
	);}
	public Value<Integer> MAX_AGE() {return update(
		Integer.class,
		c ->		((MechanicInstance) c).maxAge(),
		(c, v) ->	((MechanicInstance) c).maxAge(v)
	);}
	public Value<Mechanics> LOOMIAN_MECHANICS() {return update(
		Mechanics.class,
		c ->		((Side) c).activeLoomian().mechanics(),
		(c, v) -> 	((Side) c).activeLoomian().setMechanics(v)
	);}
	public Value<MechanicInstance> LOOMIAN_MECHANIC(LoomianMechanicId id) {return update(
		MechanicInstance.class,
		c -> 		((Side) c).activeLoomian().mechanics().get(id),
		(c, v) ->	((Side) c).activeLoomian().mechanics().set(v)
	);}
	public Value<Boolean> HAS_MECHANIC(LoomianMechanicId id) {return update(
		Boolean.class,
		c ->		((Side) c).activeLoomian().mechanics().has(id),
		null
	);}
	public Value<Double> LOOMIAN_MODIFIER(LoomModifierId id) {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().modifiers().get(id),
		(c, v) -> 	((Side) c).activeLoomian().modifiers().set(id, v)
	);}
	// MOVES
	public Value<Integer> NUM_OF_MOVES() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().moves().size(),
		null
	);}
	// STATS
	public Value<Integer> CURRENT_HEALTH() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().currentHealth(),
		(c, v) -> 	((Side) c).activeLoomian().setCurrentHealth(v)
	);}
	public Value<Integer> CURRENT_ENERGY() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().currentEnergy(),
		(c, v) -> 	((Side) c).activeLoomian().setCurrentEnergy(v)
	);}


	public Value<Double> SPECIES_BASE_HEALTH() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().health(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setHealth(v)
	);}
	public Value<Double> SPECIES_BASE_ENERGY() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().energy(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setEnergy(v)
	);}
	public Value<Double> SPECIES_BASE_MATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().mattack(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setMattack(v)
	);}
	public Value<Double> SPECIES_BASE_MDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().mdefense(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setMdefense(v)
	);}
	public Value<Double> SPECIES_BASE_RATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().rattack(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setRattack(v)
	);}
	public Value<Double> SPECIES_BASE_RDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().rdefense(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setRdefense(v)
	);}
	public Value<Double> SPECIES_BASE_SPEED() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().species().baseStats().speed(),
		(c, v) -> 	((Side) c).activeLoomian().species().baseStats().setSpeed(v)
	);}


	public Value<Double> BASE_HEALTH() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().health(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setHealth(v)
	);}
	public Value<Double> BASE_ENERGY() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().energy(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setEnergy(v)
	);}
	public Value<Double> BASE_MATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().mattack(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setMattack(v)
	);}
	public Value<Double> BASE_MDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().mdefense(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setMdefense(v)
	);}
	public Value<Double> BASE_RATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().rattack(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setRattack(v)
	);}
	public Value<Double> BASE_RDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().rdefense(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setRdefense(v)
	);}
	public Value<Double> BASE_SPEED() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().baseStats().speed(),
		(c, v) -> 	((Side) c).activeLoomian().baseStats().setSpeed(v)
	);}


	public Value<Double> HEALTH() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().health(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setHealth(v)
	);}
	public Value<Double> ENERGY() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().energy(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setEnergy(v)
	);}
	public Value<Double> MATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().mattack(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setMattack(v)
	);}
	public Value<Double> MDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().mdefense(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setMdefense(v)
	);}
	public Value<Double> RATTACK() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().rattack(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setRattack(v)
	);}
	public Value<Double> RDEFENSE() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().rdefense(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setRdefense(v)
	);}
	public Value<Double> SPEED() {return update(
		Double.class,
		c -> 		((Side) c).activeLoomian().stats().speed(),
		(c, v) -> 	((Side) c).activeLoomian().stats().setSpeed(v)
	);}
	public Value<Integer> MATTACK_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.MATTACK),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.MATTACK, v)
	);}
	public Value<Integer> MDEFENSE_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.MDEFENSE),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.MDEFENSE, v)
	);}
	public Value<Integer> RATTACK_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.RATTACK),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.RATTACK, v)
	);}
	public Value<Integer> RDEFENSE_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.RDEFENSE),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.RDEFENSE, v)
	);}
	public Value<Integer> SPEED_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.SPEED),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.SPEED, v)
	);}
	public Value<Integer> ACCURACY_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.ACCURACY),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.ACCURACY, v)
	);}
	public Value<Integer> EVASION_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.EVASION),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.EVASION, v)
	);}
	public Value<Integer> CRIT_CHANCE_STAGE_MODIFIER() {return update(
		Integer.class,
		c -> 		((Side) c).activeLoomian().statChanges().stageModifier(StatChangesId.CRIT_CHANCE),
		(c, v) -> 	((Side) c).activeLoomian(). statChanges().set(StatChangesId.CRIT_CHANCE, v)
	);}

    public Value<Move> MOVE() {return update(
		Move.class,
		c -> 		((Side) c).activeLoomian().action().mainAction().move(),
		(c, v) -> 	((Side) c).activeLoomian().action().mainAction().setMove((Move) v)
	);}

	public Value<TypeId> TYPE() {return update(
		TypeId.class,
        c -> 		((Move) c).type(),
        (c, v) -> 	((Move) c).type(v)
    );}
    public Value<CategoryId> CATEGORY() {return update(
		CategoryId.class,
        c -> 		((Move) c).category(),
        (c, v) -> 	((Move) c).category(v)
    );}
    public Value<TypeChart> TYPECHART() {return update(
		TypeChart.class,
        c -> 		((Move) c).typeChart(),
        (c, v) -> 	((Move) c).typeChart(v)
    );}
    public Value<Integer> CRIT_CHANCE() {return update(
		Integer.class,
        c -> 		((Move) c).damage(),
        (c, v) -> 	((Move) c).critChance(v)
    );}
    public Value<INumber> ENERGY_COST() {return update(
		INumber.class,
        c -> 		((Move) c).energyCost(),
        (c, v) -> 	((Move) c).energyCost(v)
    );}
    public Value<INumber> BASE_POWER() {return update(
		INumber.class,
        c -> 		((Move) c).basePower(),
        (c, v) -> 	((Move) c).basePower(v)
    );}
    public Value<Integer> CALCED_ENERGY_COST() {return update(
		Integer.class,
        c -> 		((Move) c).calcedEnergyCost(),
        (c, v) -> 	((Move) c).setCalcedEnergyCost(v)
    );}
    public Value<Integer> DAMAGE() {return update(
		Integer.class,
        c -> 		((Move) c).damage(),
        (c, v) -> 	((Move) c).setDamage(v)
    );}
    public Value<Integer> HEALTH_DRAIN() {return update(
		Integer.class,
        c -> 		((Move) c).healthDrained(),
        (c, v) -> 	((Move) c).setHealthDrained(v)
    );}
    public Value<Integer> ENERGY_DRAIN() {return update(
		Integer.class,
        c -> 		((Move) c).energyDrained(),
        (c, v) -> 	((Move) c).setHealthDrained(v)
    );}
	public Value<Double> MOVE_MODIFIER(MoveModifierId id) {return update(
		Double.class,
		c -> 		((Move) c).modifiers().get(id),
		(c, v) -> 	((Move) c).modifiers().set(id, v)
	);}
	public Value<Boolean> MOVE_TAG(MoveTagId id) {return update(
		Boolean.class,
		c -> 		((Move) c).tags().get(id),
		(c, v) -> 	((Move) c).tags().set(id, v)
	);}
	public Value<Boolean> CAN_MOVE_FAIL() {return update(
		Boolean.class,
		c -> 		((Move) c).canMoveFail(),
		(c, v) ->	((Move) c).setCanMoveFail(v)
	);}
	public Value<Boolean> HAS_MOVE_FAILED() {return update(
		Boolean.class,
		c -> 		((Move) c).hasMoveFailed(),
		(c, v) ->	((Move) c).setHasMoveFailed(v)
	);}
	public Value<Boolean> HAS_CRIT() {return update(
		Boolean.class,
		c -> 		((Move) c).hasCrit(),
		(c, v) ->	((Move) c).setHasCrit(v)
	);}
    
}