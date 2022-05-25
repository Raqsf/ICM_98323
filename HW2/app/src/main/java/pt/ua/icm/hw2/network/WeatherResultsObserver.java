package pt.ua.icm.hw2.network;

import java.util.List;

import pt.ua.icm.hw2.model.Weather;

public interface WeatherResultsObserver {
    public void receiveWeatherList(List<Weather> weatherCollection);
    public void onFailure(Throwable cause);
}
