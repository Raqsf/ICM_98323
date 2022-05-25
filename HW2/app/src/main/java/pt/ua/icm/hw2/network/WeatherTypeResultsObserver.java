package pt.ua.icm.hw2.network;

import java.util.HashMap;

import pt.ua.icm.hw2.model.WeatherType;

public interface WeatherTypeResultsObserver {
    public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection);
    public void onFailure(Throwable cause);
}
