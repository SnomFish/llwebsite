package github.snomfish.backend;

import java.util.List;

import org.junit.jupiter.api.Test;

import github.snomfish.domain.Loomian;
import github.snomfish.domain.nature.Nature;
import github.snomfish.domain.stats.Stats;

import static github.snomfish.domain.ability.AbilityId.*;
import static github.snomfish.domain.item.ItemId.*;
import static github.snomfish.domain.move.MoveId.*;
import static github.snomfish.domain.nature.NatureId.*;
import static github.snomfish.domain.species.SpeciesId.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestLoomian {


    // test loomians
    public static Loomian testLoomian1() {
        return new Loomian(
            EMBIT,
            List.of(BLAZE_OF_GLORY, BOILING_PRESS, DRUDGE, BARBS),
            TERRIFYING,
            WAVE_PEARL,
            Nature.natureModifiers(List.of(BRAWNY, NIMBLE, VERY_CLUMSY)),
            new Stats(0, 0, 0, 0, 0, 0, 0),
            new Stats(40, 40, 40, 40, 40, 40, 40),
            null
        );
    }
    public static Loomian testLoomian2() {
        return new Loomian(
            OPERAPTOR,
            List.of(VICE_JAWS, EARTHQUAKE, THUNDER_CHOMP, CHOMP),
            POWER_JAW,
            SPECIALY_BOOTS,
            Nature.natureModifiers(List.of(BRAWNY, NIMBLE, VERY_CLUMSY)),
            new Stats(0, 0, 0, 0, 0, 0, 0),
            new Stats(40, 40, 40, 40, 40, 40, 40),
            null
        );
    }


    @Test 
    void testBuilder1() {
        Loomian original = testLoomian1();
        Loomian copy = original.builder()
            .speciesId(AMPOLE)
            .moves(List.of(AIM, AIM, AIM, AIM))
            .ability(ABILITY_THIEF)
            .item(NO_ITEM)
            .nature(Nature.natureModifiers(List.of(FRAIL, VERY_SMART, SLUGGISH)))
            .tps(new Stats(100, 100, 100, 100, 100, 100, 100))
            .ups(new Stats(0, 0, 0, 0, 0, 0, 0))
            .build();
        
        assertNotEquals(original.speciesId(), copy.speciesId());
        assertNotEquals(original.moves(), copy.moves());
        assertNotEquals(original.ability(), copy.ability());
        assertNotEquals(original.item(), copy.item());
        assertNotEquals(original.nature(), copy.nature());
        assertNotEquals(original.tps(), copy.tps());
        assertNotEquals(original.ups(), copy.ups());
    }

}