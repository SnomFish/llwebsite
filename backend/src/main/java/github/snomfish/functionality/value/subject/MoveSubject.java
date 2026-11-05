package github.snomfish.functionality.value.subject;

import java.util.function.BiConsumer;
import java.util.function.Function;

import github.snomfish.domain.move.Move;
import github.snomfish.domain.move.MoveTagId;
import github.snomfish.domain.move.modifiers.ModifierId;

public enum MoveSubject implements ISubject<Move> {
    TYPE(
        c -> c.type(),
        (c, v) -> c.setType(v)
    ),
    CATEGORY(
        c -> c.category(),
        (c, v) -> c.setCategory(v)
    ),
    TYPECHART(
        c -> c.typeChart(),
        (c, v) -> c.setTypeChart(v)
    ),
    CRIT_CHANCE(
        c -> c.damage(),
        (c, v) -> c.setCritChance(v)
    ),
    BASE_POWER(
        c -> c.basePower(),
        (c, v) -> c.setBasePower(v)
    ),
    DAMAGE(
        c -> c.damage(),
        (c, v) -> c.setDamage(v)
    ),
    ATTACKING_STAT_MODIFIER(
        c -> c.modifiers().get(ModifierId.ATTACKING_STAT),
        (c, v) -> c.modifiers().set(ModifierId.ATTACKING_STAT, v)
    ),
    DEFENDING_STAT_MODIFIER(
        c -> c.modifiers().get(ModifierId.DEFENDING_STAT),
        (c, v) -> c.modifiers().set(ModifierId.DEFENDING_STAT, v)
    ),
    DAMAGE_MODIFIER(
        c -> c.modifiers().get(ModifierId.DAMAGE),
        (c, v) -> c.modifiers().set(ModifierId.DAMAGE, v)
    ),
    ACCURACY_MODIFIER(
        c -> c.modifiers().get(ModifierId.ACCURACY),
        (c, v) -> c.modifiers().set(ModifierId.ACCURACY, v)
    ),
    TYPE_MODIFIER(
        c -> c.modifiers().get(ModifierId.TYPE),
        (c, v) -> c.modifiers().set(ModifierId.TYPE, v)
    ),
    STAB_MODIFIER(
        c -> c.modifiers().get(ModifierId.STAB),
        (c, v) -> c.modifiers().set(ModifierId.STAB, v)
    ),
    CRIT_MODIFIER(
        c -> c.modifiers().get(ModifierId.CRIT),
        (c, v) -> c.modifiers().set(ModifierId.CRIT, v)
    ),
    HEALTH_DRAIN_MODIFIER(
        c -> c.modifiers().get(ModifierId.HEALTH_DRAIN),
        (c, v) -> c.modifiers().set(ModifierId.HEALTH_DRAIN, v)
    ),
    ENERGY_DRAIN_MODIFIER(
        c -> c.modifiers().get(ModifierId.ENERGY_DRAIN),
        (c, v) -> c.modifiers().set(ModifierId.ENERGY_DRAIN, v)
    ),
    CONTACT_TAG(
        c -> c.tags().get(MoveTagId.CONTACT),
        (c, v) -> c.tags().set(MoveTagId.CONTACT, v)
    ),
    EXPLOSIVE_TAG(
        c -> c.tags().get(MoveTagId.EXPLOSIVE),
        (c, v) -> c.tags().set(MoveTagId.EXPLOSIVE, v)
    ),
    HEALTH_DRAIN_TAG(
        c -> c.tags().get(MoveTagId.HEALTH_DRAIN),
        (c, v) -> c.tags().set(MoveTagId.HEALTH_DRAIN, v)
    ),
    POWDER_TAG(
        c -> c.tags().get(MoveTagId.POWDER),
        (c, v) -> c.tags().set(MoveTagId.POWDER, v)
    ),
    RECOIL_TAG(
        c -> c.tags().get(MoveTagId.RECOIL),
        (c, v) -> c.tags().set(MoveTagId.RECOIL, v)
    ),
    SOUND_TAG(
        c -> c.tags().get(MoveTagId.SOUND),
        (c, v) -> c.tags().set(MoveTagId.SOUND, v)
    )
    ;


    private final Function<Move, ?> getter;
    private final BiConsumer<Move, ?> setter;


    private <T> MoveSubject(
        Function<Move, T> getter,
        BiConsumer<Move, T> setter
    ) {
        this.getter = getter;
        this.setter = setter;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(Move side) {
        return (T) getter.apply(side);
    } 

    @SuppressWarnings("unchecked")
    public <T> void set(Move context, T value) {
        ((BiConsumer<Move, T>) setter).accept(context, value);
    }
}
