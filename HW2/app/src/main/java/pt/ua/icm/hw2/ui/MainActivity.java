package pt.ua.icm.hw2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
//    private TextView feedback;

    private boolean mTwoPane = false;

    WeatherClient client = new WeatherClient();
    private HashMap<String, Region> regions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateRegionsList();

        if (findViewById(R.id.weather_detail_container) != null) {
            mTwoPane = true;
        }
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MAIN", "CLICKED");
                //callWeatherForecastForACityStep1("Aveiro");
                //getRegions();
            }
        });*/

//        feedback = findViewById(R.id.tvFeedback);
    }

    private void generateDataList(List<Region> regions) {
        recyclerView = findViewById(R.id.weather_list);
        adapter = new RegionsAdapter(/*this, */regions);
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
                generateDataList(new ArrayList(regions.values()));
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

    /*private void getRegions() {
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
    }*/
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

    class RegionsAdapter extends
            RecyclerView.Adapter<RegionsAdapter.RegionsViewHolder> {

        private List<Region> regions;
        //private Context context;
        //private LayoutInflater mInflater;

        public RegionsAdapter(/*Context context, */List<Region> regions) {
            //this.context = context;
            this.regions = regions;
            //mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RegionsAdapter.RegionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list_content,
                    parent, false);
            return new RegionsAdapter.RegionsViewHolder(mItemView, this);

        }

        @Override
        public void onBindViewHolder(@NonNull RegionsAdapter.RegionsViewHolder holder, int position) {
            Region mCurrent = regions.get(position);
            holder.wordItemView.setText(mCurrent.getLocal());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MAIN", "ONCLICK");
                    if (mTwoPane) {
                        Log.d("MAIN", "BIG");
                        int selectedRegion = mCurrent.getGlobalLocal();
                        //selectedRegion = 1010500;
                        WeatherFragment fragment = WeatherFragment.newInstance(selectedRegion);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.weather_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        /*int selectedSong = holder.getAdapterPosition();
                        SongDetailFragment fragment =
                                SongDetailFragment.newInstance(selectedSong);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.song_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();*/
                    } else {
                        Log.d("MAIN", "SMALL");
                        Context context = v.getContext();
                        Intent intent = new Intent(context, WeatherByRegionActivity.class);
                        /*intent.putExtra(SongUtils.SONG_ID_KEY,
                                holder.getAdapterPosition());*/
                        // TODO: passar id da região
                        intent.putExtra("regionId", mCurrent.getGlobalLocal());
                        context.startActivity(intent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return regions.size();
        }

        class RegionsViewHolder extends RecyclerView.ViewHolder {

            final TextView wordItemView;
            final View mView;
            final RegionsAdapter mAdapter;

            public RegionsViewHolder(View itemView, RegionsAdapter adapter) {
                super(itemView);
                mView = itemView;
                wordItemView = (TextView) itemView.findViewById(R.id.region);
                this.mAdapter = adapter;
                //itemView.setOnClickListener(this);
            }

        /*@Override
        public void onClick(View view) {

        }*/

        /*@Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.
            mWordList.set(mPosition, "Clicked! " + element);
            // Notify the adapter that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }*/
        }

    }
}