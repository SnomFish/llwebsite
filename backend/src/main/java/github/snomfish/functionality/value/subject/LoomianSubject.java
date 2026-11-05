package github.snomfish.functionality.value.subject;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.domain.Side;
import github.snomfish.domain.stats.StatChangesId;

public enum LoomianSubject implements ISubject<Side> {
    LOOMIAN(
        c -> c.activeLoomian(),null
    ),

    // SPECIES
    SPECIES(
        c -> c.activeLoomian().species(),null
    ),
    SPECIES_ID(
        c -> c.activeLoomian().speciesId(),
        (c, v) -> c.activeLoomian().setSpeciesId(v)
    ),
    SPECIES_NAME(
        c -> c.activeLoomian().species().name(), null
    ),
    SPECIES_WEIGHT(
        c -> c.activeLoomian().species().weight(), null
    ),
    SPECIES_HEIGHT(
        c -> c.activeLoomian().species().height(), null
    ),
    SPECIES_CAN_EVOLVE(
        c -> c.activeLoomian().species().canEvolve(), null
    ),

    // TYPES
    TYPES(
        c -> c.activeLoomian().types(),
        (c, v) -> c.activeLoomian().setTypes(v)
    ),
    PRIMARY_TYPE(
        c -> c.activeLoomian().primaryType(),
        (c, v) -> c.activeLoomian().setPrimaryType(v)
    ),
    SECONDARY_TYPE(
        c -> c.activeLoomian().secondaryType(),
        (c, v) -> c.activeLoomian().setSecondaryType(v)
    ),

    // ABILITY
    ABILITY_ID(
        c -> c.activeLoomian().abilityInstance().abilityId(),
        (c, v) -> c.activeLoomian().abilityInstance().setAbilityId(v)
    ),
    ABILITY_HAS_ACTIVATED_THIS_BATTLE(
        c -> c.activeLoomian().abilityInstance().hasTriggeredThisBattle(),
        (c, v) -> c.activeLoomian().abilityInstance().setTriggeredThisBattle(v)
    ),    
    ABILITY_HAS_ACTIVATED_THIS_SWAP(
        c -> c.activeLoomian().abilityInstance().hasTriggeredThisSwapIn(),
        (c, v) -> c.activeLoomian().abilityInstance().setTriggeredThisSwapIn(v)
    ), 
    ACTUAL_ABILITY_ID(
        c -> c.activeLoomian().actualAbilityInstance().abilityId(),
        (c, v) -> c.activeLoomian().actualAbilityInstance().setAbilityId(v)
    ),
    ACTUAL_ABILITY_HAS_ACTIVATED_THIS_BATTLE(
        c -> c.activeLoomian().actualAbilityInstance().hasTriggeredThisBattle(),
        (c, v) -> c.activeLoomian().actualAbilityInstance().setTriggeredThisBattle(v)
    ),    
    ACTUAL_ABILITY_HAS_ACTIVATED_THIS_SWAP(
        c -> c.activeLoomian().actualAbilityInstance().hasTriggeredThisSwapIn(),
        (c, v) -> c.activeLoomian().actualAbilityInstance().setTriggeredThisSwapIn(v)
    ), 

    // ITEM
    ITEM_ID(
        c -> c.activeLoomian().itemId(),
        (c, v) -> c.activeLoomian().setItemId(v)
    ),

    // STATUS
    STATUS_ID(
        c -> c.activeLoomian().statusId(),
        (c, v) -> c.activeLoomian().setStatusId(v)
    ),
    STATUS_TURNS(
        c -> c.activeLoomian().statusTurns(),
        (c, v) -> c.activeLoomian().setStatusTurns(v)
    ),

    // ACTION
    ACTION(
        c -> c.activeLoomian().action(),
        (c, v) -> c.activeLoomian().setAction(v)
    ),

    // PRIORITY
    PRIORITY(
        c -> c.activeLoomian().action().priority(),
        (c, v) -> c.activeLoomian().action().setPriority(v)
    ),

    // STATS
    CURRENT_HEALTH(
        c -> c.activeLoomian().currentHealth(),
        (c, v) -> c.activeLoomian().setCurrentHealth(v)
    ),
    CURRENT_ENERGY(
        c -> c.activeLoomian().currentEnergy(),
        (c, v) -> c.activeLoomian().setCurrentEnergy(v)
    ),

    BASE_HEALTH(
        c -> c.activeLoomian().species().baseStats().health(),
        (c, v) -> c.activeLoomian().species().baseStats().setHealth(v)
    ),
    BASE_ENERGY(
        c -> c.activeLoomian().species().baseStats().energy(),
        (c, v) -> c.activeLoomian().species().baseStats().setEnergy(v)
    ),
    BASE_MATTACK(
        c -> c.activeLoomian().species().baseStats().mattack(),
        (c, v) -> c.activeLoomian().species().baseStats().setMattack(v)
    ),
    BASE_MDEFENSE(
        c -> c.activeLoomian().species().baseStats().mdefense(),
        (c, v) -> c.activeLoomian().species().baseStats().setMdefense(v)
    ),
    BASE_RATTACK(
        c -> c.activeLoomian().species().baseStats().rattack(),
        (c, v) -> c.activeLoomian().species().baseStats().setRattack(v)
    ),
    BASE_RDEFENSE(
        c -> c.activeLoomian().species().baseStats().rdefense(),
        (c, v) -> c.activeLoomian().species().baseStats().setRdefense(v)
    ),
    BASE_SPEED(
        c -> c.activeLoomian().species().baseStats().speed(),
        (c, v) -> c.activeLoomian().species().baseStats().setSpeed(v)
    ),

    ACTUAL_HEALTH(
        c -> c.activeLoomian().actualStats().health(),
        (c, v) -> c.activeLoomian().actualStats().setHealth(v)
    ),
    ACTUAL_ENERGY(
        c -> c.activeLoomian().actualStats().energy(),
        (c, v) -> c.activeLoomian().actualStats().setEnergy(v)
    ),
    ACTUAL_MATTACK(
        c -> c.activeLoomian().actualStats().mattack(),
        (c, v) -> c.activeLoomian().actualStats().setMattack(v)
    ),
    ACTUAL_MDEFENSE(
        c -> c.activeLoomian().actualStats().mdefense(),
        (c, v) -> c.activeLoomian().actualStats().setMdefense(v)
    ),
    ACTUAL_RATTACK(
        c -> c.activeLoomian().actualStats().rattack(),
        (c, v) -> c.activeLoomian().actualStats().setRattack(v)
    ),
    ACTUAL_RDEFENSE(
        c -> c.activeLoomian().actualStats().rdefense(),
        (c, v) -> c.activeLoomian().actualStats().setRdefense(v)
    ),
    ACTUAL_SPEED(
        c -> c.activeLoomian().actualStats().speed(),
        (c, v) -> c.activeLoomian().actualStats().setSpeed(v)
    ),

    ENERGY(
        c -> c.activeLoomian().stats().energy(),
        (c, v) -> c.activeLoomian().stats().setEnergy(v)
    ),
    MATTACK(
        c -> c.activeLoomian().stats().mattack(),
        (c, v) -> c.activeLoomian().stats().setMattack(v)
    ),
    MDEFENSE(
        c -> c.activeLoomian().stats().mdefense(),
        (c, v) -> c.activeLoomian().stats().setMdefense(v)
    ),  
    RATTACK(
        c -> c.activeLoomian().stats().rattack(),
        (c, v) -> c.activeLoomian().stats().setRattack(v)
    ),
    RDEFENSE(
        c -> c.activeLoomian().stats().rdefense(),
        (c, v) -> c.activeLoomian().stats().setRdefense(v)
    ),
    SPEED(
        c -> c.activeLoomian().stats().speed(),
        (c, v) -> c.activeLoomian().stats().setSpeed(v)
    ),

    MATTACK_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.MATTACK),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.MATTACK, v)
    ),
    MDEFENSE_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.MDEFENSE),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.MDEFENSE, v)
    ),
    RATTACK_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.RATTACK),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.RATTACK, v)
    ),
    RDEFENSE_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.RDEFENSE),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.RDEFENSE, v)
    ),
    SPEED_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.SPEED),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.SPEED, v)
    ),
    ACCURACY_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.ACCURACY),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.ACCURACY, v)
    ),
    EVASION_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.EVASION),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.EVASION, v)
    ),
    CRIT_CHANCE_STAGE_MODIFIER(
        c -> c.activeLoomian().statChanges().stageModifier(StatChangesId.CRIT_CHANCE),
        (c, v) -> c.activeLoomian(). statChanges().set(StatChangesId.CRIT_CHANCE, v)
    )
    ;


    private final Function<Side, ?> getter;
    private final BiConsumer<Side, ?> setter;


    private <T> LoomianSubject(
        Function<Side, T> getter,
        BiConsumer<Side, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(Side side) {
        return (T) getter.apply(side);
    } 

    @SuppressWarnings("unchecked")
    public <T> void set(Side context, T value) {
        ((BiConsumer<Side, T>) setter).accept(context, value);
    }
}
