package pt.ua.icm.hw2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pt.ua.icm.hw2.R;
import pt.ua.icm.hw2.model.Region;
import pt.ua.icm.hw2.model.Weather;
import pt.ua.icm.hw2.model.WeatherType;
import pt.ua.icm.hw2.network.WeatherClient;
import pt.ua.icm.hw2.network.WeatherResultsObserver;
import pt.ua.icm.hw2.network.WeatherTypeResultsObserver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    public List<Weather> fiveDayWeather;
    WeatherClient client = new WeatherClient();
    private HashMap<Integer, WeatherType> weatherDescriptions;

    private TextView weatherDay1;
    private TextView weatherDay2;
    private TextView weatherDay3;
    private TextView weatherDay4;
    private TextView weatherDay5;

    public WeatherFragment() {
        // Required empty public constructor
        fiveDayWeather = new ArrayList<>();
    }

    public static WeatherFragment newInstance(int selectedRegion) {
        WeatherFragment fragment = new WeatherFragment();
        // Set the bundle arguments for the fragment.
        Bundle args = new Bundle();
        args.putInt("regionId", selectedRegion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.weather_detail, container, false);
        if (getArguments().containsKey("regionId")) {
            callWeatherForecastForACity(getArguments().getInt("regionId"));
            weatherDay1 = rootView.findViewById(R.id.weather_day1);
            weatherDay2 = rootView.findViewById(R.id.weather_day2);
            weatherDay3 = rootView.findViewById(R.id.weather_day3);
            weatherDay4 = rootView.findViewById(R.id.weather_day4);
            weatherDay5 = rootView.findViewById(R.id.weather_day5);
        }
        return rootView;
    }

    private void callWeatherForecastForACity(int localId) {

        callWeatherConditions();

        client.retrieveForecastForCity(localId, new WeatherResultsObserver() {
            @Override
            public void receiveWeatherList(List<Weather> forecast) {
                weatherDay1.setText(forecast.get(0).toString() + " " + (weatherDescriptions == null ? "" : weatherDescriptions.get(forecast.get(0).getIdWeatherType()).getDescIdWeatherTypeEN()));
                weatherDay2.setText(forecast.get(1).toString() + " " + (weatherDescriptions == null ? "" : weatherDescriptions.get(forecast.get(1).getIdWeatherType()).getDescIdWeatherTypeEN()));
                weatherDay3.setText(forecast.get(2).toString() + " " + (weatherDescriptions == null ? "" : weatherDescriptions.get(forecast.get(2).getIdWeatherType()).getDescIdWeatherTypeEN()));
                weatherDay4.setText(forecast.get(3).toString() + " " + (weatherDescriptions == null ? "" : weatherDescriptions.get(forecast.get(3).getIdWeatherType()).getDescIdWeatherTypeEN()));
                weatherDay5.setText(forecast.get(4).toString() + " " + (weatherDescriptions == null ? "" : weatherDescriptions.get(forecast.get(4).getIdWeatherType()).getDescIdWeatherTypeEN()));
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.d("WEATHER", "Failed to get forecast for 5 days");
                Toast.makeText(getActivity().getApplicationContext(),"Failed to get forecast for 5 days",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callWeatherConditions() {
        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypeResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                WeatherFragment.this.weatherDescriptions = descriptorsCollection;
            }
            @Override
            public void onFailure(Throwable cause) {
                Toast.makeText(getActivity().getApplicationContext(),"Failed to get weather conditions!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}