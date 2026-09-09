package github.snomfish.functionality;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.functionality.context.BattleContext;

public enum Value {


    USER_TYPES(
        c -> c.user().activeLoomianData().types(),
        (c, v) -> c.user().activeLoomianData().setTypes(v)
    ),
    TARGET_TYPES(
        c -> c.target().activeLoomianData().types(),
        (c, v) -> c.target().activeLoomianData().setTypes(v)
    ),

    USER_STATUS_ID(
        c -> c.user().activeLoomian().status().id(),
        null
    ),
    TARGET_STATUS_ID(
        c -> c.target().activeLoomian().status().id(),
        null
    ),



    // USER BASE STATS
    USER_BASE_HEALTH(
        c -> c.user().activeLoomian().species().baseStats().health(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setHealth(v)
    ),
    USER_BASE_ENERGY(
        c -> c.user().activeLoomian().species().baseStats().energy(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setEnergy(v)
    ),
    USER_BASE_MATTACK(
        c -> c.user().activeLoomian().species().baseStats().mattack(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setMattack(v)
    ),
    USER_BASE_MDEFENSE(
        c -> c.user().activeLoomian().species().baseStats().mdefense(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setMdefense(v)
    ),
    USER_BASE_RATTACK(
        c -> c.user().activeLoomian().species().baseStats().rattack(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setRattack(v)
    ),
    USER_BASE_RDEFENSE(
        c -> c.user().activeLoomian().species().baseStats().rdefense(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setRdefense(v)
    ),
    USER_BASE_SPEED(
        c -> c.user().activeLoomian().species().baseStats().speed(),
        (c, v) -> c.user().activeLoomian().species().baseStats().setSpeed(v)
    ),
    // USER ACTUAL STATS
    USER_ACTUAL_HEALTH(
        c -> c.user().activeLoomian().actualStats().health(),
        (c, v) -> c.user().activeLoomian().actualStats().setHealth(v)
    ),
    USER_ACTUAL_ENERGY(
        c -> c.user().activeLoomian().actualStats().energy(),
        (c, v) -> c.user().activeLoomian().actualStats().setEnergy(v)
    ),
    USER_ACTUAL_MATTACK(
        c -> c.user().activeLoomian().actualStats().mattack(),
        (c, v) -> c.user().activeLoomian().actualStats().setMattack(v)
    ),
    USER_ACTUAL_MDEFENSE(
        c -> c.user().activeLoomian().actualStats().mdefense(),
        (c, v) -> c.user().activeLoomian().actualStats().setMdefense(v)
    ),
    USER_ACTUAL_RATTACK(
        c -> c.user().activeLoomian().actualStats().rattack(),
        (c, v) -> c.user().activeLoomian().actualStats().setRattack(v)
    ),
    USER_ACTUAL_RDEFENSE(
        c -> c.user().activeLoomian().actualStats().rdefense(),
        (c, v) -> c.user().activeLoomian().actualStats().setRdefense(v)
    ),
    USER_ACTUAL_SPEED(
        c -> c.user().activeLoomian().actualStats().speed(),
        (c, v) -> c.user().activeLoomian().actualStats().setSpeed(v)
    ),
    // USER BATTLE STATS
    USER_BATTLE_HEALTH(
        c -> c.user().activeLoomianData().battleStats().health(),
        (c, v) -> c.user().activeLoomianData().battleStats().setHealth(v)
    ),
    USER_BATTLE_ENERGY(
        c -> c.user().activeLoomianData().battleStats().energy(),
        (c, v) -> c.user().activeLoomianData().battleStats().setEnergy(v)
    ),
    USER_BATTLE_MATTACK(
        c -> c.user().activeLoomianData().battleStats().mattack(),
        (c, v) -> c.user().activeLoomianData().battleStats().setMattack(v)
    ),
    USER_BATTLE_MDEFENSE(
        c -> c.user().activeLoomianData().battleStats().mdefense(),
        (c, v) -> c.user().activeLoomianData().battleStats().setMdefense(v)
    ),  
    USER_BATTLE_RATTACK(
        c -> c.user().activeLoomianData().battleStats().rattack(),
        (c, v) -> c.user().activeLoomianData().battleStats().setRattack(v)
    ),
    USER_BATTLE_RDEFENSE(
        c -> c.user().activeLoomianData().battleStats().rdefense(),
        (c, v) -> c.user().activeLoomianData().battleStats().setRdefense(v)
    ),
    USER_BATTLE_SPEED(
        c -> c.user().activeLoomianData().battleStats().speed(),
        (c, v) -> c.user().activeLoomianData().battleStats().setSpeed(v)
    ),



    // TARGET BASE STATS
    TARGET_BASE_HEALTH(
        c -> c.target().activeLoomian().species().baseStats().health(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setHealth(v)
    ),
    TARGET_BASE_ENERGY(
        c -> c.target().activeLoomian().species().baseStats().energy(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setEnergy(v)
    ),
    TARGET_BASE_MATTACK(
        c -> c.target().activeLoomian().species().baseStats().mattack(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setMattack(v)
    ),
    TARGET_BASE_MDEFENSE(
        c -> c.target().activeLoomian().species().baseStats().mdefense(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setMdefense(v)
    ),
    TARGET_BASE_RATTACK(
        c -> c.target().activeLoomian().species().baseStats().rattack(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setRattack(v)
    ),
    TARGET_BASE_RDEFENSE(
        c -> c.target().activeLoomian().species().baseStats().rdefense(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setRdefense(v)
    ),
    TARGET_BASE_SPEED(
        c -> c.target().activeLoomian().species().baseStats().speed(),
        (c, v) -> c.target().activeLoomian().species().baseStats().setSpeed(v)
    ),
    // TARGET ACTUAL STATS
    TARGET_ACTUAL_HEALTH(
        c -> c.target().activeLoomian().actualStats().health(),
        (c, v) -> c.target().activeLoomian().actualStats().setHealth(v)
    ),
    TARGET_ACTUAL_ENERGY(
        c -> c.target().activeLoomian().actualStats().energy(),
        (c, v) -> c.target().activeLoomian().actualStats().setEnergy(v)
    ),
    TARGET_ACTUAL_MATTACK(
        c -> c.target().activeLoomian().actualStats().mattack(),
        (c, v) -> c.target().activeLoomian().actualStats().setMattack(v)
    ),
    TARGET_ACTUAL_MDEFENSE(
        c -> c.target().activeLoomian().actualStats().mdefense(),
        (c, v) -> c.target().activeLoomian().actualStats().setMdefense(v)
    ),
    TARGET_ACTUAL_RATTACK(
        c -> c.target().activeLoomian().actualStats().rattack(),
        (c, v) -> c.target().activeLoomian().actualStats().setRattack(v)
    ),
    TARGET_ACTUAL_RDEFENSE(
        c -> c.target().activeLoomian().actualStats().rdefense(),
        (c, v) -> c.target().activeLoomian().actualStats().setRdefense(v)
    ),
    TARGET_ACTUAL_SPEED(
        c -> c.target().activeLoomian().actualStats().speed(),
        (c, v) -> c.target().activeLoomian().actualStats().setSpeed(v)
    ),
    // TARGET BATTLE STATS
    TARGET_BATTLE_HEALTH(
        c -> c.target().activeLoomianData().battleStats().health(),
        (c, v) -> c.target().activeLoomianData().battleStats().setHealth(v)
    ),
    TARGET_BATTLE_ENERGY(
        c -> c.target().activeLoomianData().battleStats().energy(),
        (c, v) -> c.target().activeLoomianData().battleStats().setEnergy(v)
    ),
    TARGET_BATTLE_MATTACK(
        c -> c.target().activeLoomianData().battleStats().mattack(),
        (c, v) -> c.target().activeLoomianData().battleStats().setMattack(v)
    ),
    TARGET_BATTLE_MDEFENSE(
        c -> c.target().activeLoomianData().battleStats().mdefense(),
        (c, v) -> c.target().activeLoomianData().battleStats().setMdefense(v)
    ),  
    TARGET_BATTLE_RATTACK(
        c -> c.target().activeLoomianData().battleStats().rattack(),
        (c, v) -> c.target().activeLoomianData().battleStats().setRattack(v)
    ),
    TARGET_BATTLE_RDEFENSE(
        c -> c.target().activeLoomianData().battleStats().rdefense(),
        (c, v) -> c.target().activeLoomianData().battleStats().setRdefense(v)
    ),
    TARGET_BATTLE_SPEED(
        c -> c.target().activeLoomianData().battleStats().speed(),
        (c, v) -> c.target().activeLoomianData().battleStats().setSpeed(v)
    ),


    MOVE_TYPE(
        c -> c.user().activeLoomianData().action().move().type(),
        (c, v) -> c.user().activeLoomianData().action().move().setType(v)
    ),
    MOVE_CATEGORY(
        c -> c.user().activeLoomianData().action().move().category(),
        (c, v) -> c.user().activeLoomianData().action().move().setCategory(v)
    ),
    MOVE_DAMAGE_MODIFIER(
        c -> c.user().activeLoomianData().action().move().onHit().damageModifier(),
        (c, v) -> c.user().activeLoomianData().action().move().onHit().setDamageModifier(v)
    );


    private final Function<BattleContext, ?> getter;
    private final BiConsumer<BattleContext, ?> setter;


    private <T> Value(
        Function<BattleContext, T> getter,
        BiConsumer<BattleContext, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    public Object get(BattleContext context) {
        return getter.apply(context);
    }


    @SuppressWarnings("unchecked")
    public <T> void set(BattleContext context, T value) {
        ((BiConsumer<BattleContext, T>) setter).accept(context, value);
    }
}