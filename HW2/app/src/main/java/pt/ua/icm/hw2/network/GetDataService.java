package pt.ua.icm.hw2.network;

import pt.ua.icm.hw2.model.RegionGroup;
import pt.ua.icm.hw2.model.WeatherGroup;
import pt.ua.icm.hw2.model.WeatherTypeGroup;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("distrits-islands.json")
    Call<RegionGroup> getRegions();

    @GET("forecast/meteorology/cities/daily/{localId}.json")
    Call<WeatherGroup> getWeatherByRegion(@Path("localId") int localId);


    @GET("weather-type-classe.json")
    Call<WeatherTypeGroup> getWeatherTypes();
}
