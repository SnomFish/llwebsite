package github.snomfish.domain.nature;

import github.snomfish.domain.stats.StatId;

public enum NatureId {
    HYPER(StatId.ENERGY, 0.1),
    BRAWNY(StatId.MATTACK, 1.1),
    ROBUST(StatId.MDEFENSE, 1.1),
    SMART(StatId.RATTACK, 1.1),
    CLEVER(StatId.RDEFENSE, 1.1),
    NIMBLE(StatId.SPEED, 1.1),

    DULL(StatId.ENERGY, 0.9),
    FRAIL(StatId.MATTACK, 0.9),
    TENDER(StatId.MDEFENSE, 0.9),
    CLUMSY(StatId.RATTACK, 0.9),
    FOOLISH(StatId.RDEFENSE, 0.9),
    SLUGGISH(StatId.SPEED, 0.9),

    VERY_HYPER(StatId.ENERGY, 1.2),
    VERY_BRAWNY(StatId.MATTACK, 1.2),
    VERY_ROBUST(StatId.MDEFENSE, 1.2),
    VERY_SMART(StatId.RATTACK, 1.2),
    VERY_CLEVER(StatId.RDEFENSE, 1.2),
    VERY_NIMBLE(StatId.SPEED, 1.2),

    VERY_DULL(StatId.ENERGY, 0.8),
    VERY_FRAIL(StatId.MATTACK, 0.8),
    VERY_TENDER(StatId.MDEFENSE, 0.8),
    VERY_CLUMSY(StatId.RATTACK, 0.8),
    VERY_FOOLISH(StatId.RDEFENSE, 0.8),
    VERY_SLUGGISH(StatId.SPEED, 0.8);


    private final StatId statId;
    private final double modifier;


    private NatureId(
        StatId statId,
        double modifier
    ) {
        this.statId = statId;
        this.modifier = modifier;
    }


    public StatId StatId() {
        return statId;
    }
    public double modifier() {
        return modifier;
    }
}
