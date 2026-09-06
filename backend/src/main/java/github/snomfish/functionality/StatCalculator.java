package github.snomfish.functionality;

import github.snomfish.domain.Loomian;
import github.snomfish.domain.stats.Stats;

public class StatCalculator {
    

    public static Stats getActualStats(
        Loomian loomian
    ) {
        Stats base = loomian.species().baseStats();
        Stats tps = loomian.tps();
        Stats ups = loomian.ups();
        Stats nature = loomian.nature();

        Stats actual = new Stats(base.mul(2)).add(ups).add(tps.mul(0.25)).mul(Constants.LEVEL); // (2 * base + ups + tps / 4) * level

        actual
        .mul(new double[]{0.01, 1/65, 0.01, 0.01, 0.01, 0.01, 0.01})
        .add(new double[]{0, 80, 5, 5, 5, 5, 5})
        .floor()
        .addHealth(10 + Constants.LEVEL)
        .mul(nature)
        .floor();    

        return actual;
    }
}
