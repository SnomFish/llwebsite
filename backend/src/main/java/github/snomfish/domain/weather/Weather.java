package github.snomfish.domain.weather;

import github.snomfish.functionality.event.Definition;

public class Weather extends Definition<WeatherId, Weather> {

    public Weather(WeatherId id) {
        super(id);
    }


    // builder functions
    public static Weather build(WeatherId id) {
        return new Weather(id);
    }
}
