package github.snomfish.domain.stats;

import github.snomfish.functionality.copy.DeepCopyable;

public class Stats implements DeepCopyable<Stats> {
    
    private double health;
    private double energy;
    private double mattack;
    private double mdefense;
    private double rattack;
    private double rdefense;
    private double speed;


    public Stats(
        double health,
        double energy,
        double mattack,
        double mdefense,
        double rattack,
        double rdefense,
        double speed
    ) {
        this.health = health;
        this.energy = energy;
        this.mattack = mattack;
        this.mdefense = mdefense;
        this.rattack = rattack;
        this.rdefense = rdefense;
        this.speed = speed;
    }
    public Stats(Stats other) {
        this.health = other.health;
        this.energy = other.energy;
        this.mattack = other.mattack;
        this.mdefense = other.mdefense;
        this.rattack = other.rattack;
        this.rdefense = other.rdefense;
        this.speed = other.speed;
    }
    public Stats() {
        this(1, 1, 1, 1, 1, 1, 1);
    }


    public Stats deepCopy() {
        return new Stats(
            health,
            energy,
            mattack,
            mdefense,
            rattack,
            rdefense,
            speed
        );
    }


    public String toString() {
        return (health + ", " + energy + ", " + mattack + ", " + mdefense + ", " + rattack + ", " + rdefense + ", " + speed);
    }

    
    // getters
    public double health() {return health;}
    public double energy() {return energy;}
    public double mattack() {return mattack;}
    public double mdefense() {return mdefense;}
    public double rattack() {return rattack;}
    public double rdefense() {return rdefense;}
    public double speed() {return speed;}
    public double get(StatId statId) {
        return switch (statId) {
            case HEALTH -> health;
            case ENERGY -> energy;
            case MATTACK -> mattack;
            case MDEFENSE -> mdefense;
            case RATTACK -> rattack;
            case RDEFENSE -> rdefense;
            case SPEED -> speed;
        };
    }


    // setters
    public void setHealth(double health) {this.health = health;}
    public void setEnergy(double energy) {this.energy = energy;}
    public void setMattack(double mattack) {this.mattack = mattack;}
    public void setMdefense(double mdefense) {this.mdefense = mdefense;}
    public void setRattack(double rattack) {this.rattack = rattack;}
    public void setRdefense(double rdefense) {this.rdefense = rdefense;}
    public void setSpeed(double speed) {this.speed = speed;}
    public void set(StatId statId, double value) {
        switch (statId) {
            case HEALTH -> this.health = value;
            case ENERGY -> this.energy = value;
            case MATTACK -> this.mattack = value;
            case MDEFENSE -> this.mdefense = value;
            case RATTACK -> this.rattack = value;
            case RDEFENSE -> this.rdefense = value;
            case SPEED -> this.speed = value;
        }
    }
}
