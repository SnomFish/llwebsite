package github.snomfish.domain.species;

import java.util.List;

import github.snomfish.domain.ability.AbilityId;
import github.snomfish.domain.move.MoveId;
import github.snomfish.domain.stats.Stats;
import github.snomfish.domain.type.TypeId;

public record Species(
    SpeciesId id,
    String name,
    List<TypeId> baseTypes,
    List<AbilityId> baseAbilities,
    Stats baseStats,
    int bst,
    double height,
    double weight,
    List<MoveId> learnset,
    boolean canEvolve
) {}


