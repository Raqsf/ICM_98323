package pt.ua.icm.hw2.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.icm.hw2.model.Region;
import pt.ua.icm.hw2.model.RegionGroup;
import pt.ua.icm.hw2.model.Weather;
import pt.ua.icm.hw2.model.WeatherGroup;
import pt.ua.icm.hw2.model.WeatherType;
import pt.ua.icm.hw2.model.WeatherTypeGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherClient {

    private GetDataService apiService;

    public WeatherClient() {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        this.apiService = retrofitInstance.create(GetDataService.class);
    }

    /**
     * get the 5-day forecast for a city
     * @param  localId the global identifier of the location
     * @param listener a listener object to callback with the results
     */
    public void retrieveForecastForCity(int localId, WeatherResultsObserver listener) {
        List<Weather> forecast = new ArrayList<>();

        Call<WeatherGroup> call = apiService.getWeatherByRegion(localId);
        call.enqueue(new Callback<WeatherGroup>() {
            @Override
            public void onResponse(Call<WeatherGroup> call, Response<WeatherGroup> response) {
                int statusCode = response.code();
                WeatherGroup weatherTypesGroup = response.body();
                forecast.addAll(weatherTypesGroup.getData() );
                listener.receiveWeatherList(forecast);
            }

            @Override
            public void onFailure(Call<WeatherGroup> call, Throwable t) {
                Log.e( "main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }
        });

    }

    /**
     * get the dictionary for the weather states
     * @param listener a listener object to callback with the results
     */
    public void retrieveWeatherConditionsDescriptions(WeatherTypeResultsObserver listener) {
        HashMap<Integer, WeatherType> weatherDescriptions = new HashMap<>();

        Call<WeatherTypeGroup> call = apiService.getWeatherTypes();
        call.enqueue(new Callback<WeatherTypeGroup>() {
            @Override
            public void onResponse(Call<WeatherTypeGroup> call, Response<WeatherTypeGroup> response) {
                int statusCode = response.code();
                WeatherTypeGroup weatherTypesGroup = response.body();
                for ( WeatherType weather: weatherTypesGroup.getTypes() ) {
                    Log.d("WEATHER CLIENT", weather.toString());
                    weatherDescriptions.put(weather.getIdWeatherType(), weather);
                }
                listener.receiveWeatherTypesList(weatherDescriptions);
            }

            @Override
            public void onFailure(Call<WeatherTypeGroup> call, Throwable t) {
                Log.e( "main", "errog calling remote api: " + t.getLocalizedMessage());
                listener.onFailure( t);
            }
        });
    }
}
