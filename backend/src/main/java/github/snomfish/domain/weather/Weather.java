package github.snomfish.domain.weather;

import github.snomfish.functionality.event.Definition;

public class Weather extends Definition<WeatherId, Weather> {

    
    public Weather() {
        super();
    }


    public static Weather build() {
        return new Weather();
    }
}
