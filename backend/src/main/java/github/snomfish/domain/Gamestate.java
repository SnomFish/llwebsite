package github.snomfish.domain;

import github.snomfish.domain.weather.Weather;
import github.snomfish.domain.weather.WeatherId;
import github.snomfish.domain.weather.WeatherInstance;
import github.snomfish.functionality.copy.DeepCopyable;

import java.util.ArrayList;
import java.util.List;

import static github.snomfish.domain.weather.WeatherId.*;

// the gamestate is mutable.
// then branching the gamestate is copied and the new changes are applied to each gamestate
// should cut down on garbage collection
public class Gamestate implements DeepCopyable<Gamestate> {
    
    
    private Side playerSide;
    private Side enemySide;
    private WeatherInstance weather;

    private List<String> battleLog;


    public Gamestate(
        Side playerSide,
        Side enemySide
    ) {
        this.playerSide = playerSide;
        this.enemySide = enemySide;
        this.weather = new WeatherInstance(NO_WEATHER, 999);

        this.battleLog = new ArrayList<>();
    }
    public Gamestate(Gamestate copy) {
        this.playerSide = copy.playerSide.deepCopy();
        this.enemySide = copy.enemySide.deepCopy();
        this.weather = copy.weather.deepCopy();

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
    public WeatherId weatherId() {return weather.weatherId();}
    public Weather weather() {return weather.weather();}
    public int weatherLifespan() {return weather.lifespan();}
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
    public void setWeatherId(WeatherId weatherId) {this.weather.setWeatherId(weatherId);}
    public void setWeatherLifespan(int weatherLifespan) {this.weather.setLifespan(weatherLifespan);}
    public void setBattleLog(List<String> battleLog) {this.battleLog = battleLog;}



    public void clearWeather() {this.weather = new WeatherInstance(NO_WEATHER, 999);}
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
