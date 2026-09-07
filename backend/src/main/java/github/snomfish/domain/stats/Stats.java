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


    // adders
    public Stats addHealth(double health) {
        this.health += health;
        return this;
    }
    public Stats addEnergy(double energy) {
        this.energy += energy;
        return this;
    }
    public Stats addMattack(double mattack) {
        this.mattack += mattack;
        return this;
    }
    public Stats addMdefense(double mdefense) {
        this.mdefense += mdefense;
        return this;
    }
    public Stats addRattack(double rattack) {
        this.rattack += rattack;
        return this;
    }
    public Stats addRdefense(double rdefense) {
        this.rdefense += rdefense;
        return this;
    }
    public Stats addSpeed(double speed) {
        this.speed += speed;
        return this;
    }
    public Stats add(double value) {
        this.health += value;
        this.energy += value;
        this.mattack += value;
        this.mdefense += value;
        this.rattack += value;
        this.rdefense += value;
        this.speed += value;
        return this;
    }
    public Stats add(Stats other) {
        this.health += other.health;
        this.energy += other.energy;
        this.mattack += other.mattack;
        this.mdefense += other.mdefense;
        this.rattack += other.rattack;
        this.rdefense += other.rdefense;
        this.speed += other.speed;
        return this;
    } 
    public Stats add(double[] values) {
        if (values.length != 7) {
            throw new IllegalArgumentException("Array must have length 7");
        }
        this.health += values[0];
        this.energy += values[1];
        this.mattack += values[2];
        this.mdefense += values[3];
        this.rattack += values[4];
        this.rdefense += values[5];
        this.speed += values[6];
        return this;
    }


    // multipliers
    public Stats mulHealth(double health) {
        this.health *= health;
        return this;
    }
    public Stats mulEnergy(double energy) {
        this.energy *= energy;
        return this;
    }
    public Stats mulMattack(double mattack) {
        this.mattack *= mattack;
        return this;
    }
    public Stats mulMdefense(double mdefense) {
        this.mdefense *= mdefense;
        return this;
    }
    public Stats mulRattack(double rattack) {
        this.rattack *= rattack;
        return this;
    }
    public Stats mulRdefense(double rdefense) {
        this.rdefense *= rdefense;
        return this;
    }
    public Stats mulSpeed(double speed) {
        this.speed *= speed;
        return this;
    }
    public Stats mul(StatId statId, double value) {
        switch (statId) {
            case HEALTH -> this.health *= value;
            case ENERGY -> this.energy *= value;
            case MATTACK -> this.mattack *= value;
            case MDEFENSE -> this.mdefense *= value;
            case RATTACK -> this.rattack *= value;
            case RDEFENSE -> this.rdefense *= value;
            case SPEED -> this.speed *= value;  
        };
        return this;
    }
    public Stats mul(double value) {
        this.health *= value;
        this.energy *= value;
        this.mattack *= value;
        this.mdefense *= value;
        this.rattack *= value;
        this.rdefense *= value;
        this.speed *= value;
        return this;
    }
    public Stats mul(Stats other) {
        this.health *= other.health;
        this.energy *= other.energy;
        this.mattack *= other.mattack;
        this.mdefense *= other.mdefense;
        this.rattack *= other.rattack;
        this.rdefense *= other.rdefense;
        this.speed *= other.speed;
        return this;
    }
    public Stats mul(double[] values) {
        if (values.length != 7) {
            throw new IllegalArgumentException("Array must have length 7");
        }
        this.health *= values[0];
        this.energy *= values[1];
        this.mattack *= values[2];
        this.mdefense *= values[3];
        this.rattack *= values[4];
        this.rdefense *= values[5];
        this.speed *= values[6];
        return this;
    }


    // math
    public Stats floor() {
        this.health = Math.floor(this.health);
        this.energy = Math.floor(this.energy);
        this.mattack = Math.floor(this.mattack);
        this.mdefense = Math.floor(this.mdefense);
        this.rattack = Math.floor(this.rattack);
        this.rdefense = Math.floor(this.rdefense);
        this.speed = Math.floor(this.speed);
        return this;
    }
}
