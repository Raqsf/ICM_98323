package pt.ua.icm.hw2.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pt.ua.icm.hw2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    // TODO: create class Weather
    public String weather;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(int selectedRegion) {
        Log.d("FRAGMENT", String.valueOf(selectedRegion));
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
        Log.d("WEATHER FRAGMENT", "ONCREATE");
        if (getArguments().containsKey("regionId")) {
            // TODO: getWeather next 5 days
            weather = String.valueOf(getArguments().getInt("regionId"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("WEATHER FRAGMENT", "ONCREATEVIEW");
        View rootView = inflater.inflate(R.layout.weather_detail, container, false);
        if (!weather.equals("")) {
            ((TextView) rootView.findViewById(R.id.weather_detail))
                    .setText(weather);
        }
        return rootView;
    }
}