package github.snomfish.functionality;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.functionality.context.BattleContext;

public enum Value {

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
        c -> c.user().activeLoomian().battleStats().health(),
        (c, v) -> c.user().activeLoomian().battleStats().setHealth(v)
    ),
    USER_BATTLE_ENERGY(
        c -> c.user().activeLoomian().battleStats().energy(),
        (c, v) -> c.user().activeLoomian().battleStats().setEnergy(v)
    ),
    USER_BATTLE_MATTACK(
        c -> c.user().activeLoomian().battleStats().mattack(),
        (c, v) -> c.user().activeLoomian().battleStats().setMattack(v)
    ),
    USER_BATTLE_MDEFENSE(
        c -> c.user().activeLoomian().battleStats().mdefense(),
        (c, v) -> c.user().activeLoomian().battleStats().setMdefense(v)
    ),  
    USER_BATTLE_RATTACK(
        c -> c.user().activeLoomian().battleStats().rattack(),
        (c, v) -> c.user().activeLoomian().battleStats().setRattack(v)
    ),
    USER_BATTLE_RDEFENSE(
        c -> c.user().activeLoomian().battleStats().rdefense(),
        (c, v) -> c.user().activeLoomian().battleStats().setRdefense(v)
    ),
    USER_BATTLE_SPEED(
        c -> c.user().activeLoomian().battleStats().speed(),
        (c, v) -> c.user().activeLoomian().battleStats().setSpeed(v)
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
        c -> c.target().activeLoomian().battleStats().health(),
        (c, v) -> c.target().activeLoomian().battleStats().setHealth(v)
    ),
    TARGET_BATTLE_ENERGY(
        c -> c.target().activeLoomian().battleStats().energy(),
        (c, v) -> c.target().activeLoomian().battleStats().setEnergy(v)
    ),
    TARGET_BATTLE_MATTACK(
        c -> c.target().activeLoomian().battleStats().mattack(),
        (c, v) -> c.target().activeLoomian().battleStats().setMattack(v)
    ),
    TARGET_BATTLE_MDEFENSE(
        c -> c.target().activeLoomian().battleStats().mdefense(),
        (c, v) -> c.target().activeLoomian().battleStats().setMdefense(v)
    ),  
    TARGET_BATTLE_RATTACK(
        c -> c.target().activeLoomian().battleStats().rattack(),
        (c, v) -> c.target().activeLoomian().battleStats().setRattack(v)
    ),
    TARGET_BATTLE_RDEFENSE(
        c -> c.target().activeLoomian().battleStats().rdefense(),
        (c, v) -> c.target().activeLoomian().battleStats().setRdefense(v)
    ),
    TARGET_BATTLE_SPEED(
        c -> c.target().activeLoomian().battleStats().speed(),
        (c, v) -> c.target().activeLoomian().battleStats().setSpeed(v)
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