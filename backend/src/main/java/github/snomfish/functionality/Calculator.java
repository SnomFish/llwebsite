package github.snomfish.functionality;

import github.snomfish.domain.Loomian;
import github.snomfish.domain.modifiers.SideModifierId;
import github.snomfish.domain.stats.StatChangesId;
import github.snomfish.domain.stats.Stats;
import github.snomfish.functionality.context.BattleContext;

public class Calculator {
    
    public static final int LEVEL = 50;

    public static Stats getActualStats(
        Loomian loomian
    ) {
        Stats base = loomian.species().baseStats();
        Stats tps = loomian.tps();
        Stats ups = loomian.ups();
        Stats nature = loomian.nature();

        Stats actual = new Stats(
            Math.floor((2 * base.health() + ups.health() + tps.health() / 4)                     * LEVEL / 100) + LEVEL + 10,
            Math.floor(Math.floor((2 * base.energy() + ups.energy() + tps.energy() / 4)          * 50    / 65 + 80) * nature.energy()),
            Math.floor(Math.floor((2 * base.mattack() + ups.mattack() + tps.mattack() / 4)       * LEVEL / 100 + 5) * nature.mattack()),
            Math.floor(Math.floor((2 * base.mdefense() + ups.mdefense() + tps.mdefense() / 4)    * LEVEL / 100 + 5) * nature.mdefense()),
            Math.floor(Math.floor((2 * base.rattack() + ups.rattack() + tps.rattack() / 4)       * LEVEL / 100 + 5) * nature.rattack()),
            Math.floor(Math.floor((2 * base.rdefense() + ups.rdefense() + tps.rdefense() / 4)    * LEVEL / 100 + 5) * nature.rdefense()),
            Math.floor(Math.floor((2 * base.speed() + ups.speed() + tps.speed() / 4)             * LEVEL / 100 + 5) * nature.speed())
        );

        return actual;
    }


    public static int calcPriority(BattleContext context) {
        context.user().activeLoomian().action().calcPriority(context);
        int priority = context.user().activeLoomian().action().mainAction().priority();
        return priority;
    }
    public static int calcSpeed(BattleContext context) {
        double speed = 1;
        
        speed *= context.user().activeLoomian().actualStats().speed();
        speed *= context.user().activeLoomian().statChanges().multiplier(StatChangesId.SPEED);
        speed *= context.user().modifiers().get(SideModifierId.SPEED);

        return (int)speed;
    }
}
