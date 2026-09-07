package github.snomfish.domain.stats;

import github.snomfish.functionality.copy.DeepCopyable;

public class StatChanges implements DeepCopyable<StatChanges> {
    

    private int mattack;
    private int mdefense;
    private int rattack;
    private int rdefense;
    private int speed;
    private int accuracy;
    private int evasion;
    // crit chance is too different and should be stored elsewhere I think


    public StatChanges(
        int mattack,
        int mdefense,
        int rattack,
        int rdefense,
        int speed,
        int accuracy,
        int evasion
    ) {
        this.mattack = mattack;
        this.mdefense = mdefense;
        this.rattack = rattack;
        this.rdefense = rdefense;
        this.speed = speed;
        this.accuracy = accuracy;
        this.evasion = evasion;
    }
    public StatChanges() {
        this(0, 0, 0, 0, 0, 0, 0);
    }


    @Override 
    public StatChanges deepCopy() {
        return new StatChanges(
            mattack,
            mdefense,
            rattack,
            rdefense,
            speed,
            accuracy,
            evasion
        );
    }

    
    public int mattackStage() {return mattack;}
    public int mdefenseStage() {return mdefense;}
    public int rattackStage() {return rattack;}
    public int rdefenseStage() {return rdefense;}
    public int speedStage() {return speed;}
    public int accuracyStage() {return accuracy;}
    public int evasionStage() {return evasion;}
    public int getStage(StatChangesId id) {
        switch (id) {
            case MATTACK: return mattack;
            case MDEFENSE: return mdefense;
            case RATTACK: return rattack;
            case RDEFENSE: return rdefense;
            case SPEED: return speed;
            case ACCURACY: return accuracy;
            case EVASION: return evasion;      
        }
        throw new RuntimeException("how have you managed to do this, is your id null?");
    }



    private double multiplier(int statStage) {
        return (2 + Math.max(statStage, 0)) / (2 - Math.min(statStage, 0));
    }
    public double mattackMultiplier() {return multiplier(mattack);}
    public double mdefenseMultiplier() {return multiplier(mdefense);}
    public double rattackMultiplier() {return multiplier(rattack);}
    public double rdefenseMultiplier() {return multiplier(rdefense);}
    public double speedMultiplier() {return multiplier(speed);}
    public double accuracyMultiplier() {return multiplier(accuracy);}
    public double evasionMultiplier() {return multiplier(evasion);}
    public double getMuliplier(StatChangesId id) {
        switch (id) {
            case MATTACK: return multiplier(mattack);
            case MDEFENSE: return multiplier(mdefense);
            case RATTACK: return multiplier(rattack);
            case RDEFENSE: return multiplier(rdefense);
            case SPEED: return multiplier(speed);
            case ACCURACY: return multiplier(accuracy);
            case EVASION: return multiplier(evasion);
        }
        throw new RuntimeException("how have you managed to do this, is your id null?");
    }


    // setter
    public void setMattack(int mattack) {
        this.mattack = (int) Math.clamp(mattack, -6, 6);
    }
    public void setMdefense(int mdefense) {
        this.mdefense = (int) Math.clamp(mdefense, -6, 6);
    }
    public void setRattack(int rattack) {
        this.rattack = (int) Math.clamp(rattack, -6, 6);
    }
    public void setRdefense(int rdefense) {
        this.rdefense = (int) Math.clamp(rdefense, -6, 6);
    }
    public void setSpeed(int speed) {
        this.speed = (int) Math.clamp(speed, -6, 6);
    }
    public void setAccuracy(int accuracy) {
        this.accuracy = (int) Math.clamp(accuracy, -6, 6);
    }
    public void setEvasion(int evasion) {
        this.evasion = (int) Math.clamp(evasion, -6, 6);
    }
}
