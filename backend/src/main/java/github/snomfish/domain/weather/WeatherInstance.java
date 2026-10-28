package github.snomfish.domain.weather;

import github.snomfish.functionality.copy.DeepCopyable;

public class WeatherInstance implements DeepCopyable{
    

    private WeatherId id;
    private int lifespan;


    public WeatherInstance(
        WeatherId id,
        int lifespan
    ) {
        this.id = id;
        this.lifespan = lifespan;
    }
    public WeatherInstance(WeatherInstance copy) {
        this.id = copy.id;
        this.lifespan = copy.lifespan;
    }


    public WeatherInstance deepCopy() {
        return new WeatherInstance(this);
    }


    // getter
    public WeatherId weatherId() {return id;}
    public Weather weather() {return WeatherRegistry.get(id);}
    public int lifespan() {return lifespan;}


    // setter
    public void setWeatherId(WeatherId id) {this.id = id;}
    public void setLifespan(int lifespan) {this.lifespan = lifespan;}
}
