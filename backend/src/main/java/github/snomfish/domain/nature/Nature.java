package github.snomfish.domain.nature;

import java.util.List;

import github.snomfish.domain.stats.Stats;

public class Nature {

    // THIS ALSO DOESNT WORK AS THE MUL FUNC IN STATS DOESNT DO WHAT IT ADVERTISES
    // update: I think it now works but it hasnt been tested
    public static Stats newNature(List<NatureId> natures) {
        Stats modifiers = new Stats(1, 1, 1, 1, 1, 1, 1);
        for (NatureId nature : natures) {
            modifiers.set(nature.StatId(), nature.modifier());
        }
        return modifiers;
    }
}