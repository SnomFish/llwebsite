package github.snomfish.domain;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.domain.mechanics.Mechanics;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherInstance;
import github.snomfish.functionality.copy.DeepCopyable;

// the gamestate is mutable.
// then branching the gamestate is copied and the new changes are applied to each gamestate
// should cut down on garbage collection
public class Gamestate implements DeepCopyable<Gamestate> {
    
    
    private Side playerSide;
    private Side enemySide;
    private List<SideId> speedOrder;
    private WeatherInstance weather;
    private Mechanics mechanics;
    private int turnNumber;

    private List<String> battleLog;


    public Gamestate(
        Side playerSide,
        Side enemySide
    ) {
        this.playerSide = playerSide;
        this.enemySide = enemySide;
        this.speedOrder = new ArrayList<>();
        this.weather = new WeatherInstance(WeatherId.NO_WEATHER, 999);
        this.mechanics = new Mechanics();
        this.turnNumber = 0;

        this.battleLog = new ArrayList<>();
    }
    public Gamestate(Gamestate copy) {
        this.playerSide = copy.playerSide.deepCopy();
        this.enemySide = copy.enemySide.deepCopy();
        this.speedOrder = new ArrayList<>(copy.speedOrder);
        this.weather = copy.weather.deepCopy();
        this.mechanics = copy.mechanics.deepCopy();
        this.turnNumber = copy.turnNumber;

        this.battleLog = new ArrayList<>(copy.battleLog);
    }



    // deepcopy
    public Gamestate deepCopy() {
        return new Gamestate(this);
    }



    
    // getters
    public Side playerSide() {return playerSide;}
    public Side enemySide() {return enemySide;}
    public Side side(SideId sideId) {
        switch (sideId) {
            case PLAYER: return playerSide;
            case ENEMY: return enemySide;
            default: 
                throw new IllegalArgumentException("Unknown side: " + sideId);
        }
    }
    public List<SideId> speedOrder() {return speedOrder;}
    public WeatherInstance weatherInstance() {return weather;}
    public Mechanics mechanics() {return mechanics;}
    public int turnNumber() {return turnNumber;}
    public List<String> battleLog() {return battleLog;}




    // setter
    public void setPlayerSide(Side playerSide) {this.playerSide = playerSide;}
    public void setEnemySide(Side enemySide) {this.enemySide = enemySide;} 
    public void setSide(SideId sideId, Side side) {
        switch (sideId) {
            case PLAYER: this.playerSide = side;
            case ENEMY: this.enemySide = side;
            default:
                throw new IllegalArgumentException("Unkown side: " + sideId);
        }
    }
    public void setSpeedOrder(List<SideId> speedOrder) {this.speedOrder = speedOrder;}
    public void setWeatherInstance(WeatherInstance weather) {this.weather = weather;}
    public void setMechanics(Mechanics mechanics) {this.mechanics = mechanics;}
    public void setTurnNumber(int turnNumber) {this.turnNumber = turnNumber;}
    public void setBattleLog(List<String> battleLog) {this.battleLog = battleLog;}


    public void incrementTurnNumber() {
        this.turnNumber++;
        this.playerSide.incrementTurnNumber();
        this.enemySide.incrementTurnNumber();
    }


    public void clearWeather() {this.weather = new WeatherInstance(WeatherId.NO_WEATHER, 999);}
    // add a way to decriment the lifespan


    // battle log
    public void log(String entry) {battleLog.add(entry);}
    public void printLog() {
        System.out.println("BattleLog:");
        for (String entry : battleLog) {
            System.out.println(entry);
        }
    }


    
}
