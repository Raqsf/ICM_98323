package pt.ua.icm.hw2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ua.icm.hw2.R;
import pt.ua.icm.hw2.model.Region;
import pt.ua.icm.hw2.model.RegionGroup;
import pt.ua.icm.hw2.network.GetDataService;
import pt.ua.icm.hw2.network.RegionResultsObserver;
import pt.ua.icm.hw2.network.RetrofitClientInstance;
import pt.ua.icm.hw2.network.WeatherClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RegionsAdapter adapter;
    private RecyclerView recyclerView;
    private TextView feedback;

    WeatherClient client = new WeatherClient();
    private HashMap<String, Region> regions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateRegionsList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MAIN", "CLICKED");
                //callWeatherForecastForACityStep1("Aveiro");
                getRegions();
            }
        });

        feedback = findViewById(R.id.tvFeedback);
    }

    private void generateDataList(List<String> regions) {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new RegionsAdapter(this, regions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void generateRegionsList() {
        HashMap<String, Region> regions = new HashMap<>();
        GetDataService apiService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<RegionGroup> call = apiService.getRegions();
        call.enqueue(new Callback<RegionGroup>() {
            @Override
            public void onResponse(Call<RegionGroup> call, Response<RegionGroup> response) {
                int statusCode = response.code();
                Log.d("CLIENT", String.valueOf(statusCode));
                RegionGroup regionGroup = response.body();
                for(Region region : regionGroup.getRegions()) {
                    regions.put(region.getLocal(), region);
                }
                generateDataList(new ArrayList(regions.keySet()));
                //listener.receiveRegionsList(regions);
            }

            @Override
            public void onFailure(Call<RegionGroup> call, Throwable t) {
                Log.e("main", "error calling remote api: " + t.getLocalizedMessage());
                //listener.onFailure(t);
                //toast
            }
        });
       /* client.getRegionsList(new RegionResultsObserver() {

            @Override
            public void receiveRegionsList(HashMap<String, Region> regionsCollection) {
                Log.d("MAIN", "RECEIVING REGIONS");
                MainActivity.this.regions = regionsCollection;
                recyclerView = findViewById(R.id.recyclerview);
                adapter = new RegionsAdapter(this, new ArrayList(regionsCollection.values()));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get cities list!");
            }
        });*/
    }

    private void getRegions() {
        Log.d("MAIN", "GET REGIONS");
        client.getRegionsList(new RegionResultsObserver() {

            @Override
            public void receiveRegionsList(HashMap<String, Region> regionsCollection) {
                Log.d("MAIN", "RECEIVING REGIONS");
                MainActivity.this.regions = regionsCollection;
                for(Map.Entry<String,Region> entry : regionsCollection.entrySet()) {
                    feedback.append(entry.getKey() + " - " + entry.getValue());
                    feedback.append("\t");
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get cities list!");
            }
        });
    }
/*
    private void callWeatherForecastForACityStep1(String city) {

        feedback.append("\nGetting forecast for: " + city); feedback.append("\n");

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescriptions = descriptorsCollection;
                callWeatherForecastForACityStep2( city);
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get weather conditions!");
            }
        });

    }

    private void callWeatherForecastForACityStep2(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    callWeatherForecastForACityStep3(locationId);
                } else {
                    feedback.append("unknown city: " + city);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                feedback.append("Failed to get cities list!");
            }
        });
    }

    private void callWeatherForecastForACityStep3(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    feedback.append(day.toString());
                    feedback.append("\t");
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback.append( "Failed to get forecast for 5 days");
            }
        });

    }*/
}